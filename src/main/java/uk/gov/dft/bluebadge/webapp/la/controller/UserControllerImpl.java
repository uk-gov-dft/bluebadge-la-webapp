package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.validation.Valid;
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

  public static final String URL_ACCESS_DENIED = "/access-denied";
  public static final String URL_EXPIRED_SESSION = "/expired-session";
  public static final String URL_SIGN_IN = "/sign-in";
  public static final String URL_SIGN_OUT = "/sign-out";
  public static final String URL_SIGNED_OUT = "/signed-out";
  public static final String URL_HOME = "/";

  public static final String TEMPLATE_SIGN_IN = "sign-in";
  public static final String TEMPLATE_SIGNED_OUT = "signed-out";

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
        if (userService.isAuthorised(formRequest.getEmail(), formRequest.getPassword())) {
          return "redirect:" + URL_HOME + "?email=" + formRequest.getEmail();
        }
      }
      return showAccessDenied(formRequest, model);
    } catch (GeneralServiceException ex) {
      throw new GeneralControllerException("There was a general controller exception", ex);
    }
  }

  @GetMapping(URL_SIGN_OUT)
  public String signout() {
    try {
      return "redirect:" + URL_SIGN_OUT;
    } catch (GeneralServiceException ex) {
      throw new GeneralControllerException("There was a general controller exception", ex);
    }
  }

  @GetMapping(URL_SIGNED_OUT)
  public String showSignedOut() {
    return TEMPLATE_SIGNED_OUT;
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
}
