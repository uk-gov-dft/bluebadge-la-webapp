package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;

@Controller
@RequestMapping("/order-a-badge")
public class OrderBadgeIndexController extends OrderBadgeBaseController {
  private static final String TEMPLATE = "order-a-badge/index";
  static final String ORDER_BADGE_RESET_URL = "/order-a-badge?action=reset";

  private static final String REDIRECT_ORDER_A_BADGE_PERSON_DETAILS =
      "redirect:" + OrderBadgePersonDetailsController.ORDER_A_BADGE_PERSON_DETAILS_URL;
  private static final String REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS =
      "redirect:" + OrderBadgeOrganisationDetailsController.ORDER_A_BADGE_ORGANISATION_DETAILS_URL;

  private static final String PARAM_ACTION_RESET = "reset";

  private static final String APPLICANT_TYPE_ORGANISATION = "organisation";
  private static final String APPLICANT_TYPE_PERSON = "person";

  @GetMapping
  public String show(
      @RequestParam(name = "action", required = false) String action,
      @RequestParam(name = "fid", required = false) String flowId,
      @ModelAttribute("formRequest") OrderBadgeIndexFormRequest formRequest,
      HttpSession session) {
    if (StringUtils.isBlank(flowId)) {
      flowId = UUID.randomUUID().toString();
    }

    if (PARAM_ACTION_RESET.equalsIgnoreCase(StringUtils.trimToEmpty(action))) {
      super.finishSession(session);
      formRequest = OrderBadgeIndexFormRequest.builder().flowId(flowId).build();
      session.setAttribute(APP_TYPE_FORM_SESSION_ATTR, formRequest);
      return "redirect:/order-a-badge?fid=" + flowId;
    }

    setupPageModel(session, APP_TYPE_FORM_SESSION_ATTR, formRequest, flowId);
    return TEMPLATE;
  }

  @PostMapping
  public String submit(
      @Valid @ModelAttribute("formRequest") OrderBadgeIndexFormRequest formRequest,
      BindingResult bindingResult,
      HttpSession session) {
    OrderBadgeIndexFormRequest sessionFormRequest =
        (OrderBadgeIndexFormRequest) session.getAttribute(APP_TYPE_FORM_SESSION_ATTR);
    // If there was a running session and you change the applicant type, the session is wiped out
    if (sessionFormRequest != null
        && sessionFormRequest.getApplicantType() != null
        && !sessionFormRequest
            .getApplicantType()
            .equalsIgnoreCase(formRequest.getApplicantType())) {
      super.finishSession(session);
    }
    session.setAttribute(APP_TYPE_FORM_SESSION_ATTR, formRequest);

    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }

    if (APPLICANT_TYPE_ORGANISATION.equalsIgnoreCase(formRequest.getApplicantType())) {
      return REDIRECT_ORDER_A_BADGE_ORGANISATION_DETAILS + "?fid=" + formRequest.getFlowId();
    } else {
      return REDIRECT_ORDER_A_BADGE_PERSON_DETAILS + "?fid=" + formRequest.getFlowId();
    }
  }

  @ModelAttribute("applicantTypeOptions")
  public List<ReferenceData> applicantTypeOptions() {
    ReferenceData person =
        ReferenceData.builder().description("A person").shortCode(APPLICANT_TYPE_PERSON).build();
    ReferenceData organisation =
        ReferenceData.builder()
            .description("An organisation")
            .shortCode(APPLICANT_TYPE_ORGANISATION)
            .build();
    return Lists.newArrayList(person, organisation);
  }
}
