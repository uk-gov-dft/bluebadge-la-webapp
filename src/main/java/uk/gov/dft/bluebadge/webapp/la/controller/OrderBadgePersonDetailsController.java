package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.service.model.referencedata.ReferenceData;

@Slf4j
@Controller
public class OrderBadgePersonDetailsController {
  public static final String URL = "/order-a-badge/details";

  private static final String TEMPLATE = "order-a-badge/details";

  private static final String REDIRECT_ORDER_A_BADGE_PROCESSING =
      "redirect:" + OrderBadgeProcessingController.URL;

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgePersonDetailsController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(
      @ModelAttribute("formRequest") OrderBadgePersonDetailsFormRequest formRequest,
      HttpSession session) {
    Object sessionFormRequest = session.getAttribute("formRequest-order-a-badge-details");
    if (sessionFormRequest != null) {
      formRequest = (OrderBadgePersonDetailsFormRequest) sessionFormRequest;
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @Valid @ModelAttribute("formRequest") final OrderBadgePersonDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel());
    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }
    session.setAttribute("formRequest-order-a-badge-details", formRequest);
    return REDIRECT_ORDER_A_BADGE_PROCESSING;
  }

  @ModelAttribute("eligibilities")
  public List<ReferenceData> eligibilities() {
    return referenceDataService.retrieveEligilities();
  }
}
