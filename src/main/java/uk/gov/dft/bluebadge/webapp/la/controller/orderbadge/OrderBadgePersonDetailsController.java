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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.dft.bluebadge.common.service.ImageProcessingUtils;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Slf4j
@Controller
public class OrderBadgePersonDetailsController
    extends OrderBadgeBaseDetailsController<OrderBadgePersonDetailsFormRequest> {
  public static final String URL = "/order-a-badge/person/details";

  private static final String TEMPLATE = "order-a-badge/person/details";

  private static final String REDIRECT_ORDER_BADGE_PROCESSING =
      "redirect:" + OrderBadgeProcessingController.URL_PERSON_PROCESSING;

  private static final int THUMB_IMAGE_HEIGHT = 300;
  private static final String FORM_REQUEST = "formRequest";

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgePersonDetailsController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String showPersonDetails(
      @ModelAttribute(FORM_REQUEST) OrderBadgePersonDetailsFormRequest formRequest,
      HttpSession session,
      Model model) {
    log.debug("Show person details page.");
    return super.show(formRequest, session, model);
  }

  @PostMapping(URL)
  public String submitPersonDetails(
      @Valid @ModelAttribute(FORM_REQUEST) final OrderBadgePersonDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {

    log.debug("Submit person details");
    model.addAttribute("errorSummary", new ErrorViewModel());

    log.debug("Submit:check hasPhoto and !photoValid:{},{}", formRequest.hasPhoto(), !formRequest.isPhotoValid());
    if (formRequest.hasPhoto() && !formRequest.isPhotoValid()) {
      bindingResult.rejectValue("photo", "NotValid.badge.photo", "Select a valid photo");
    }

    if (formRequest.isPhotoValid()) {
      log.debug("Submit photo processing");
      try {
        processImage(formRequest);
      } catch (IOException | IllegalArgumentException e) {
        log.debug("Error uploading image:{}", e.getCause());
        bindingResult.rejectValue("photo", "NotValid.badge.photo", "Select a valid photo");
      }
    } else {
      log.debug("Submit, start augment session photo");
      augmentWithExistingSessionPhoto(formRequest, session);
      log.debug("Submit, end augment session photo");
    }

    log.debug("Submit, set form request");
    session.setAttribute(SESSION_FORM_REQUEST, formRequest);

    if (bindingResult.hasErrors()) {
      log.debug("Submit, have binding errors");
      return getTemplate();
    }

    log.debug("Submit, redirecting");
    return getProcessingRedirectUrl();
  }

  private void augmentWithExistingSessionPhoto(
      OrderBadgePersonDetailsFormRequest formRequest, HttpSession session) {
    OrderBadgePersonDetailsFormRequest formSession =
        (OrderBadgePersonDetailsFormRequest) session.getAttribute(SESSION_FORM_REQUEST);

    if (formSession != null) {
      formRequest.setThumbBase64(formSession.getThumbBase64());
      formRequest.setByteImage(formSession.getByteImage());
    }
  }

  private String generateThumbnail(BufferedImage imageBuffer, String contentType)
      throws IOException {
    InputStream stream =
        ImageProcessingUtils.getInputStreamForSizedBufferedImage(imageBuffer, THUMB_IMAGE_HEIGHT);

    byte[] bytes = convertInputStreamToBytesArray(stream);

    String thumbBase64 = "data:" + contentType + ";base64, ";

    return thumbBase64 + Base64.getEncoder().encodeToString(bytes);
  }

  private void processImage(OrderBadgePersonDetailsFormRequest formRequest) throws IOException {

    MultipartFile photo = formRequest.getPhoto();

    InputStream stream = photo.getInputStream();
    byte[] imageByteArray = convertInputStreamToBytesArray(stream);

    formRequest.setByteImage(imageByteArray);

    BufferedImage sourceImageBuffer = ImageIO.read(new ByteArrayInputStream(imageByteArray));

    if (sourceImageBuffer == null) {
      throw new IllegalArgumentException("Invalid image.");
    }

    formRequest.setThumbBase64(generateThumbnail(sourceImageBuffer, photo.getContentType()));
  }

  private byte[] convertInputStreamToBytesArray(InputStream stream) throws IOException {
    byte[] bytes = new byte[stream.available()];
    int bytesRead = stream.read(bytes);
    log.debug("Read {} bytes.", bytesRead);
    return bytes;
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

  @Override
  protected String getTemplate() {
    return TEMPLATE;
  }

  @Override
  protected String getProcessingRedirectUrl() {
    return REDIRECT_ORDER_BADGE_PROCESSING;
  }
}
