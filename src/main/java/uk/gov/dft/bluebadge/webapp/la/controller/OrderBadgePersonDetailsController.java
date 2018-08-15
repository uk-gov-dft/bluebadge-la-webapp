package uk.gov.dft.bluebadge.webapp.la.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ImageProcessingService;
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
  public static final String PHOTO_SESSION_KEY = "photos";
  public static final int THUMB_IMAGE_HEIGHT = 300;
  /*
  private String[] allowedFileTypes =
    new String[] {"image/jpg", "image/jpeg", "image/png", "image/gif"};*/

  private ReferenceDataService referenceDataService;

  private ImageProcessingService imageProcessingService;

  @Autowired
  public OrderBadgePersonDetailsController(
      ReferenceDataService referenceDataService, ImageProcessingService imageProcessing) {
    this.referenceDataService = referenceDataService;
    this.imageProcessingService = imageProcessing;
  }

  @GetMapping(URL)
  public String show(
      @RequestParam(name = "action", required = false) String action,
      @ModelAttribute("formRequest") OrderBadgePersonDetailsFormRequest formRequest,
      Model model,
      HttpSession session) {
    if (FORM_ACTION_RESET.equalsIgnoreCase(StringUtils.trimToEmpty(action))) {
      session.removeAttribute(OrderBadgeIndexController.FORM_REQUEST_SESSION);
      session.removeAttribute(FORM_REQUEST_SESSION);
      session.removeAttribute(OrderBadgeProcessingController.FORM_REQUEST_SESSION);
      session.removeAttribute(PHOTO_SESSION_KEY);
    } else {
      Object sessionFormRequest = session.getAttribute(FORM_REQUEST_SESSION);
      if (sessionFormRequest != null) {
        BeanUtils.copyProperties(sessionFormRequest, formRequest);
        model.addAttribute(PHOTO_SESSION_KEY, session.getAttribute(PHOTO_SESSION_KEY));
      }
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @Valid @ModelAttribute("formRequest") final OrderBadgePersonDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel());

    /*
        Boolean isFileTypeCorrect =
            Arrays.asList(allowedFileTypes)
                .contains(formRequest.getPhoto().getContentType().toLowerCase());
    */
    /*
        if (!isFileTypeCorrect && formRequest.getPhoto().getSize() > 0) {
          bindingResult.addError(new FieldError("photo", "photo", "Select a valid photo"));
        }
    */
    //  if (isFileTypeCorrect && formRequest.getPhoto().getSize() > 0) {
    Map<String, String> photos = processImage(formRequest.getPhoto());
    session.setAttribute(PHOTO_SESSION_KEY, photos);
    //}

    session.setAttribute(FORM_REQUEST_SESSION, formRequest);

    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }
    return REDIRECT_ORDER_BADGE_PROCESSING;
  }

  private Map<String, String> processImage(MultipartFile photo) {
    Map<String, String> photos = new HashMap<>();

    try {

      InputStream stream = photo.getInputStream();
      BufferedImage buffer = ImageIO.read(stream);

      String photoBase64 = imageProcessingService.convertImageBufferToBase64(buffer);
      photos.put("photo", photoBase64);

      // generate thumb image
      Dimension dimension =
          imageProcessingService.calculateWidthBasedOnHeight(
              buffer.getWidth(), buffer.getHeight(), THUMB_IMAGE_HEIGHT);
      BufferedImage thumbBuffer = imageProcessingService.reSizeImage(buffer, dimension);
      String thumbBase64 = imageProcessingService.convertImageBufferToBase64(thumbBuffer);
      photos.put("thumb", "data:" + photo.getContentType() + ";base64, " + thumbBase64);
    } catch (IOException e) {
      log.error("Failed to process user image", e);
    }

    return photos;
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
