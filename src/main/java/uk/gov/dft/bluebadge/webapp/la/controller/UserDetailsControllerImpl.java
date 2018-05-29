package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserData;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.UserDetailsFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.SignInUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class UserDetailsControllerImpl implements UserDetailsController {

  private static final Logger logger = LoggerFactory.getLogger(UserDetailsControllerImpl.class);

  public static final String URL_USER_DETAILS = "/manage-users/user-details/{id}";

  public static final String TEMPLATE_USER_DETAILS = "manage-users/user-details";

  public static final String REDIRECT_URL_SIGN_IN = "redirect:" + SignInControllerImpl.URL_SIGN_IN;
  public static final String REDIRECT_URL_MANAGE_USERS =
      "redirect:" + ManageUsersControllerImpl.URL_MANAGE_USERS;

  public static final String PARAM_ID = "id";

  private UserService userService;

  private UserDetailsFormRequestToUser userDetailsFormRequestToUser;

  @Autowired
  public UserDetailsControllerImpl(
      UserService userService, UserDetailsFormRequestToUser userDetailsFormRequestToUser) {
    this.userService = userService;
    this.userDetailsFormRequestToUser = userDetailsFormRequestToUser;
  }

  @Override
  @GetMapping(URL_USER_DETAILS)
  public String showUserDetails(
      @PathVariable(PARAM_ID) int id,
      @ModelAttribute("formRequest") final UserDetailsFormRequest formRequest,
      Model model,
      HttpSession session) {
    if (!SignInUtils.isSignedIn(session)) {
      return REDIRECT_URL_SIGN_IN;
    }
    UserResponse userResponse = userService.findOneById(id);
    UserData user = userResponse.getData();
    formRequest.setEmailAddress(user.getEmailAddress());
    formRequest.setName(user.getName());
    model.addAttribute("id", id);
    return TEMPLATE_USER_DETAILS;
  }

  @Override
  @PostMapping(URL_USER_DETAILS)
  public String updateUserDetails(
      @PathVariable(PARAM_ID) int id,
      @ModelAttribute("formRequest") UserDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    try {
      if (!SignInUtils.isSignedIn(session)) {
        return REDIRECT_URL_SIGN_IN;
      }
      User user = userDetailsFormRequestToUser.convert(formRequest);
      UserData userData = userService.findOneById(id).getData();
      user.setId(userData.getId());
      user.setLocalAuthorityId(userData.getLocalAuthorityId());
      user.setRoleId(userData.getRoleId());
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
      model.addAttribute("id", id);
      return TEMPLATE_USER_DETAILS;
    }
  }
}
