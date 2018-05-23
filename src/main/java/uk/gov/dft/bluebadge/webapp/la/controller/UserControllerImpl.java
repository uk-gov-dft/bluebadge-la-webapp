package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
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
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.CreateANewUserRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CreateANewUserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralControllerException;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralServiceException;
import uk.gov.dft.bluebadge.webapp.la.service.SignInService;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class UserControllerImpl implements UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

  public static final String URL_ACCESS_DENIED = "/access-denied";
  public static final String URL_EXPIRED_SESSION = "/expired-session";
  public static final String URL_SERVER_ERROR = "/server-error";
  public static final String URL_SIGN_IN = "/sign-in";
  public static final String URL_SIGN_OUT = "/sign-out";
  public static final String URL_HOME = "/";
  public static final String URL_MANAGE_USERS = "/manage-users";
  public static final String URL_CREATE_A_NEW_USER = "/manage-users/create-a-new-user";

  public static final String TEMPLATE_SIGN_IN = "sign-in";
  public static final String TEMPLATE_MANAGE_USERS = "manage-users";
  public static final String TEMPLATE_CREATE_A_NEW_USER = "manage-users/create-a-new-user";

  private UserService userService;
  private SignInService signInService;

  private CreateANewUserRequestToUser createANewUserRequest2User;

  @Autowired
  public UserControllerImpl(
      UserService userService,
      SignInService signInService,
      CreateANewUserRequestToUser createANewUserRequest2UserConverter) {
    this.userService = userService;
    this.signInService = signInService;
    this.createANewUserRequest2User = createANewUserRequest2UserConverter;
  }

  @GetMapping(URL_SIGN_IN)
  public String showSignIn(
      @ModelAttribute("formRequest") final SignInFormRequest formRequest, HttpSession session) {

    if (session.getAttribute("user") != null) {
      return "redirect:" + URL_HOME;
    }
    return TEMPLATE_SIGN_IN;
  }

  @PostMapping(URL_SIGN_IN)
  public String signIn(
      @Valid @ModelAttribute("formRequest") final SignInFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {

    //   model.addAttribute("errorSummary", new ErrorViewModel("Fix the following errors:", null));

    try {
      if (bindingResult.hasErrors()) {
        return TEMPLATE_SIGN_IN;
      } else {
        String email = formRequest.getEmail();
        Optional<User> user = signInService.signIn(email);
        if (user.isPresent()) {
          session.setAttribute("user", user.get());
          return "redirect:" + URL_HOME;
        }
      }
      return showAccessDenied(formRequest, model);
    } catch (GeneralServiceException gex) {
      logger.error("There was a general controller exception", gex);
      return showServerError(formRequest, model);
    } catch (Exception ex) {
      logger.error("There was an unexpected exception", ex);
      return showServerError(formRequest, model);
    }
  }

  @Override
  @GetMapping(URL_SIGN_OUT)
  public String signOut(HttpSession session) {
    try {
      session.invalidate();
      return "redirect:" + URL_SIGN_IN;
    } catch (GeneralServiceException ex) {
      logger.error("There was a general controller exception", ex);
      throw new GeneralControllerException("There was a general controller exception", ex);
    }
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

  @GetMapping(URL_MANAGE_USERS)
  public String showManageUsers(Model model, HttpSession session) {
    Optional<String> email = SignInUtils.getEmailSignedIn(session);
    if (!email.isPresent()) {
      return "redirect:" + UserControllerImpl.URL_SIGN_IN;
    }
    User user = (User) session.getAttribute("user");
    List<User> users = userService.findAll(user.getLocalAuthorityId());
    model.addAttribute("users", users);
    return TEMPLATE_MANAGE_USERS;
  }

  @GetMapping(URL_CREATE_A_NEW_USER)
  public String showCreateANewUser(
      @ModelAttribute("formRequest") final CreateANewUserFormRequest formRequest,
      HttpSession session) {
    Optional<String> email = SignInUtils.getEmailSignedIn(session);
    if (!email.isPresent()) {
      return "redirect:" + UserControllerImpl.URL_SIGN_IN;
    }
    return TEMPLATE_CREATE_A_NEW_USER;
  }

  @PostMapping(URL_CREATE_A_NEW_USER)
  public String createANewUser(
      @ModelAttribute("formRequest") CreateANewUserFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    try {
      User user = createANewUserRequest2User.convert(formRequest).localAuthorityId(1);
      UserResponse userResponse = userService.create(user);
      return ErrorHandlingUtils.handleError(
          userResponse.getError(),
          "redirect:/" + TEMPLATE_MANAGE_USERS,
          TEMPLATE_CREATE_A_NEW_USER,
          bindingResult,
          model);
    } catch (Exception ex) {
      TemplateModelUtils.addCustomError(
          "general error creating user", "error in creating user", model);
      return TEMPLATE_CREATE_A_NEW_USER;
    }
  }
}
