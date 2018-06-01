package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@Slf4j
public class UserDetailsControllerImpl implements UserDetailsController {

  public static final String TEMPLATE_USER_DETAILS = "manage-users/user-details";

  public static final String URL_USER_DETAILS = "/manage-users/user-details/{id}";

  public static final String REDIRECT_URL_SIGN_IN = "redirect:" + SignInControllerImpl.URL_SIGN_IN;
  public static final String REDIRECT_URL_MANAGE_USERS =
      "redirect:" + ManageUsersControllerImpl.URL_MANAGE_USERS;

  public static final String PARAM_ID = "id";

  public static final String MODEL_FORM_REQUEST = "formRequest";
  public static final String MODEL_ID = "id";

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
      @ModelAttribute(MODEL_FORM_REQUEST) final UserDetailsFormRequest formRequest,
      Model model,
      HttpSession session) {
    if (!SignInUtils.isSignedIn(session)) {
      return REDIRECT_URL_SIGN_IN;
    }
    UserResponse userResponse = userService.findOneById(id);
    UserData user = userResponse.getData();
    formRequest.setLocalAuthorityId(user.getLocalAuthorityId());
    formRequest.setEmailAddress(user.getEmailAddress());
    formRequest.setName(user.getName());
    model.addAttribute(MODEL_ID, id);
    return TEMPLATE_USER_DETAILS;
  }

  @Override
  @PostMapping(URL_USER_DETAILS)
  public String updateUserDetails(
      @PathVariable(PARAM_ID) int id,
      @ModelAttribute(MODEL_FORM_REQUEST) UserDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    try {
      if (!SignInUtils.isSignedIn(session)) {
        return REDIRECT_URL_SIGN_IN;
      }
      UserData userData = userService.findOneById(id).getData();
      User user = combine(formRequest, userData);
      UserResponse userResponse = userService.update(user);
      return ErrorHandlingUtils.handleError(
          userResponse.getError(),
          REDIRECT_URL_MANAGE_USERS,
          TEMPLATE_USER_DETAILS,
          bindingResult,
          model);
    } catch (Exception ex) {
      TemplateModelUtils.addCustomError(
          "error.updateUser.generalError.title",
          "error.updateUser.generalError.description",
          model);
      model.addAttribute(MODEL_ID, id);
      return TEMPLATE_USER_DETAILS;
    }
  }

  private User combine(final UserDetailsFormRequest formRequest, final UserData userData) {
    User user = userDetailsFormRequestToUser.convert(formRequest);
    user.setId(userData.getId());
    user.setLocalAuthorityId(userData.getLocalAuthorityId());
    user.setRoleId(userData.getRoleId());
    return user;
  }

  @DeleteMapping(URL_USER_DETAILS)
  public String deleteUser(
      @PathVariable(PARAM_ID) int id,
      @ModelAttribute(MODEL_FORM_REQUEST) UserDetailsFormRequest formRequest,
      Model model) {
    try {
      userService.delete(formRequest.getLocalAuthorityId(), id);
      return REDIRECT_URL_MANAGE_USERS;
    } catch (Exception ex) {
      TemplateModelUtils.addCustomError(
          "error.updateUser.generalError.title",
          "error.updateUser.generalError.description",
          model);
      model.addAttribute(MODEL_ID, id);
      return TEMPLATE_USER_DETAILS;
    }
  }
}
