package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.ui.Model;

public interface UserController {

  String showLogin(Model model);

  String login();

  String logout();

  String showLoggedOut();

  String showExpiredSession();

  String showWelcomePage();

  String showAccessDenied();
}
