package uk.gov.dft.bluebadge.webapp.la.controller;

import static uk.gov.dft.bluebadge.common.security.Role.DFT_ADMIN;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.BBPrincipal;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.UserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

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
  private final ReferenceDataService referenceDataService;

  @Autowired
  public CreateUserController(
      UserService userService,
      UserFormRequestToUser userConverter,
      SecurityUtils securityUtils,
      ReferenceDataService referenceDataService) {
    this.userService = userService;
    this.userConverter = userConverter;
    this.securityUtils = securityUtils;
    this.referenceDataService = referenceDataService;
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
    log.debug("Creating new user");

    try {
      if (!DFT_ADMIN.equals(formRequest.getRole())
          && securityUtils.isPermitted(Permissions.CREATE_DFT_USER)
          && StringUtils.isEmpty(formRequest.getLocalAuthorityShortCode())) {
        bindingResult.rejectValue(
            "localAuthorityShortCode", "NotNull.user.localAuthorityShortCode");
      }

      if (bindingResult.hasErrors()) {
        throw new BadRequestException(new CommonResponse());
      }

      User user = userConverter.convert(formRequest);
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
    return referenceDataService.displayedUserRoles();
  }

  @ModelAttribute("localAuthorities")
  public List<ReferenceData> localAuthorities() {
    return referenceDataService.retrieveBadgeLocalAuthorities();
  }
}
