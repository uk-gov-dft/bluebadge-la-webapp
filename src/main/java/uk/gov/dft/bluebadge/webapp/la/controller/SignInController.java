package uk.gov.dft.bluebadge.webapp.la.controller;

import static uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils.addCustomError;

import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.AuthServerConnectionException;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.InvalidEmailFormatException;

@Controller
public class SignInController {
  @GetMapping("/sign-in")
  public String startSignIn(
      Model model,
      @RequestParam(name = "error", required = false) String error,
      @RequestParam(name = "logout", required = false) String loggedOut,
      @SessionAttribute(name = "SPRING_SECURITY_LAST_EXCEPTION", required = false)
          AuthenticationException signInException) {
    model.addAttribute("formRequest", new SignInForm());

    if (null != error && null != signInException) {
      handleSignInError(model, signInException);
    } else if (null != loggedOut) {
      addCustomError("info.form.global.signedOut.title", "empty", model);
    }

    return "sign-in";
  }

  private void handleSignInError(Model model, AuthenticationException signInException) {
    if (signInException instanceof BadCredentialsException) {
      addCustomError(
          "error.form.summary.title", "error.form.global.accessDenied.description", model);
    } else if (signInException instanceof InvalidEmailFormatException) {
      addCustomError("error.form.summary.title", "error.form.field.signin.email.invalid", model);
    } else if (signInException instanceof AuthServerConnectionException) {
      addCustomError(
          "error.form.field.signin.connection.failure.title",
          "error.form.field.signin.connection.failure.description",
          model);
    }
  }

  @Data
  private class SignInForm {
    private String username;
    private String password;
  }
}
