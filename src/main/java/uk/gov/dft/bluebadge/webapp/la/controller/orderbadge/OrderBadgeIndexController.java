package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import com.google.common.collect.Lists;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;

@Controller
public class OrderBadgeIndexController extends OrderBadgeBaseController {
  public static final String URL = "/order-a-badge/";

  private static final String TEMPLATE = "order-a-badge/index";

  private static final String REDIRECT_ORDER_A_BADGE_PERSON_DETAILS =
      "redirect:" + OrderBadgePersonDetailsController.URL;
  private static final String REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS =
      "redirect:" + OrderBadgeOrganisationDetailsController.URL;

  private static final String PARAM_ACTION_RESET = "reset";

  public static final String SESSION_FORM_REQUEST = "formRequest-order-a-badge-index";

  private static final String APPLICANT_TYPE_ORGANISATION = "organisation";
  private static final String APPLICANT_TYPE_PERSON = "person";

  @GetMapping(URL)
  public String show(
      @RequestParam(name = "action", required = false) String action,
      @ModelAttribute("formRequest") OrderBadgeIndexFormRequest formRequest,
      HttpSession session) {
    if (PARAM_ACTION_RESET.equalsIgnoreCase(StringUtils.trimToEmpty(action))) {
      super.finishSession(session);
    } else {
      Object sessionFormRequest = session.getAttribute(SESSION_FORM_REQUEST);
      if (sessionFormRequest != null) {
        BeanUtils.copyProperties(sessionFormRequest, formRequest);
      }
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @Valid @ModelAttribute("formRequest") OrderBadgeIndexFormRequest formRequest,
      BindingResult bindingResult,
      HttpSession session) {
    OrderBadgeIndexFormRequest sessionFormRequest =
        (OrderBadgeIndexFormRequest) session.getAttribute(SESSION_FORM_REQUEST);
    // If there was a running session and you change the applicant type, the session is wiped out
    if (sessionFormRequest != null
        && sessionFormRequest.getApplicantType() != null
        && !sessionFormRequest
            .getApplicantType()
            .equalsIgnoreCase(formRequest.getApplicantType())) {
      super.finishSession(session);
    }
    session.setAttribute(SESSION_FORM_REQUEST, formRequest);

    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }

    if (APPLICANT_TYPE_ORGANISATION.equalsIgnoreCase(formRequest.getApplicantType())) {
      return REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS;
    } else {
      return REDIRECT_ORDER_A_BADGE_PERSON_DETAILS;
    }
  }

  @ModelAttribute("applicantTypeOptions")
  public List<ReferenceData> applicantTypeOptions() {
    ReferenceData person =
        new ReferenceData().description("A person").shortCode(APPLICANT_TYPE_PERSON);
    ReferenceData organisation =
        new ReferenceData().description("An organisation").shortCode(APPLICANT_TYPE_ORGANISATION);
    return Lists.newArrayList(person, organisation);
  }
}
