package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalCouncil;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalCouncilRefData;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

import javax.validation.Valid;

@Slf4j
@Controller
public class LocalCouncilDetailsController extends BaseController {

  public static final String URL = "/manage-local-councils/local-council-details/{shortCode}";

  private static final String TEMPLATE = "manage-local-councils/local-council-details";

  private static final String REDIRECT_URL_MANAGE_LOCAL_COUNCILS =
      "redirect:" + ManageLocalCouncilsController.URL;

  private static final String MODEL_FORM_REQUEST = "formRequest";

  private ReferenceDataService referenceDataService;

  @Autowired
  public LocalCouncilDetailsController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(@PathVariable("shortCode") String shortCode, Model model) {

    if (!model.containsAttribute(MODEL_FORM_REQUEST)) {
      LocalCouncilRefData localCouncilRefData =
          (LocalCouncilRefData)
              referenceDataService
                  .retrieveBadgeReferenceDataItem(RefDataGroupEnum.LC, shortCode)
                  .orElseThrow(() -> new NotFoundException(new CommonResponse()));

      LocalCouncil council =
          LocalCouncil.builder()
              .description(localCouncilRefData.getDescription())
              .welshDescription(localCouncilRefData.getWelshDescription())
              .build();

      model.addAttribute(MODEL_FORM_REQUEST, council);

      model.addAttribute("shortCode", shortCode);
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @PathVariable("shortCode") String shortCode,
      @Valid @ModelAttribute(MODEL_FORM_REQUEST) LocalCouncil formRequest,
      BindingResult bindingResult,
      RedirectAttributes attr,
      Model model) {

    log.info("Submit local council details, {}", shortCode);

    if (bindingResult.hasErrors()) {
      return redirectToOnBindingError(URL, formRequest, bindingResult, attr);
    }

    try {
      referenceDataService.updateLocalCouncil(shortCode, formRequest);
    } catch (BadRequestException e) {
      return redirectToOnBindingError(e, model, URL, formRequest, bindingResult, attr);
    }
    return REDIRECT_URL_MANAGE_LOCAL_COUNCILS;
  }
}
