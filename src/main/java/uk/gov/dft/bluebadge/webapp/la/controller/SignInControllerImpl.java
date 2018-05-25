package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.SignInUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.SignInService;

@Controller
public class SignInControllerImpl implements SignInController {

  private static final Logger logger = LoggerFactory.getLogger(SignInControllerImpl.class);

  public static final String URL_ACCESS_DENIED = "/access-denied";
  public static final String URL_EXPIRED_SESSION = "/expired-session";
  public static final String URL_SERVER_ERROR = "/server-error";
  public static final String URL_SIGN_IN = "/sign-in";
  public static final String URL_SIGN_OUT = "/sign-out";

  public static final String TEMPLATE_SIGN_IN = "sign-in";

  public static final String REDIRECT_URL_HOME = "redirect:" + HomeControllerImpl.URL_HOME;

  private SignInService signInService;

  @Autowired
  public SignInControllerImpl(SignInService signInService) {
    this.signInService = signInService;
  }

  @GetMapping(URL_SIGN_IN)
  public String showSignIn(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, HttpSession session) {
    if (SignInUtils.isSignedIn(session)) {
      return REDIRECT_URL_HOME;
    }
    return TEMPLATE_SIGN_IN;
  }

  @PostMapping(URL_SIGN_IN)
  public String signIn(
      @Valid @ModelAttribute("formRequest") final SignInFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel("Fix the following errors:", null));

    try {
      if (bindingResult.hasErrors()) {
        return TEMPLATE_SIGN_IN;
      } else {
        String email = formRequest.getEmail();
        Optional<UserResponse> user = signInService.signIn(email);
        if (user.isPresent()) {
          session.setAttribute("user", user.get().getData());
          return REDIRECT_URL_HOME;
        }
      }
      return showAccessDenied(formRequest, model);
    } catch (Exception ex) {
      logger.error("There was an unexpected exception", ex);
      return showServerError(formRequest, model);
    }
  }

  @Override
  @GetMapping(URL_SIGN_OUT)
  public String signOut(HttpSession session) {
    session.invalidate();
    return "redirect:" + URL_SIGN_IN;
  }

  @GetMapping(URL_EXPIRED_SESSION)
  public String showExpiredSession(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, Model model) {
    model.addAttribute(
        "errorSummary",
        new ErrorViewModel(
            "You've been signed out",
            "You were inactive for 2 hours so we've signed you out to secure your account"));
    return TEMPLATE_SIGN_IN;
  }

  @GetMapping(URL_ACCESS_DENIED)
  public String showAccessDenied(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, Model model) {
    model.addAttribute(
        "errorSummary",
        new ErrorViewModel(
            "Access Denied", "You've entered an incorrect email address or password"));
    return TEMPLATE_SIGN_IN;
  }

  @GetMapping(URL_SERVER_ERROR)
  public String showServerError(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, Model model) {
    model.addAttribute("errorSummary", new ErrorViewModel("Can't sign in", "Please try again."));
    return TEMPLATE_SIGN_IN;
  }
}
