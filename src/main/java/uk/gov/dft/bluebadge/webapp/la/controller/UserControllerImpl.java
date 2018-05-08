package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralControllerException;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralServiceException;

@Controller
public class UserControllerImpl {

  public static final String URL_LOGIN = "/user/login";
  public static final String URL_LOGOUT = "/user/logout";
  public static final String URL_SHOW_ACCESS_DENIED = "/user/showAccessDenied";
  public static final String URL_SHOW_EXPIREDSESSION = "/user/showExpiredSession";
  public static final String URL_SHOW_LOGGEDOUT = "/user/showLoggedout";
  public static final String URL_SHOW_LOGIN = "/user/showLogin";
  public static final String URL_SHOW_WELCOME = "/user/showWelcome";

  public static final String TEMPLATE_ACCESS_DENIED = "user/accessDenied";
  public static final String TEMPLATE_EXPIREDSESSION = "user/expiredSession";
  public static final String TEMPLATE_LOGGEDOUT = "user/loggedout";
  public static final String TEMPLATE_WELCOME = "user/welcome";

  @GetMapping(URL_SHOW_LOGIN)
  public String showLogin(Model model) {
    return "user/login";
  }

  @PostMapping(URL_LOGIN)
  public String login() {
    try {
      return "redirect:" + URL_SHOW_WELCOME;
    } catch (GeneralServiceException ex) {
      throw new GeneralControllerException("There was a general controller exception", ex);
    }
  }

  @GetMapping(URL_LOGOUT)
  public String logout() {
    try {
      return "redirect:" + URL_SHOW_LOGGEDOUT;
    } catch (GeneralServiceException ex) {
      throw new GeneralControllerException("There was a general controller exception", ex);
    }
  }

  @GetMapping(URL_SHOW_LOGGEDOUT)
  public String showLoggedOut() {
    return TEMPLATE_LOGGEDOUT;
  }

  @GetMapping(URL_SHOW_EXPIREDSESSION)
  public String showExpiredSession() {
    return TEMPLATE_EXPIREDSESSION;
  }

  @GetMapping(URL_SHOW_WELCOME)
  public String showWelcomePage() {
    return TEMPLATE_WELCOME;
  }

  @GetMapping(URL_SHOW_ACCESS_DENIED)
  public String showAccessDenied() {
    return TEMPLATE_ACCESS_DENIED;
  }
}
