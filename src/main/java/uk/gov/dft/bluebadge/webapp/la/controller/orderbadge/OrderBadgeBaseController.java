package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import static uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeIndexController.ORDER_BADGE_RESET_URL;

import com.google.common.collect.ImmutableList;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.RedirectView;
import uk.gov.dft.bluebadge.webapp.la.controller.exceptions.InvalidSessionException;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FlowForm;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;

abstract class OrderBadgeBaseController {
  static final String APP_TYPE_FORM_SESSION_ATTR = "formRequest-order-a-badge-index";
  static final String DETAILS_SESSION_ATTR = "formRequest-order-a-badge-details";
  static final String PROCESSING_SESSION_ATTR = "formRequest-order-a-badge-processing";

  private static final ImmutableList<String> FLOWS_SESSION_ATTR =
      ImmutableList.of(APP_TYPE_FORM_SESSION_ATTR, DETAILS_SESSION_ATTR, PROCESSING_SESSION_ATTR);

  void finishSession(HttpSession session) {
    session.removeAttribute(APP_TYPE_FORM_SESSION_ATTR);
    session.removeAttribute(DETAILS_SESSION_ATTR);
    session.removeAttribute(PROCESSING_SESSION_ATTR);
  }

  String getApplicantType(HttpSession session) {
    OrderBadgeIndexFormRequest form =
        (OrderBadgeIndexFormRequest) session.getAttribute(APP_TYPE_FORM_SESSION_ATTR);
    if (null == form || null == form.getApplicantType()) {
      throw new InvalidSessionException("No badge order session attribute for App type form");
    }
    return form.getApplicantType();
  }

  void addApplicantTypeToModel(HttpSession session, Model model) {
    model.addAttribute("applicantType", getApplicantType(session));
  }

  void setupPageModel(
      HttpSession session, Model model, String sessionAttr, FlowForm formRequest, String flowId) {
    checkFlow(session, flowId);
    Object sessionFormRequest = session.getAttribute(sessionAttr);
    if (sessionFormRequest != null) {
      BeanUtils.copyProperties(sessionFormRequest, formRequest);
    }

    formRequest.setFlowId(flowId);
  }

  static String redirectFlow(FlowForm flowForm, String url) {
    RedirectView rv = new RedirectView(url);
    rv.setExposeModelAttributes(true);
    rv.addStaticAttribute("fid", flowForm.getFlowId());
    return "redirect:" + rv.getUrl() + "?fid=" + flowForm.getFlowId();
  }

  /**
   * If the flow ID does not match the session form flow ID, then error. If there are no session
   * forms at all, then redirect back to start.
   */
  void checkFlow(HttpSession session, final String flowId) {
    FLOWS_SESSION_ATTR
        .stream()
        .map(session::getAttribute)
        .filter(Objects::nonNull)
        .map(ff -> checkFlow((FlowForm) ff, flowId))
        .findAny()
        .orElseThrow(
            () ->
                new InvalidSessionException(
                    "No badge order session attributes", ORDER_BADGE_RESET_URL));
    //
    //    checkFlow((FlowForm) session.getAttribute(APP_TYPE_FORM_SESSION_ATTR), flowId);
    //    checkFlow((FlowForm) session.getAttribute(DETAILS_SESSION_ATTR), flowId);
    //    checkFlow((FlowForm) session.getAttribute(PROCESSING_SESSION_ATTR), flowId);
  }

  void checkFlow(HttpSession session, final FlowForm flowForm) {
    checkFlow(session, flowForm.getFlowId());
  }

  private Optional<FlowForm> checkFlow(FlowForm flowForm, String flowId) {
    Assert.hasText(flowId, "Flow ID is not set");
    if (flowForm != null && flowForm.getFlowId() != null && !flowForm.getFlowId().equals(flowId)) {
      throw new InvalidSessionException("Badge order flow ID mismatch");
    }
    return Optional.ofNullable(flowForm);
  }
}
