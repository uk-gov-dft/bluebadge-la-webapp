package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.dft.bluebadge.common.service.ImageProcessingUtils;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.config.GeneralConfig;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Slf4j
@Controller
@RequestMapping(OrderBadgePersonDetailsController.ORDER_A_BADGE_PERSON_DETAILS_URL)
public class OrderBadgePersonDetailsController extends OrderBadgeBaseController {
  static final String ORDER_A_BADGE_PERSON_DETAILS_URL = "/order-a-badge/person/details";
  private static final String TEMPLATE = "order-a-badge/person/details";

  private static final String REDIRECT_ORDER_BADGE_PROCESSING =
      "redirect:" + OrderBadgeProcessingController.URL_PERSON_PROCESSING;

  private static final String FORM_REQUEST = "formRequest";

  private ReferenceDataService referenceDataService;
  private GeneralConfig generalConfig;

  @Autowired
  OrderBadgePersonDetailsController(
      ReferenceDataService referenceDataService, GeneralConfig generalConfig) {
    this.referenceDataService = referenceDataService;
    this.generalConfig = generalConfig;
  }

  @GetMapping
  public String showDetails(
      @ModelAttribute(FORM_REQUEST) OrderBadgePersonDetailsFormRequest formRequest,
      HttpSession session,
      Model model,
      @RequestParam(name = "fid") String flowId) {
    log.debug("Show person details page.");
    super.setupPageModel(session, model, DETAILS_SESSION_ATTR, formRequest, flowId);
    return TEMPLATE;
  }

  @PostMapping
  public String submitPersonDetails(
      @Valid @ModelAttribute(FORM_REQUEST) final OrderBadgePersonDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {

    checkFlow(session, formRequest);

    if (formRequest.hasPhoto() && !formRequest.isPhotoValid()) {
      bindingResult.rejectValue("photo", "NotValid.badge");
    }

    if (formRequest.isPhotoValid()) {
      try {
        processImage(formRequest);
      } catch (IOException | IllegalArgumentException e) {
        log.info("Error uploading image:{}", e.getCause());
        bindingResult.rejectValue("photo", "NotValid.badge");
      }
    } else {
      augmentWithExistingSessionPhoto(formRequest, session);
    }

    session.setAttribute(DETAILS_SESSION_ATTR, formRequest);

    if (bindingResult.hasErrors()) {
      model.addAttribute("errorSummary", new ErrorViewModel());
      return TEMPLATE;
    }

    return redirectFlow(formRequest, OrderBadgeProcessingController.ORDER_A_BADGE_PROCESSING_URL);
  }

  private void augmentWithExistingSessionPhoto(
      OrderBadgePersonDetailsFormRequest formRequest, HttpSession session) {
    OrderBadgePersonDetailsFormRequest formSession =
        (OrderBadgePersonDetailsFormRequest) session.getAttribute(DETAILS_SESSION_ATTR);

    if (formSession != null) {
      formRequest.setThumbBase64(formSession.getThumbBase64());
      formRequest.setByteImage(formSession.getByteImage());
    }
  }

  private void processImage(OrderBadgePersonDetailsFormRequest formRequest) throws IOException {

    MultipartFile photo = formRequest.getPhoto();
    byte[] imageByteArray = IOUtils.toByteArray(photo.getInputStream());

    formRequest.setByteImage(imageByteArray);

    BufferedImage sourceImageBuffer = ImageIO.read(new ByteArrayInputStream(imageByteArray));

    if (sourceImageBuffer == null) {
      throw new IllegalArgumentException("Invalid image.");
    }
    InputStream stream =
        ImageProcessingUtils.getInputStreamForSizedBufferedImage(
            sourceImageBuffer, generalConfig.getThumbnailHeight());

    String thumbBase64 =
        "data:image/jpeg;base64, "
            + Base64.getEncoder().encodeToString(IOUtils.toByteArray(stream));
    formRequest.setThumbBase64(thumbBase64);
  }

  @ModelAttribute("genderOptions")
  public List<ReferenceData> genderOptions() {
    return referenceDataService.retrieveBadgeGenders();
  }

  @ModelAttribute("eligibilityOptions")
  public Map<String, List<ReferenceData>> eligibilities() {
    return new TreeMap<>(
        referenceDataService
            .retrieveBadgeEligilities()
            .stream()
            .collect(
                Collectors.groupingBy(
                    ref ->
                        "ELIG_AUTO".equals(ref.getSubgroupShortCode()) ? "Automatic" : "Further")));
  }
}
