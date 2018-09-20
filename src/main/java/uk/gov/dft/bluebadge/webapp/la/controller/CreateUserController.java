package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.BBPrincipal;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.UserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Slf4j
@Controller
public class CreateUserController {

  private static final String URL_CREATE_A_NEW_USER = "/manage-users/create-a-new-user";

  private static final String TEMPLATE_CREATE_A_NEW_USER = "manage-users/create-a-new-user";

  private static final String REDIRECT_URL_MANAGE_USERS =
      "redirect:" + ManageUsersController.URL_MANAGE_USERS;

  private final UserService userService;
  private final UserFormRequestToUser userConverter;
  private final SecurityUtils securityUtils;

  @Autowired
  public CreateUserController(
      UserService userService, UserFormRequestToUser userConverter, SecurityUtils securityUtils) {
    this.userService = userService;
    this.userConverter = userConverter;
    this.securityUtils = securityUtils;
  }

  @GetMapping(URL_CREATE_A_NEW_USER)
  public String show(@ModelAttribute("formRequest") final UserFormRequest formRequest) {
    return TEMPLATE_CREATE_A_NEW_USER;
  }

  @PostMapping(URL_CREATE_A_NEW_USER)
  public String submit(
      @ModelAttribute("formRequest") UserFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    try {
      log.debug("Creating new user");
      BBPrincipal signedInUser = securityUtils.getCurrentAuth();
      User user = userConverter.convert(formRequest);
      user.setLocalAuthorityShortCode(signedInUser.getLocalAuthorityShortCode());
      user.setRoleId(Role.findForPrettyName(formRequest.getRoleName()).getRoleId());
      log.debug("Creating user for email {}, user: {}", user.getEmailAddress(), user.toString());
      userService.create(user);

      return REDIRECT_URL_MANAGE_USERS;
    } catch (BadRequestException e) {
      ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);
      return TEMPLATE_CREATE_A_NEW_USER;
    }
  }
}
