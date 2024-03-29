package uk.gov.dft.bluebadge.webapp.la.controller;

import static uk.gov.dft.bluebadge.webapp.la.controller.ManageUsersController.URL_MANAGE_USERS;

import java.util.List;
import java.util.UUID;
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
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.UserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Controller
@Slf4j
public class UserDetailsController {

  private static final String TEMPLATE_USER_DETAILS = "manage-users/user-details";
  private static final String URL_USER_DETAILS = "/manage-users/user-details/{uuid}";
  private static final String REDIRECT_URL_MANAGE_USERS = "redirect:" + URL_MANAGE_USERS;
  private static final String PARAM_ID = "uuid";
  private static final String MODEL_FORM_REQUEST = "formRequest";
  private static final String URL_REQUEST_RESET_EMAIL =
      "/manage-users/request***REMOVED***-reset/{uuid}";

  private final UserService userService;
  private final UserFormRequestToUser userConverter;
  private final ReferenceDataService referenceDataService;

  @Autowired
  public UserDetailsController(
      UserService userService,
      UserFormRequestToUser userConverter,
      ReferenceDataService referenceDataService) {
    this.userService = userService;
    this.userConverter = userConverter;
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL_USER_DETAILS)
  public String showUserDetails(
      @PathVariable(PARAM_ID) UUID uuid,
      @ModelAttribute(MODEL_FORM_REQUEST) final UserFormRequest formRequest,
      Model model) {
    User user = userService.retrieve(uuid);
    populateForm(formRequest, user);
    model.addAttribute(PARAM_ID, uuid);

    return TEMPLATE_USER_DETAILS;
  }

  private void populateForm(final UserFormRequest formRequest, User user) {
    formRequest.setLocalAuthorityShortCode(user.getLocalAuthorityShortCode());
    formRequest.setEmailAddress(user.getEmailAddress());
    formRequest.setName(user.getName());
    formRequest.setRole(Role.getById(user.getRoleId()));
  }

  @PostMapping(URL_USER_DETAILS)
  public String updateUserDetails(
      @PathVariable(PARAM_ID) UUID uuid,
      @ModelAttribute(MODEL_FORM_REQUEST) UserFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    try {
      User user = userConverter.convert(formRequest);
      user.setUuid(uuid);
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
      @ModelAttribute(MODEL_FORM_REQUEST) UserFormRequest formRequest,
      Model model) {
    try {
      userService.delete(uuid);
      return REDIRECT_URL_MANAGE_USERS;
    } catch (Exception ex) {
      log.error("Exception while trying to delete user. ", ex);
      TemplateModelUtils.addCustomError(
          "error.deleteUser.generalError.title",
          "error.deleteUser.generalError.description",
          model);
      model.addAttribute(PARAM_ID, uuid);
      return TEMPLATE_USER_DETAILS;
    }
  }

  @PostMapping(URL_REQUEST_RESET_EMAIL)
  public String requestPasswordReset(
      @PathVariable(PARAM_ID) UUID uuid,
      @ModelAttribute(MODEL_FORM_REQUEST) UserFormRequest formRequest,
      Model model) {

    userService.requestPasswordReset(uuid);
    return REDIRECT_URL_MANAGE_USERS;
  }

  @ModelAttribute("permissionsOptions")
  public List<ReferenceData> permissionsOptions() {
    return referenceDataService.displayedUserRoles();
  }

  @ModelAttribute("localAuthorities")
  public List<ReferenceData> localAuthorities() {
    return referenceDataService.retrieveBadgeLocalAuthorities();
  }
}
