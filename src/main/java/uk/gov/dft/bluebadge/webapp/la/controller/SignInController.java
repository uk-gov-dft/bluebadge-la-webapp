package uk.gov.dft.bluebadge.webapp.la.controller;

import static uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils.addCustomError;

import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.security.PasswordGrantFlowAuthenticationProvider;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.AuthServerConnectionException;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.InvalidEmailFormatException;

@Controller
@Slf4j
public class SignInController {
  @GetMapping("/sign-in")
  public String startSignIn(
      @ModelAttribute("formRequest") final SignInForm formRequest,
      BindingResult bindingResult,
      Model model,
      HttpServletRequest request,
      @SessionAttribute(name = "SPRING_SECURITY_LAST_EXCEPTION", required = false)
          AuthenticationException signInException) {

    if (null != request.getParameter("error") && null != signInException) {

      // Handles validation errors

      handleSignInError(model, bindingResult, signInException);

    } else if (null != request.getParameter("logout")) {

      // Shows logout message on logout
      addCustomError("info.form.global.signedOut.title", "empty", model);
    }

    return "sign-in";
  }

  private void handleSignInError(
      Model model, BindingResult bindingResult, AuthenticationException signInException) {

    if (signInException instanceof BadCredentialsException) {
        addCustomError("error.form.summary.title", "error.form.global.accessDenied.description", model);
    } else if (signInException instanceof InvalidEmailFormatException) {
      model.addAttribute("errorSummary", new ErrorViewModel("error.form.summary.title"));
      bindingResult.addError(new FieldError("username", "username", "Enter a valid email address"));
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
