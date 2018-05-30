package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
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
import uk.gov.dft.bluebadge.webapp.la.controller.converter.CreateANewUserFormequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CreateANewUserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class UserControllerImpl implements UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

  public static final String URL_MANAGE_USERS = "/manage-users";
  public static final String URL_CREATE_A_NEW_USER = "/manage-users/create-a-new-user";
  public static final String URL_USER_DETAILS = "/manage-users/user-details";

  public static final String TEMPLATE_MANAGE_USERS = "manage-users";
  public static final String TEMPLATE_CREATE_A_NEW_USER = "manage-users/create-a-new-user";
  public static final String TEMPLATE_USER_DETAILS = "manage-users/user-details";

  public static final String REDIRECT_URL_SIGN_IN = "redirect:" + SignInControllerImpl.URL_SIGN_IN;

  private UserService userService;

  private CreateANewUserFormequestToUser createANewUserRequest2User;

  @Autowired
  public UserControllerImpl(
      UserService userService, CreateANewUserFormequestToUser createANewUserRequest2UserConverter) {
    this.userService = userService;
    this.createANewUserRequest2User = createANewUserRequest2UserConverter;
  }

  @GetMapping(URL_MANAGE_USERS)
  public String showManageUsers(Model model, HttpSession session) {
    if (!SignInUtils.isSignedIn(session)) {
      return REDIRECT_URL_SIGN_IN;
    }
    User user = SignInUtils.getUserSignedIn(session).get();
    List<User> users = userService.findAll(user.getLocalAuthorityId());
    model.addAttribute("users", users);
    return TEMPLATE_MANAGE_USERS;
  }

  @GetMapping(URL_CREATE_A_NEW_USER)
  public String showCreateANewUser(
      @ModelAttribute("formRequest") final CreateANewUserFormRequest formRequest,
      HttpSession session) {
    if (!SignInUtils.isSignedIn(session)) {
      return REDIRECT_URL_SIGN_IN;
    }
    return TEMPLATE_CREATE_A_NEW_USER;
  }

  @PostMapping(URL_CREATE_A_NEW_USER)
  public String createANewUser(
      @ModelAttribute("formRequest") CreateANewUserFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    try {
      if (!SignInUtils.isSignedIn(session)) {
        return REDIRECT_URL_SIGN_IN;
      }
      User signedInUser = SignInUtils.getUserSignedIn(session).get();
      // TODO: Role id should come from the form
      User user =
          createANewUserRequest2User
              .convert(formRequest)
              .localAuthorityId(signedInUser.getLocalAuthorityId())
              .roleId(1);
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

  @GetMapping(URL_USER_DETAILS)
  public String showUserDetails(
      @ModelAttribute("formRequest") final UserDetailsFormRequest formRequest,
      HttpSession session) {
    if (!SignInUtils.isSignedIn(session)) {
      return REDIRECT_URL_SIGN_IN;
    }
    return TEMPLATE_USER_DETAILS;
  }

  @PostMapping(URL_USER_DETAILS)
  public String updateUserDetails(
      @ModelAttribute("formRequest") UserDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    try {
      if (!SignInUtils.isSignedIn(session)) {
        return REDIRECT_URL_SIGN_IN;
      }
      User signedInUser = SignInUtils.getUserSignedIn(session).get();
      // TODO: Role id should come from the form
      User user = null;
      /* createANewUserRequest2User
      .convert(formRequest)
      .localAuthorityId(signedInUser.getLocalAuthorityId())
      .roleId(1); */
      UserResponse userResponse = userService.create(user);
      return ErrorHandlingUtils.handleError(
          userResponse.getError(),
          "redirect:/" + TEMPLATE_MANAGE_USERS,
          TEMPLATE_USER_DETAILS,
          bindingResult,
          model);
    } catch (Exception ex) {
      TemplateModelUtils.addCustomError(
          "general error updating user", "error in updating a user", model);
      return TEMPLATE_USER_DETAILS;
    }
  }
}
