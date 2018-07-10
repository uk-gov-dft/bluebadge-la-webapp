package uk.gov.dft.bluebadge.webapp.la.controller;

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
  private static final String URL = "/order-a-badge/";
  private static final String TEMPLATE = "order-a-badge/index";
  private static final String APPLICANT_TYPE_ORGANISATION = "organisation";

  public static final String FORM_REQUEST_ORDER_A_BADGE_INDEX = "formRequest-order-a-badge-index";
  public static final String FORM_REQUEST_ORDER_A_BADGE_DETAILS =
      "formRequest-order-a-badge-details";
  public static final String FORM_REQUEST_ORDER_A_BADGE_PROCESSING =
      "formRequest-order-a-badge-processing";

  private static final String REDIRECT_ORDER_A_BADGE_PERSON_DETAILS =
      "redirect:" + OrderBadgePersonDetailsController.URL;
  private static final String REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS = "redirect:/";

  @GetMapping(URL)
  public String show(
      @ModelAttribute("formRequest") final OrderBadgeIndexFormRequest formRequest,
      HttpSession session) {
    Object sessionFormRequest = session.getAttribute(FORM_REQUEST_ORDER_A_BADGE_INDEX);
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
    session.setAttribute(FORM_REQUEST_ORDER_A_BADGE_INDEX, formRequest);
    if (APPLICANT_TYPE_ORGANISATION.equalsIgnoreCase(formRequest.getApplicantType())) {
      return REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS;
    } else {
      return REDIRECT_ORDER_A_BADGE_PERSON_DETAILS;
    }
  }
}
