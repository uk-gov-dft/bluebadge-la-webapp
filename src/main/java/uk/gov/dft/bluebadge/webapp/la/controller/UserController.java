package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;

public interface UserController {

  String showSignIn(SignInFormRequest formRequest);

  String signIn(SignInFormRequest formRequest, BindingResult result, Model model);

  String signout();

  String showSignedOut(SignInFormRequest formRequest, Model model);

  String showExpiredSession(SignInFormRequest formRequest, Model model);

  String showAccessDenied(SignInFormRequest formRequest, Model model);
}
