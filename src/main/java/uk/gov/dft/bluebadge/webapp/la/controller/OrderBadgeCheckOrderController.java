package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.OrderBadgeFormsToBadgeOrder;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.service.model.badge.BadgeOrder;

@Slf4j
@Controller
public class OrderBadgeCheckOrderController {
  public static final String URL = "/order-a-badge/check-order";

  public static final String TEMPLATE = "order-a-badge/check-order";

  public static final String REDIRECT_HOME = "redirect:" + HomeController.URL;

  private BadgeService badgeService;
  private ReferenceDataService referenceDataService;
  private OrderBadgeFormsToBadgeOrder converter;

  @Autowired
  public OrderBadgeCheckOrderController(
      BadgeService badgeService,
      ReferenceDataService referenceDataService,
      OrderBadgeFormsToBadgeOrder converter) {
    this.badgeService = badgeService;
    this.referenceDataService = referenceDataService;
    this.converter = converter;
  }

  @GetMapping(URL)
  public String show() {
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(Model model, HttpSession session) {
    try {
      //model.addAttribute("errorSummary", new ErrorViewModel());
    /*if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }*/
      OrderBadgePersonDetailsFormRequest detailsForm =
        (OrderBadgePersonDetailsFormRequest)
          session.getAttribute("formRequest-order-a-badge-details");
      OrderBadgeProcessingFormRequest processingForm =
        (OrderBadgeProcessingFormRequest)
          session.getAttribute("formRequest-order-a-badge-processing");
      BadgeOrder badgeOrder = converter.convert(detailsForm, processingForm);
      badgeService.orderABadge(badgeOrder);
      //session.removeAttribute("formRequest-order-a-badge-index");
      //session.removeAttribute("formRequest-order-a-badge-details");
      //session.removeAttribute("formRequest-order-a-badge-processing");
      return REDIRECT_HOME;
    } catch (BadRequestException e) {
      model.addAttribute("errorSummary", new ErrorViewModel("Error in the request"));
      return TEMPLATE;
      // TODO: handling errors, it is not in the scope of this story
    }
  }
}
