package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserControllerImpl {

  @GetMapping("/user/showLogin")
  public String showLogin(Long id, Model model) {
    return "user/login";
  }

  @PostMapping("/user/login")
  public String login() {
    // login
    return "redirect:/user/welcome";
  }

  @GetMapping("/user/logout")
  public String logout() {
    // logout
    return "redirect:/user/loggedout";
  }

  @GetMapping("/user/showLoggedout")
  public String showLoggedOut() {
    return "user/loggedout";
  }

  @GetMapping("/user/showExpiredsession")
  public String showExpiredSession() {
    return "user/expiredsession";
  }

  @GetMapping("/user/showWelcome")
  public String showWelcomePage() {
    return "user/welcome";
  }

  @GetMapping("/user/showAccessDenied")
  public String showAccessDenied() {
    return "user/accessDenied";
  }
}
