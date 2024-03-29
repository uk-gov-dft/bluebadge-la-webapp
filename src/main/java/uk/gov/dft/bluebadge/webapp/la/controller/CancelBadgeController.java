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
import uk.gov.dft.bluebadge.webapp.la.controller.request.CancelBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.enums.CancelReason;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Controller
@PreAuthorize("hasAuthority('PERM_CANCEL_BADGE')")
public class CancelBadgeController {

  private static final String URL_CANCEL_BADGE = "/manage-badges/cancel-badge/{badgeNumber}";
  private static final String TEMPLATE_CANCEL_BADGE = "manage-badges/cancel-badge";

  private static final String URL_BADGE_CANCELLED = "/manage-badges/badge-cancelled/{badgeNumber}";
  private static final String TEMPLATE_BADGE_CANCELLED = "manage-badges/badge-cancelled";
  private static final String REDIRECT_URL_BADGE_CANCELLED = "redirect:" + URL_BADGE_CANCELLED;

  private static final String PARAM_BADGE_NUMBER = "badgeNumber";
  private static final String FORM_REQUEST = "formRequest";

  private ReferenceDataService referenceDataService;
  private BadgeService badgeService;

  CancelBadgeController(ReferenceDataService refDataService, BadgeService badgeService) {
    this.referenceDataService = refDataService;
    this.badgeService = badgeService;
  }

  @GetMapping(URL_CANCEL_BADGE)
  public String show(
      @SuppressWarnings("unused") @PathVariable(PARAM_BADGE_NUMBER) String badgeNumber,
      @SuppressWarnings("unused") @ModelAttribute(FORM_REQUEST)
          final CancelBadgeFormRequest formRequest,
      Model model) {
    populateCancellationReferenceData(model);
    return TEMPLATE_CANCEL_BADGE;
  }

  @PostMapping(URL_CANCEL_BADGE)
  public String submit(
      @PathVariable(PARAM_BADGE_NUMBER) String badgeNumber,
      @Valid @ModelAttribute(FORM_REQUEST) final CancelBadgeFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {

    model.addAttribute("errorSummary", new ErrorViewModel());

    if (bindingResult.hasErrors()) {
      populateCancellationReferenceData(model);
      return TEMPLATE_CANCEL_BADGE;
    }

    CancelReason reason = CancelReason.valueOf(formRequest.getReason());
    badgeService.cancelBadge(badgeNumber, reason);

    return REDIRECT_URL_BADGE_CANCELLED;
  }

  private void populateCancellationReferenceData(Model model) {
    List<ReferenceData> reasonOptions = referenceDataService.retrieveBadgeCancellations();
    model.addAttribute("reasonOptions", reasonOptions);
  }

  @GetMapping(URL_BADGE_CANCELLED)
  public String showBadgeCancelled(
      @PathVariable(PARAM_BADGE_NUMBER) String badgeNumber, Model model) {
    model.addAttribute(PARAM_BADGE_NUMBER, badgeNumber);
    return TEMPLATE_BADGE_CANCELLED;
  }
}
