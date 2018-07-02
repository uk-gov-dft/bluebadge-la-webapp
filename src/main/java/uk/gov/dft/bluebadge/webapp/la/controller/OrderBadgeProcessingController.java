package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ReferenceDataService;

@Slf4j
@Controller
public class OrderBadgeProcessingController {
  public static final String URL = "/order-a-badge/processing";

  public static final String TEMPLATE = "order-a-badge/processing";

  public static final String REDIRECT_HOME = "redirect:" + HomeController.URL;

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgeProcessingController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(
      @ModelAttribute("formRequest") OrderBadgeProcessingFormRequest formRequest,
      HttpSession session) {
    Object sessionFormRequest = session.getAttribute("formRequest-order-a-badge-processing");
    if (sessionFormRequest != null) {
      formRequest = (OrderBadgeProcessingFormRequest) sessionFormRequest;
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @ModelAttribute("formRequest") OrderBadgeProcessingFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel());
    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }
    session.setAttribute("formRequest-order-a-badge-processing", formRequest);
    return REDIRECT_HOME;
  }
}
