package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderABadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class OrderABadgePersonDetailsController {
  public static final String URL = "/order-a-badge/details";

  public static final String TEMPLATE = "order-a-badge/details";

  public static final String REDIRECT_ORDER_A_BADGE_PROCESSING = "/";

  private BadgeService badgeService;

  @Autowired
  public OrderABadgePersonDetailsController(BadgeService badgeService) {
    this.badgeService = badgeService;
  }

  @GetMapping(URL)
  public String show(
      @ModelAttribute("formRequest") final OrderABadgePersonDetailsFormRequest formRequest) {
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @Valid @ModelAttribute("formRequest") final OrderABadgePersonDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    model.addAttribute("errorSummary", new ErrorViewModel());
    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }
    return REDIRECT_ORDER_A_BADGE_PROCESSING;
  }
}
