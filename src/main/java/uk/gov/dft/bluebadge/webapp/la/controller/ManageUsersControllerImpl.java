package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class ManageUsersControllerImpl implements ManageUsersController {

  public static final String URL_MANAGE_USERS = "/manage-users";
  public static final String TEMPLATE_MANAGE_USERS = "manage-users";
  public static final String REDIRECT_URL_SIGN_IN = "redirect:" + SignInControllerImpl.URL_SIGN_IN;

  private static final Logger logger = LoggerFactory.getLogger(ManageUsersControllerImpl.class);

  private UserService userService;

  @Autowired
  public ManageUsersControllerImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  @GetMapping(URL_MANAGE_USERS)
  public String showManageUsers(Model model, HttpSession session) {
    if (!SignInUtils.isSignedIn(session)) {
      return REDIRECT_URL_SIGN_IN;
    }
    User user = SignInUtils.getUserSignedIn(session).get();
    List<User> users = userService.findAll(user.getLocalAuthorityId());
    model.addAttribute("users", users);
    return TEMPLATE_MANAGE_USERS;
  }
}
