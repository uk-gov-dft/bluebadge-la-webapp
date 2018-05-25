package uk.gov.dft.bluebadge.webapp.la.controller;

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
import uk.gov.dft.bluebadge.webapp.la.controller.converter.CreateANewUserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.UserDetailsFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.SignInUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserDetailsControllerImpl implements UserDetailsController {

  private static final Logger logger = LoggerFactory.getLogger(UserDetailsControllerImpl.class);

  public static final String URL_USER_DETAILS = "/manage-users/user-details";

  public static final String TEMPLATE_USER_DETAILS = "manage-users/user-details";

  public static final String REDIRECT_URL_SIGN_IN = "redirect:" + SignInControllerImpl.URL_SIGN_IN;
  public static final String REDIRECT_URL_MANAGE_USERS =
      "redirect:" + ManageUsersControllerImpl.URL_MANAGE_USERS;

  private UserService userService;

  private UserDetailsFormRequestToUser userDetailsFormRequestToUser;

  @Autowired
  public UserDetailsControllerImpl(
      UserService userService,
      UserDetailsFormRequestToUser userDetailsFormRequestToUser) {
    this.userService = userService;
    this.userDetailsFormRequestToUser = userDetailsFormRequestToUser;
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
      User user = userDetailsFormRequestToUser.convert(formRequest);
      UserResponse userResponse = userService.update(user);
      return ErrorHandlingUtils.handleError(
          userResponse.getError(),
          REDIRECT_URL_MANAGE_USERS,
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
