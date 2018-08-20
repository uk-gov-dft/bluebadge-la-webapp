package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import javax.servlet.http.HttpSession;

public abstract class OrderBadgeBaseController {
  protected void finishSession(HttpSession session) {
    session.removeAttribute(OrderBadgeIndexController.SESSION_FORM_REQUEST);
    session.removeAttribute(OrderBadgeBaseDetailsController.SESSION_FORM_REQUEST);
    session.removeAttribute(OrderBadgeProcessingController.SESSION_FORM_REQUEST);
  }
}
