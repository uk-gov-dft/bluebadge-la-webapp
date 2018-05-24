package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ManageUsersFormRequest;

public interface ManageUsersController {
  String manageUsers(ManageUsersFormRequest formRequest, Model model, HttpSession session);
}
