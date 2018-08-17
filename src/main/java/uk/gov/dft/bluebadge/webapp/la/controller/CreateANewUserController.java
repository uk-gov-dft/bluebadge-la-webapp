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
import uk.gov.dft.bluebadge.common.security.BBPrincipal;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.CreateANewUserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CreateANewUserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class CreateANewUserController {

  private static final Logger log = LoggerFactory.getLogger(CreateANewUserController.class);

  private static final String URL_CREATE_A_NEW_USER = "/manage-users/create-a-new-user";

  private static final String TEMPLATE_CREATE_A_NEW_USER = "manage-users/create-a-new-user";

  private static final String REDIRECT_URL_MANAGE_USERS =
      "redirect:" + ManageUsersController.URL_MANAGE_USERS;
  private static final Integer TODO_HARDCODED_CREATE_USER_ROLE_ID = 2;

  private final UserService userService;
  private final CreateANewUserFormRequestToUser createANewUserRequest2User;
  private final SecurityUtils securityUtils;

  @Autowired
  public CreateANewUserController(
      UserService userService,
      CreateANewUserFormRequestToUser createANewUserRequest2UserConverter,
      SecurityUtils securityUtils) {
    this.userService = userService;
    this.createANewUserRequest2User = createANewUserRequest2UserConverter;
    this.securityUtils = securityUtils;
  }

  @GetMapping(URL_CREATE_A_NEW_USER)
  public String show(@ModelAttribute("formRequest") final CreateANewUserFormRequest formRequest) {
    return TEMPLATE_CREATE_A_NEW_USER;
  }

  @PostMapping(URL_CREATE_A_NEW_USER)
  public String submit(
      @ModelAttribute("formRequest") CreateANewUserFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    try {
      log.debug("Creating new user");
      BBPrincipal signedInUser = securityUtils.getCurrentAuth();
      User user = createANewUserRequest2User.convert(formRequest);
      user.setLocalAuthorityShortCode(signedInUser.getLocalAuthorityShortCode());
      user.setRoleId(TODO_HARDCODED_CREATE_USER_ROLE_ID);
      log.debug("Creating user for email {}", user.getEmailAddress());
      userService.create(user);
      return REDIRECT_URL_MANAGE_USERS;
    } catch (BadRequestException e) {
      ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);
      return TEMPLATE_CREATE_A_NEW_USER;
    }
  }
}
