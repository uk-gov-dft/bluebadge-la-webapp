package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeBaseDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;

@Slf4j
@SuppressWarnings("squid:S00119")
public abstract class OrderBadgeBaseDetailsController<
    FormRequest extends OrderBadgeBaseDetailsFormRequest> {
  static final String SESSION_FORM_REQUEST = "formRequest-order-a-badge-details";

  public String show(FormRequest formRequest, HttpSession session, Model model) {
    Object sessionFormRequest = session.getAttribute(SESSION_FORM_REQUEST);
    if (sessionFormRequest != null) {
      BeanUtils.copyProperties(sessionFormRequest, formRequest);
    }

    OrderBadgeIndexFormRequest sessionFormRequestOrderBadgeIndex =
        (OrderBadgeIndexFormRequest)
            session.getAttribute(OrderBadgeIndexController.SESSION_FORM_REQUEST);
    String applicantType = sessionFormRequestOrderBadgeIndex.getApplicantType();
    model.addAttribute("applicantType", applicantType);

    return getTemplate();
  }

  public String submit(
      final FormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel());
    session.setAttribute(SESSION_FORM_REQUEST, formRequest);
    if (bindingResult.hasErrors()) {
      return getTemplate();
    }
    return getProcessingRedirectUrl();
  }

  protected abstract String getTemplate();

  protected abstract String getProcessingRedirectUrl();
}
