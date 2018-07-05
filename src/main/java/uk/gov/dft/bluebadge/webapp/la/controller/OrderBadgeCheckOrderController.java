package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.ReferenceDataService;

@Slf4j
@Controller
public class OrderBadgeCheckOrderController {
  public static final String URL = "/order-a-badge/check-order";

  public static final String TEMPLATE = "order-a-badge/check-order";

  public static final String REDIRECT_HOME = "redirect:" + HomeController.URL;

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgeCheckOrderController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(@ModelAttribute("formRequest") OrderBadgeProcessingFormRequest formRequest) {
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(HttpSession session) {
    //model.addAttribute("errorSummary", new ErrorViewModel());
    /*if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }*/
    session.removeAttribute("formRequest-order-a-badge-index");
    session.removeAttribute("formRequest-order-a-badge-details");
    session.removeAttribute("formRequest-order-a-badge-processing");
    return REDIRECT_HOME;
  }
}
