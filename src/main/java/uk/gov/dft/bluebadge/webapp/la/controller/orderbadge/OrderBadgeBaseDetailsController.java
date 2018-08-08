package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeBaseDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;

@Slf4j
public abstract class OrderBadgeBaseDetailsController<
    FORM_REQUEST extends OrderBadgeBaseDetailsFormRequest> {
  public static final String SESSION_FORM_REQUEST = "formRequest-order-a-badge-details";

  public String show(FORM_REQUEST formRequest, HttpSession session) {
    Object sessionFormRequest = session.getAttribute(SESSION_FORM_REQUEST);
    if (sessionFormRequest != null) {
      BeanUtils.copyProperties(sessionFormRequest, formRequest);
    }
    return getTemplate();
  }

  public String submit(
      final FORM_REQUEST formRequest,
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
