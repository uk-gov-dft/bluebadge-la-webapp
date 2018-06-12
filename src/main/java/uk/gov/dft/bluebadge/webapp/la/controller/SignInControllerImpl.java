package uk.gov.dft.bluebadge.webapp.la.controller;

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
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;

@Controller
public class SignInControllerImpl {

  private static final Logger logger = LoggerFactory.getLogger(SignInControllerImpl.class);

  public static final String URL_ACCESS_DENIED = "/access-denied";
  public static final String URL_EXPIRED_SESSION = "/expired-session";
  public static final String URL_SERVER_ERROR = "/server-error";
  public static final String URL_SIGN_IN = "/sign-in";
  public static final String URL_SIGN_OUT = "/sign-out";

  public static final String TEMPLATE_SIGN_IN = "sign-in";

  public static final String REDIRECT_URL_HOME = "redirect:" + HomeController.URL_HOME;

  @Autowired
  public SignInControllerImpl() {}

  //  @GetMapping(URL_SIGN_IN)
  //  public String showSignIn(
  //      @ModelAttribute("formRequest") final SignInFormRequest formRequest, HttpSession session) {
  //    if (SignInUtils.isSignedIn(session)) {
  //      return REDIRECT_URL_HOME;
  //    }
  //    return TEMPLATE_SIGN_IN;
  //  }

  @PostMapping(URL_SIGN_IN)
  public String signIn(
      @Valid @ModelAttribute("formRequest") final SignInFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel());

    try {
      if (bindingResult.hasErrors()) {
        return TEMPLATE_SIGN_IN;
      } else {
        String emailAddress = formRequest.getEmailAddress();
        //        Optional<UserResponse> user = signInService.signIn(emailAddress);
        //        if (user.isPresent()) {
        //          session.setAttribute("user", user.get().getData());
        //          return REDIRECT_URL_HOME;
        //        }
      }
      return showAccessDenied(formRequest, model);
    } catch (Exception ex) {
      logger.error("There was an unexpected exception", ex);
      return showServerError(formRequest, model);
    }
  }

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
            "error.form.global.expiredSession.title",
            "error.form.global.expiredSession.description"));
    return TEMPLATE_SIGN_IN;
  }

  @GetMapping(URL_ACCESS_DENIED)
  public String showAccessDenied(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, Model model) {
    model.addAttribute(
        "errorSummary",
        new ErrorViewModel(
            "error.form.global.accessDenied.title", "error.form.global.accessDenied.description"));
    return TEMPLATE_SIGN_IN;
  }

  @GetMapping(URL_SERVER_ERROR)
  public String showServerError(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, Model model) {
    model.addAttribute(
        "errorSummary",
        new ErrorViewModel(
            "error.form.global.serverError.title", "error.form.global.serverError.description"));
    return TEMPLATE_SIGN_IN;
  }
}
