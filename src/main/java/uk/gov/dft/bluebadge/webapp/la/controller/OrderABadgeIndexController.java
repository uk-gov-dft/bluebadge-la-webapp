package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderABadgeIndexFormRequest;

@Controller
public class OrderABadgeIndexController {
  private static final String URL = "/order-a-badge/";
  private static final String TEMPLATE = "order-a-badge/index";

  private static final String REDIRECT_ORDER_A_BADGE_PERSON_DETAILS =
      "redirect:" + OrderABadgePersonDetailsController.URL;
  private static final String REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS = "TODO";

  @GetMapping(URL)
  public String show(@ModelAttribute("formRequest") final OrderABadgeIndexFormRequest formRequest) {
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @ModelAttribute("formRequest") OrderABadgeIndexFormRequest formRequest,
      BindingResult bindingResult) {
    if ("organisation".equalsIgnoreCase(formRequest.getApplicantType())) {
      return REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS;
    } else {
      return REDIRECT_ORDER_A_BADGE_PERSON_DETAILS;
    }
  }
}
