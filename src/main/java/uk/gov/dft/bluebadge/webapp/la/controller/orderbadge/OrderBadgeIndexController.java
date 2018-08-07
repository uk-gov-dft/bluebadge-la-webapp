package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import javax.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeIndexFormRequest;

@Controller
public class OrderBadgeIndexController {
  public static final String URL = "/order-a-badge/";

  public static final String FORM_REQUEST_SESSION = "formRequest-order-a-badge-index";

  private static final String TEMPLATE = "order-a-badge/index";

  private static final String APPLICANT_TYPE_ORGANISATION = "organisation";

  private static final String REDIRECT_ORDER_A_BADGE_PERSON_DETAILS =
      "redirect:" + OrderBadgePersonDetailsController.URL;
  private static final String REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS = "redirect:/";

  @GetMapping(URL)
  public String show(
      @ModelAttribute("formRequest") final OrderBadgeIndexFormRequest formRequest,
      HttpSession session) {
    Object sessionFormRequest = session.getAttribute(FORM_REQUEST_SESSION);
    if (sessionFormRequest != null) {
      BeanUtils.copyProperties(sessionFormRequest, formRequest);
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @ModelAttribute("formRequest") OrderBadgeIndexFormRequest formRequest,
      BindingResult bindingResult,
      HttpSession session) {
    session.setAttribute(FORM_REQUEST_SESSION, formRequest);
    if (APPLICANT_TYPE_ORGANISATION.equalsIgnoreCase(formRequest.getApplicantType())) {
      return REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS;
    } else {
      return REDIRECT_ORDER_A_BADGE_PERSON_DETAILS;
    }
  }
}
