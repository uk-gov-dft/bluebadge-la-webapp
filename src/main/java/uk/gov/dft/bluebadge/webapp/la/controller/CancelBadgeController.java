package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CancelBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Controller
public class CancelBadgeController {

  private static final String URL_CANCEL_BADGE = "/manage-badges/cancel-badge/{badgeNumber}";
  private static final String TEMPLATE_CANCEL_BADGE = "/manage-badges/cancel-badge";
  private static final String PARAM_BADGE_NUMBER = "badgeNumber";

  private ReferenceDataService referenceDataService;

  public CancelBadgeController(ReferenceDataService refDataService) {
    this.referenceDataService = refDataService;
  }

  @GetMapping(URL_CANCEL_BADGE)
  public String show(
      @PathVariable(PARAM_BADGE_NUMBER) String badgeNumber,
      Model model,
      @ModelAttribute("formRequest") final CancelBadgeFormRequest formRequest) {

    List<ReferenceData> reasonOptions = referenceDataService.retrieveCancellations();
    model.addAttribute("reason", reasonOptions);

    return TEMPLATE_CANCEL_BADGE;
  }
}
