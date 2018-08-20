package uk.gov.dft.bluebadge.webapp.la.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.dft.bluebadge.common.service.ImageProcessingUtils;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Slf4j
@Controller
public class OrderBadgePersonDetailsController {
  public static final String URL = "/order-a-badge/details";
  public static final String FORM_REQUEST_SESSION = "formRequest-order-a-badge-details";
  private static final String TEMPLATE = "order-a-badge/details";

  private static final String REDIRECT_ORDER_BADGE_PROCESSING =
      "redirect:" + OrderBadgeProcessingController.URL;

  private static final String FORM_ACTION_RESET = "reset";
  public static final int THUMB_IMAGE_HEIGHT = 300;
  public static final String FORM_REQUEST = "formRequest";

  private String[] allowedFileTypes =
      new String[] {"image/jpg", "image/jpeg", "image/png", "image/gif"};

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgePersonDetailsController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(
      @RequestParam(name = "action", required = false) String action,
      @ModelAttribute(FORM_REQUEST) OrderBadgePersonDetailsFormRequest formRequest,
      Model model,
      HttpSession session) {
    if (FORM_ACTION_RESET.equalsIgnoreCase(StringUtils.trimToEmpty(action))) {
      session.removeAttribute(OrderBadgeIndexController.FORM_REQUEST_SESSION);
      session.removeAttribute(FORM_REQUEST_SESSION);
      session.removeAttribute(OrderBadgeProcessingController.FORM_REQUEST_SESSION);
    } else {
      Object sessionFormRequest = session.getAttribute(FORM_REQUEST_SESSION);
      if (sessionFormRequest != null) {
        BeanUtils.copyProperties(sessionFormRequest, formRequest);
      }
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @Valid @ModelAttribute(FORM_REQUEST) final OrderBadgePersonDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel());

    Boolean isFileTypeCorrect =
        Arrays.asList(allowedFileTypes)
            .contains(formRequest.getPhoto().getContentType().toLowerCase());

    if (!isFileTypeCorrect && formRequest.getPhoto().getSize() > 0) {
      bindingResult.rejectValue("photo", "NotValid.badge.photo", "Select a valid photo");
    }

    if (isFileTypeCorrect && formRequest.getPhoto().getSize() > 0) {
      try {
        processImage(formRequest);
      } catch (IOException | IllegalArgumentException e) {
        bindingResult.rejectValue("photo", "NotValid.badge.photo", "Select a valid photo");
      }
    }

    session.setAttribute(FORM_REQUEST_SESSION, formRequest);

    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }
    return REDIRECT_ORDER_BADGE_PROCESSING;
  }

  private void processImage(OrderBadgePersonDetailsFormRequest formRequest) throws IOException {

    MultipartFile photo = formRequest.getPhoto();
    InputStream stream = photo.getInputStream();
    BufferedImage imageBuffer = ImageIO.read(stream);

    if (imageBuffer == null) {
      throw new IllegalArgumentException("Invalid image.");
    }

    byte[] imageInByte;
    ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
    ImageIO.write(imageBuffer, "JPG", byteArrayStream);
    byteArrayStream.flush();
    imageInByte = byteArrayStream.toByteArray();
    byteArrayStream.close();

    InputStream input =
        ImageProcessingUtils.getInputStreamForSizedBufferedImage(imageBuffer, THUMB_IMAGE_HEIGHT);
    BufferedImage thumb = ImageIO.read(input);
    String thumbBase64 =
        "data:"
            + formRequest.getPhoto().getContentType()
            + ";base64, "
            + ImageProcessingUtils.getBase64FromBufferedImage(thumb);

    formRequest.setThumbBase64(thumbBase64);
    formRequest.setByteImage(imageInByte);
  }

  @ModelAttribute("genderOptions")
  public List<ReferenceData> genderOptions() {
    return referenceDataService.retrieveGenders();
  }

  @ModelAttribute("eligibilityOptions")
  public Map<String, List<ReferenceData>> eligibilities() {
    return new TreeMap<>(
        referenceDataService
            .retrieveEligilities()
            .stream()
            .collect(
                Collectors.groupingBy(
                    ref ->
                        "ELIG_AUTO".equals(ref.getSubgroupShortCode()) ? "Automatic" : "Further")));
  }
}
