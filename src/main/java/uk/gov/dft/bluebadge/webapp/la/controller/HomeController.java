package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;

@Controller
public class HomeController {

  private final SecurityUtils securityUtils;
  public static final String URL = "/";
  private static final String REDIRECT = "redirect:";

  @Autowired
  HomeController(SecurityUtils securityUtils) {
    this.securityUtils = securityUtils;
  }

  @GetMapping(URL)
  public String show() {

    // default action
    if (securityUtils.isPermitted(Permissions.FIND_USERS)) {
      return REDIRECT + ManageUsersController.URL_MANAGE_USERS;
    }

    // plan b
    if (securityUtils.isPermitted(Permissions.FIND_APPLICATION)) {
      return REDIRECT + ApplicationsController.URL;
    }

    // have neither of the above permissions
    return REDIRECT + FindBadgeController.URL_FIND_BADGE;
  }
}
