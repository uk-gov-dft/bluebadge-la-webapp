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
import uk.gov.dft.bluebadge.model.usermanagement.UserData;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.CreateANewUserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CreateANewUserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils;
import uk.gov.dft.bluebadge.webapp.la.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class CreateANewUserController {

  private static final Logger logger = LoggerFactory.getLogger(CreateANewUserController.class);

  public static final String URL_CREATE_A_NEW_USER = "/manage-users/create-a-new-user";

  public static final String TEMPLATE_CREATE_A_NEW_USER = "manage-users/create-a-new-user";

  public static final String REDIRECT_URL_MANAGE_USERS =
      "redirect:" + ManageUsersController.URL_MANAGE_USERS;

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
  public String showCreateANewUser(
      @ModelAttribute("formRequest") final CreateANewUserFormRequest formRequest) {
    return TEMPLATE_CREATE_A_NEW_USER;
  }

  @PostMapping(URL_CREATE_A_NEW_USER)
  public String createANewUser(
      @ModelAttribute("formRequest") CreateANewUserFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    try {
      UserData signedInUser = securityUtils.getCurrentUserDetails();
      User user =
          createANewUserRequest2User
              .convert(formRequest)
              .localAuthorityId(signedInUser.getLocalAuthorityId())
              .roleId(signedInUser.getRoleId());
      UserResponse userResponse = userService.create(user);
      return ErrorHandlingUtils.handleError(
          userResponse.getError(),
          REDIRECT_URL_MANAGE_USERS,
          TEMPLATE_CREATE_A_NEW_USER,
          bindingResult,
          model);
    } catch (Exception ex) {
      TemplateModelUtils.addCustomError(
          "error.createUser.generalError.title",
          "error.createUser.generalError.description",
          model);
      return TEMPLATE_CREATE_A_NEW_USER;
    }
  }
}
