package uk.gov.dft.bluebadge.webapp.la.controller;

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
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralControllerException;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralServiceException;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class UserControllerImpl implements UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

  public static final String URL_ACCESS_DENIED = "/access-denied";
  public static final String URL_EXPIRED_SESSION = "/expired-session";
  public static final String URL_SERVER_ERROR = "/server-error";
  public static final String URL_SIGN_IN = "/sign-in";
  public static final String URL_SIGN_OUT = "/sign-out";
  public static final String URL_SIGNED_OUT = "/signed-out";
  public static final String URL_HOME = "/";

  public static final String TEMPLATE_SIGN_IN = "sign-in";

  private UserService userService;

  @Autowired
  public UserControllerImpl(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(URL_SIGN_IN)
  public String showSignIn(@ModelAttribute("formRequest") final SignInFormRequest formRequest) {
    return TEMPLATE_SIGN_IN;
  }

  @PostMapping(URL_SIGN_IN)
  public String signIn(
      @Valid @ModelAttribute("formRequest") final SignInFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    try {
      if (bindingResult.hasErrors()) {
        return TEMPLATE_SIGN_IN;
      } else {
        if((formRequest.getEmail().startsWith("serverError"))) {
          logger.error("There was a general controller exception");
          return showServerError(formRequest, model);
        }
        if (userService.isAuthorised(formRequest.getEmail(), formRequest.getPassword())) {
          return "redirect:" + URL_HOME + "?email=" + formRequest.getEmail();
        }
      }
      return showAccessDenied(formRequest, model);
    } catch (GeneralServiceException ex) {
      logger.error("There was a general controller exception", ex);
      return showServerError(formRequest, model);
    }
  }

  @Override
  @GetMapping(URL_SIGN_OUT)
  public String signOut() {
    try {
      // Sign out
      return "redirect:" + URL_SIGNED_OUT;
    } catch (GeneralServiceException ex) {
      logger.error("There was a general controller exception", ex);
      throw new GeneralControllerException("There was a general controller exception", ex);
    }
  }

  @GetMapping(URL_SIGNED_OUT)
  public String showSignedOut(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, Model model) {
    model.addAttribute("signedOut", true);
    return TEMPLATE_SIGN_IN;
  }

  @GetMapping(URL_EXPIRED_SESSION)
  public String showExpiredSession(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, Model model) {
    model.addAttribute("expiredSession", true);
    return TEMPLATE_SIGN_IN;
  }

  @GetMapping(URL_ACCESS_DENIED)
  public String showAccessDenied(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, Model model) {
    model.addAttribute("accessDenied", true);
    return TEMPLATE_SIGN_IN;
  }

  @GetMapping(URL_SERVER_ERROR)
  public String showServerError(@ModelAttribute("formRequest") final SignInFormRequest formRequest, Model model) {
    model.addAttribute("serverError", true);
    return TEMPLATE_SIGN_IN;
  }

  @GetMapping("/decorated")
  public String decorated() {
    return "decorated";
  }
}
