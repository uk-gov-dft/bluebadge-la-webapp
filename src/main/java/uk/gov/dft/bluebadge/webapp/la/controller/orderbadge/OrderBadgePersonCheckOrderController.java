package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgePersonFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.OrderBadgePersonFormsToOrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class OrderBadgePersonCheckOrderController
    extends OrderBadgeBaseCheckOrderController<
        OrderBadgePersonDetailsFormRequest, OrderBadgePersonFormsToBadgeOrderRequest,
        OrderBadgePersonFormsToOrderBadgeCheckOrderViewModel> {
  static final String URL = "/order-a-badge/person/check-order";

  @Autowired
  public OrderBadgePersonCheckOrderController(
      BadgeService badgeService,
      OrderBadgePersonFormsToBadgeOrderRequest converterToServiceModel,
      OrderBadgePersonFormsToOrderBadgeCheckOrderViewModel converterToViewModel) {
    super(badgeService, converterToServiceModel, converterToViewModel);
  }

  @GetMapping(URL)
  public String showCheckOrderPerson(Model model, HttpSession session) {
    return super.show(model, session, "person");
  }

  @PostMapping(URL)
  public String submitCheckOrderPerson(
      Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    return super.submit(model, session, redirectAttributes);
  }
}
