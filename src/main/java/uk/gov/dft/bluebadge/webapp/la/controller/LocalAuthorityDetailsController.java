package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Slf4j
@Controller
public class LocalAuthorityDetailsController {

  public static final String URL = "/manage-local-authorities/local-authority-details/{shortCode}";

  private static final String TEMPLATE = "manage-local-authorities/local-authority-details";

  private static final String REDIRECT_URL_MANAGE_LOCAL_AUTHORITIES =
      "redirect:" + ManageLocalAuthoritiesController.URL;

  private static final String MODEL_FORM_REQUEST = "formRequest";

  private ReferenceDataService referenceDataService;

  @Autowired
  public LocalAuthorityDetailsController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(
      @PathVariable("shortCode") String shortCode,
      @ModelAttribute(MODEL_FORM_REQUEST) final LocalAuthorityDetailsFormRequest formRequest,
      Model model) {
    Optional<ReferenceData> localAuthorityMaybe =
        referenceDataService.retrieveBadgeReferenceDataItem(RefDataGroupEnum.LA, shortCode);
    LocalAuthorityRefData localAuthority =
        (LocalAuthorityRefData)
            localAuthorityMaybe.orElseThrow(() -> new NotFoundException(new CommonResponse()));

    formRequest.setDifferentServiceSignpostUrl(localAuthority.getDifferentServiceSignpostUrl());
    model.addAttribute("shortCode", shortCode);
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @PathVariable("shortCode") String shortCode,
      @Valid @ModelAttribute(MODEL_FORM_REQUEST) final LocalAuthorityDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    log.info("Submit local authority details");
    model.addAttribute("errorSummary", new ErrorViewModel());

    if (bindingResult.hasErrors()) {
      log.debug("Submit, have binding errors.");
      return TEMPLATE;
    } else {
      try {
        referenceDataService.updateLocalAuthority(
            shortCode, formRequest.getDifferentServiceSignpostUrl());
      } catch (BadRequestException e) {
        log.debug("Submit, have binding errors.");
        ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);
        return TEMPLATE;
      }
      return REDIRECT_URL_MANAGE_LOCAL_AUTHORITIES;
    }
  }
}
