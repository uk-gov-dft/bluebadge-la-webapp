package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgeOrganisationFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeOrganisationDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class OrderBadgeOrganisationCheckOrderController
    extends OrderBadgeBaseCheckOrderController<
        OrderBadgeOrganisationDetailsFormRequest, OrderBadgeOrganisationFormsToBadgeOrderRequest,
        OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel> {
  private static final String URL = "/order-a-badge/organisation/check-order";

  @Autowired
  public OrderBadgeOrganisationCheckOrderController(
      BadgeService badgeService,
      OrderBadgeOrganisationFormsToBadgeOrderRequest converterToServiceModel,
      OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel converterToViewModel) {
    super(badgeService, converterToServiceModel, converterToViewModel);
  }

  @GetMapping(URL)
  public String showCheckOrderOrganisation(Model model, HttpSession session) {
    return super.show(model, session, "organisation");
  }

  @PostMapping(URL)
  public String submitCheckOrderOrganisation(
      Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    return super.submit(session, redirectAttributes);
  }
}
