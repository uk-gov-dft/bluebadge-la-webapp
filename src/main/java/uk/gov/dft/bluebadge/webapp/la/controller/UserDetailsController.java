package uk.gov.dft.bluebadge.webapp.la.controller;

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
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.UserDetailsFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

import java.util.UUID;

@Controller
@Slf4j
public class UserDetailsController {

  private static final String TEMPLATE_USER_DETAILS = "manage-users/user-details";
  private static final String URL_USER_DETAILS = "/manage-users/user-details/{uuid}";
  private static final String REDIRECT_URL_MANAGE_USERS =
      "redirect:" + ManageUsersController.URL_MANAGE_USERS;
  private static final String PARAM_ID = "uuid";
  private static final String MODEL_FORM_REQUEST = "formRequest";
  private static final String MODEL_ID = "uuid";
  private static final String URL_REQUEST_RESET_EMAIL = "/manage-users/request***REMOVED***-reset/{uuid}";
  private UserService userService;

  private UserDetailsFormRequestToUser userDetailsFormRequestToUser;

  @Autowired
  public UserDetailsController(
      UserService userService, UserDetailsFormRequestToUser userDetailsFormRequestToUser) {
    this.userService = userService;
    this.userDetailsFormRequestToUser = userDetailsFormRequestToUser;
  }

  @GetMapping(URL_USER_DETAILS)
  public String showUserDetails(
      @PathVariable(PARAM_ID) UUID uuid,
      @ModelAttribute(MODEL_FORM_REQUEST) final UserDetailsFormRequest formRequest,
      Model model) {
    User user = userService.retrieve(uuid);
    formRequest.setLocalAuthorityShortCode(user.getLocalAuthorityShortCode());
    formRequest.setEmailAddress(user.getEmailAddress());
    formRequest.setName(user.getName());
    model.addAttribute(MODEL_ID, uuid);
    return TEMPLATE_USER_DETAILS;
  }

  @PostMapping(URL_USER_DETAILS)
  public String updateUserDetails(
      @PathVariable(PARAM_ID) UUID uuid,
      @ModelAttribute(MODEL_FORM_REQUEST) UserDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    try {
      User user = combine(formRequest, userService.retrieve(uuid));
      userService.update(user);
      return REDIRECT_URL_MANAGE_USERS;
    } catch (BadRequestException e) {
      ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);
      return TEMPLATE_USER_DETAILS;
    }
  }

  @DeleteMapping(URL_USER_DETAILS)
  public String deleteUser(
      @PathVariable(PARAM_ID) UUID uuid,
      @ModelAttribute(MODEL_FORM_REQUEST) UserDetailsFormRequest formRequest,
      Model model) {
    try {
      userService.delete(uuid);
      return REDIRECT_URL_MANAGE_USERS;
    } catch (Exception ex) {
      TemplateModelUtils.addCustomError(
          "error.deleteUser.generalError.title",
          "error.deleteUser.generalError.description",
          model);
      model.addAttribute(MODEL_ID, uuid);
      return TEMPLATE_USER_DETAILS;
    }
  }

  private User combine(final UserDetailsFormRequest formRequest, final User userData) {
    User user = userDetailsFormRequestToUser.convert(formRequest);
    user.setUuid(userData.getUuid());
    user.setLocalAuthorityShortCode(userData.getLocalAuthorityShortCode());
    user.setRoleId(userData.getRoleId());
    return user;
  }

  @PostMapping(URL_REQUEST_RESET_EMAIL)
  public String requestPasswordReset(
      @PathVariable(PARAM_ID) UUID uuid,
      @ModelAttribute(MODEL_FORM_REQUEST) UserDetailsFormRequest formRequest,
      Model model) {

    userService.requestPasswordReset(uuid);
    return REDIRECT_URL_MANAGE_USERS;
  }
}
