package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.OrderBadgeFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.OrderBadgeFormsToOrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.ReferenceDataService;

@Slf4j
@Controller
public class OrderBadgeCheckOrderController {
  public static final String URL = "/order-a-badge/check-order";

  public static final String TEMPLATE = "order-a-badge/check-order";

  public static final String REDIRECT_HOME = "redirect:" + HomeController.URL;

  private BadgeService badgeService;
  private ReferenceDataService referenceDataService;
  private OrderBadgeFormsToBadgeOrderRequest converterToServiceModel;
  private OrderBadgeFormsToOrderBadgeCheckOrderViewModel converterToViewModel;

  @Autowired
  public OrderBadgeCheckOrderController(
      BadgeService badgeService,
      ReferenceDataService referenceDataService,
      OrderBadgeFormsToBadgeOrderRequest converterToServiceModel,
      OrderBadgeFormsToOrderBadgeCheckOrderViewModel converterToViewModel) {
    this.badgeService = badgeService;
    this.referenceDataService = referenceDataService;
    this.converterToServiceModel = converterToServiceModel;
    this.converterToViewModel = converterToViewModel;
  }

  @GetMapping(URL)
  public String show(Model model, HttpSession session) {
    OrderBadgePersonDetailsFormRequest detailsForm =
        (OrderBadgePersonDetailsFormRequest)
            session.getAttribute(OrderBadgePersonDetailsController.FORM_REQUEST_SESSION);
    OrderBadgeProcessingFormRequest processingForm =
        (OrderBadgeProcessingFormRequest)
            session.getAttribute(OrderBadgeProcessingController.FORM_REQUEST_SESSION);
    OrderBadgeCheckOrderViewModel data = converterToViewModel.convert(detailsForm, processingForm);

    model.addAttribute("data", data);
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(Model model, HttpSession session) {
    OrderBadgePersonDetailsFormRequest detailsForm =
        (OrderBadgePersonDetailsFormRequest)
            session.getAttribute(OrderBadgePersonDetailsController.FORM_REQUEST_SESSION);
    OrderBadgeProcessingFormRequest processingForm =
        (OrderBadgeProcessingFormRequest)
            session.getAttribute(OrderBadgeProcessingController.FORM_REQUEST_SESSION);
    BadgeOrderRequest badgeOrderRequest =
        converterToServiceModel.convert(detailsForm, processingForm, 2);
    badgeService.orderABadgeForAPerson(badgeOrderRequest);
    session.removeAttribute(OrderBadgeIndexController.FORM_REQUEST_SESSION);
    session.removeAttribute(OrderBadgePersonDetailsController.FORM_REQUEST_SESSION);
    session.removeAttribute(OrderBadgeProcessingController.FORM_REQUEST_SESSION);
    return REDIRECT_HOME;
  }
}
