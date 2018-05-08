package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.ui.Model;

public interface HomeController {

  String showHome(String email, Model model);
}
