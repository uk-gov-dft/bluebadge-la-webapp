package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;

@Slf4j
@Controller
public class OrderBadgeProcessingController {
  public static final String URL = "/order-a-badge/processing";
  public static final String TEMPLATE = "order-a-badge/processing";

  public static final String REDIRECT_ORDER_A_BADGE_SUMMARY = "/";

  @GetMapping(URL)
  public String show(
      @ModelAttribute("formRequest") final OrderBadgeProcessingFormRequest formRequest) {
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @ModelAttribute("formRequest") OrderBadgeProcessingFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    return REDIRECT_ORDER_A_BADGE_SUMMARY;
  }
}
