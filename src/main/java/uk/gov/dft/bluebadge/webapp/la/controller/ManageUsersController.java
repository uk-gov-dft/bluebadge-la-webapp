package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CreateANewUserFormRequest;

public interface ManageUsersController {
  String showManageUsers(Model model, HttpSession session);
}
