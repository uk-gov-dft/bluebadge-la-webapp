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

  @Autowired
  public HomeController(SecurityUtils securityUtils) {
    this.securityUtils = securityUtils;
  }

  @GetMapping(URL)
  public String show() {
    if (securityUtils.isPermitted(Permissions.FIND_USERS)) {
      return "redirect:" + ManageUsersController.URL_MANAGE_USERS;
    } else if (securityUtils.isPermitted(Permissions.FIND_APPLICATION)) {
      return "redirect:" + NewApplicationsController.URL;
    } else {
      return "redirect:" + FindBadgeController.URL_FIND_BADGE;
    }
  }
}
