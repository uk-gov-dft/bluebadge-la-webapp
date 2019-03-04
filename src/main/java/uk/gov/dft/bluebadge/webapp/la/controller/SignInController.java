package uk.gov.dft.bluebadge.webapp.la.controller;

import static uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils.addCustomError;

import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.AuthServerConnectionException;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.InvalidEmailFormatException;

@Controller
public class SignInController {
  @GetMapping("/sign-in")
  public String startSignIn(
      Model model,
      HttpServletRequest request,
      @SessionAttribute(name = "SPRING_SECURITY_LAST_EXCEPTION", required = false)
          AuthenticationException signInException) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (null != auth && !(auth instanceof AnonymousAuthenticationToken)) {
      return "redirect:" + HomeController.URL;
    }

    model.addAttribute("formRequest", new SignInForm());

    if (null != request.getParameter("error") && null != signInException) {
      handleSignInError(model, signInException);
    } else if (null != request.getParameter("logout")) {
      addCustomError("info.form.global.signedOut.title", "empty", model);
    }

    return "sign-in";
  }

  private void handleSignInError(Model model, AuthenticationException signInException) {
    if (signInException instanceof BadCredentialsException) {
      addCustomError(
          "error.form.summary.title", "error.form.global.accessDenied.description", model);
    } else if (signInException instanceof LockedException) {
      addCustomError(
          "error.form.field.signin.locked.title",
          "error.form.field.signin.locked.description",
          model);
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
  private static class SignInForm {
    private String username;
    private String password;
  }
}
