package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface ManageUsersController {
  String showManageUsers(Model model, HttpSession session);
}
