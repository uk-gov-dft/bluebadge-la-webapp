package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgeFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.OrderBadgeFormsToOrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class OrderBadgePersonCheckOrderController {
  public static final String URL = "/order-a-badge/person/check-order";

  private static final String TEMPLATE = "order-a-badge/person/check-order";

  private static final String REDIRECT_BADGE_ORDERED =
      "redirect:" + OrderBadgeBadgeOrderedController.URL;

  private BadgeService badgeService;
  private OrderBadgeFormsToBadgeOrderRequest converterToServiceModel;
  private OrderBadgeFormsToOrderBadgeCheckOrderViewModel converterToViewModel;

  @Autowired
  public OrderBadgePersonCheckOrderController(
      BadgeService badgeService,
      OrderBadgeFormsToBadgeOrderRequest converterToServiceModel,
      OrderBadgeFormsToOrderBadgeCheckOrderViewModel converterToViewModel) {
    this.badgeService = badgeService;
    this.converterToServiceModel = converterToServiceModel;
    this.converterToViewModel = converterToViewModel;
  }

  @GetMapping(URL)
  public String show(Model model, HttpSession session) {
    OrderBadgePersonDetailsFormRequest detailsForm =
        (OrderBadgePersonDetailsFormRequest)
            session.getAttribute(OrderBadgePersonDetailsController.SESSION_FORM_REQUEST);
    OrderBadgeProcessingFormRequest processingForm =
        (OrderBadgeProcessingFormRequest)
            session.getAttribute(OrderBadgeProcessingController.SESSION_FORM_REQUEST);
    OrderBadgeCheckOrderViewModel data = converterToViewModel.convert(detailsForm, processingForm);

    model.addAttribute("data", data);
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    OrderBadgePersonDetailsFormRequest detailsForm =
        (OrderBadgePersonDetailsFormRequest)
            session.getAttribute(OrderBadgePersonDetailsController.SESSION_FORM_REQUEST);
    OrderBadgeProcessingFormRequest processingForm =
        (OrderBadgeProcessingFormRequest)
            session.getAttribute(OrderBadgeProcessingController.SESSION_FORM_REQUEST);
    BadgeOrderRequest badgeOrderRequest =
        converterToServiceModel.convert(detailsForm, processingForm);

    String badgeNumber = badgeService.orderABadgeForAPerson(badgeOrderRequest);
    redirectAttributes.addFlashAttribute("badgeNumber", badgeNumber);

    session.removeAttribute(OrderBadgeIndexController.SESSION_FORM_REQUEST);
    session.removeAttribute(OrderBadgePersonDetailsController.SESSION_FORM_REQUEST);
    session.removeAttribute(OrderBadgeProcessingController.SESSION_FORM_REQUEST);
    return REDIRECT_BADGE_ORDERED;
  }
}
