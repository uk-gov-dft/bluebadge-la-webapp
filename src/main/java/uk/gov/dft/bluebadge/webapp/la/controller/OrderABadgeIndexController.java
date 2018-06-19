package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderABadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class OrderABadgeIndexController {
  public static final String URL = "/order-a-badge/";
  public static final String TEMPLATE = "order-a-badge/index";

  public static final String REDIRECT_ORDER_A_BADGE_PERSON_DETAILS =
      "redirect:" + OrderABadgePersonDetailsController.URL;
  public static final String REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS = "TODO";

  private BadgeService badgeService;

  @Autowired
  public OrderABadgeIndexController(BadgeService badgeService) {
    this.badgeService = badgeService;
  }

  @GetMapping(URL)
  public String show(@ModelAttribute("formRequest") final OrderABadgeIndexFormRequest formRequest) {
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @ModelAttribute("formRequest") OrderABadgeIndexFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    if ("organisation".equalsIgnoreCase(formRequest.getApplicantType())) {
      return REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS;
    } else {
      return REDIRECT_ORDER_A_BADGE_PERSON_DETAILS;
    }
  }
}
