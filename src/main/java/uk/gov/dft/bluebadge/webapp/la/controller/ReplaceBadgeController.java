package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ReplaceBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Controller
public class ReplaceBadgeController {

  private static final String URL_REPLACE_BADGE = "/manage-badges/replace-badge/{badgeNumber}";
  private static final String TEMPLATE_REPLACE_BADGE = "manage-badges/replace-badge";

  private static final String URL_BADGE_REPLACED = "/manage-badges/replacement-ordered/";
  private static final String TEMPLATE_BADGE_REPLACED = "manage-badges/badge-replaced";
  private static final String REDIRECT_URL_BADGE_REPLACED = "redirect:" + URL_BADGE_REPLACED;

  private static final String PARAM_BADGE_NUMBER = "badgeNumber";
  private static final String FORM_REQUEST = "formRequest";

  private ReferenceDataService referenceDataService;
  private BadgeService badgeService;

  public ReplaceBadgeController(ReferenceDataService refDataService, BadgeService badgeService) {
    this.referenceDataService = refDataService;
    this.badgeService = badgeService;
  }

  @PreAuthorize("hasAuthority('PERM_REPLACE_BADGE')")
  @GetMapping(URL_REPLACE_BADGE)
  public String show(
      @PathVariable(PARAM_BADGE_NUMBER) String badgeNumber,
      @ModelAttribute(FORM_REQUEST) final ReplaceBadgeFormRequest formRequest,
      Model model) {
    return TEMPLATE_REPLACE_BADGE;
  }

  @PreAuthorize("hasAuthority('PERM_REPLACE_BADGE')")
  @PostMapping(URL_REPLACE_BADGE)
  public String submit(
      @PathVariable(PARAM_BADGE_NUMBER) String badgeNumber,
      @Valid @ModelAttribute(FORM_REQUEST) final ReplaceBadgeFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {

    model.addAttribute("errorSummary", new ErrorViewModel());

    if (bindingResult.hasErrors()) {
      return TEMPLATE_REPLACE_BADGE;
    }

    String newBadgeNumber =
        badgeService.replaceBadge(
            badgeNumber,
            formRequest.getReason(),
            formRequest.getDeliverTo().name(),
            formRequest.getDeliveryOption().name());

    return REDIRECT_URL_BADGE_REPLACED + newBadgeNumber;
  }

  @PreAuthorize("hasAuthority('PERM_REPLACE_BADGE')")
  @GetMapping(URL_BADGE_REPLACED)
  public String showBadgeReplaced(
      @PathVariable(PARAM_BADGE_NUMBER) String badgeNumber, Model model) {
    model.addAttribute(PARAM_BADGE_NUMBER, badgeNumber);
    return TEMPLATE_BADGE_REPLACED;
  }

  @ModelAttribute("reasonOptions")
  private List<ReferenceData> replaceReasons(Model model) {
    return referenceDataService.retrieveBadgeReplaceReasons();
  }

  @ModelAttribute("deliveryOptions")
  public List<ReferenceData> deliveryOptions() {
    return referenceDataService.retrieveBadgeDeliveryOptions();
  }

  @ModelAttribute("deliverToOptions")
  public List<ReferenceData> delivertoOptions() {
    return referenceDataService.retrieveBadgeDeliverTos();
  }

  @ModelAttribute("localAuthorityName")
  public String localAuthorityDisplayValue() {
    return referenceDataService.retrieveBadgeLocalAuthorityDisplayValue();
  }
}
