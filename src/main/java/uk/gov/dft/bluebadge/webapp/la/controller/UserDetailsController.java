package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserDetailsFormRequest;

public interface UserDetailsController {
  String showUserDetails(UserDetailsFormRequest formRequest, HttpSession session);

  String updateUserDetails(
      UserDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session);
}
