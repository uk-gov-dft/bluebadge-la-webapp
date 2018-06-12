package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ManageUsersFormRequest;

import uk.gov.dft.bluebadge.webapp.la.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class ManageUsersController {

  public static final String URL_MANAGE_USERS = "/manage-users";
  public static final String REDIRECT_URL_MANAGE_USERS = "redirect:" + URL_MANAGE_USERS;
  public static final String TEMPLATE_MANAGE_USERS = "manage-users";

  private static final Logger logger = LoggerFactory.getLogger(ManageUsersController.class);

  private final UserService userService;
  private final SecurityUtils securityUtils;

  @Autowired
  public ManageUsersController(UserService userService, SecurityUtils securityUtils) {
    this.userService = userService;
    this.securityUtils = securityUtils;
  }

  @GetMapping(URL_MANAGE_USERS)
  public String manageUsers(@ModelAttribute final ManageUsersFormRequest formRequest, Model model) {
    UserData user = securityUtils.getCurrentUserDetails();

    List<User> allUsers = userService.find(user.getLocalAuthorityId()).getData().getUsers();
    List<User> users = Lists.newArrayList();

    String trimmedSearch = StringUtils.trimToEmpty(formRequest.getSearch());
    if (StringUtils.isEmpty(trimmedSearch)) {
      users.addAll(allUsers);
    } else {
      users.addAll(
          userService.find(user.getLocalAuthorityId(), trimmedSearch).getData().getUsers());
    }

    model.addAttribute("search", trimmedSearch);
    model.addAttribute("users", users);
    model.addAttribute("allUsersSize", allUsers.size());
    if (!StringUtils.isEmpty(trimmedSearch)) {
      model.addAttribute("searchCount", users.size());
    }
    return TEMPLATE_MANAGE_USERS;
  }
}
