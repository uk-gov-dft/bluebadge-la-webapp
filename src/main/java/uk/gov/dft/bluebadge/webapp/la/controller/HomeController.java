package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface HomeController {

  String showHome(Model model, HttpSession session);
}
