package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.BBPrincipal;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.UserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Slf4j
@Controller
public class CreateUserController {
  private static final String URL_CREATE_USER = "/manage-users/create-user";

  private static final String TEMPLATE_CREATE_USER = "manage-users/create-user";

  private static final String REDIRECT_URL_MANAGE_USERS =
      "redirect:" + ManageUsersController.URL_MANAGE_USERS;

  private final UserService userService;
  private final UserFormRequestToUser userConverter;
  private final SecurityUtils securityUtils;
  private final MessageSource messageSource;

  @Autowired
  public CreateUserController(
      UserService userService,
      UserFormRequestToUser userConverter,
      SecurityUtils securityUtils,
      MessageSource messageSource) {
    this.userService = userService;
    this.userConverter = userConverter;
    this.securityUtils = securityUtils;
    this.messageSource = messageSource;
  }

  @GetMapping(URL_CREATE_USER)
  public String show(@ModelAttribute("formRequest") final UserFormRequest formRequest) {
    return TEMPLATE_CREATE_USER;
  }

  @PostMapping(URL_CREATE_USER)
  public String submit(
      @Valid @ModelAttribute("formRequest") UserFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {

    try {
      if (bindingResult.hasErrors()) {
        throw new BadRequestException(new CommonResponse());
      }

      log.debug("Creating new user");
      BBPrincipal signedInUser = securityUtils.getCurrentAuth();
      User user = userConverter.convert(formRequest);
      user.setLocalAuthorityShortCode(signedInUser.getLocalAuthorityShortCode());
      user.setRoleId(Role.valueOf(formRequest.getRoleName()).getRoleId());
      log.debug("Creating user for email {}, user: {}", user.getEmailAddress(), user.toString());
      userService.create(user);

      return REDIRECT_URL_MANAGE_USERS;
    } catch (BadRequestException e) {
      ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);
      return TEMPLATE_CREATE_USER;
    }
  }

  @ModelAttribute("permissionsOptions")
  public List<ReferenceData> permissionsOptions() {
    ReferenceData admin =
        new ReferenceData()
            .description(
                messageSource.getMessage(
                    "label.user.permissions.administrator", null, Locale.ENGLISH))
            .shortCode(Role.LA_ADMIN.name());

    ReferenceData editor =
        new ReferenceData()
            .description(
                messageSource.getMessage("label.user.permissions.editor", null, Locale.ENGLISH))
            .shortCode(Role.LA_EDITOR.name());

    ReferenceData viewer =
        new ReferenceData()
            .description(
                messageSource.getMessage("label.user.permissions.viewer", null, Locale.ENGLISH))
            .shortCode(Role.LA_READ.name());

    return Lists.newArrayList(viewer, editor, admin);
  }
}
