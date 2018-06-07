package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;

public interface SignInController {

  String showSignIn(SignInFormRequest formRequest, HttpSession session);

  String signIn(
      SignInFormRequest formRequest, BindingResult result, Model model, HttpSession session);

  String signOut(HttpSession session);

  String showExpiredSession(SignInFormRequest formRequest, Model model);

  String showAccessDenied(SignInFormRequest formRequest, Model model);

  String showServerError(SignInFormRequest formRequest, Model model);
}