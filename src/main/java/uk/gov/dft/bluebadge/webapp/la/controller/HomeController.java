package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  public static final String URL_HOME = "/";

  public static final String TEMPLATE_HOME = "home";

  @GetMapping(URL_HOME)
  public String showHome(Model model) {
    return TEMPLATE_HOME;
  }
}
