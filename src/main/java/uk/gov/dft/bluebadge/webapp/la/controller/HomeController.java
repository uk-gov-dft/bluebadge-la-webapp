package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  public static final String URL = "/";

  public static final String TEMPLATE = "home";

  @GetMapping(URL)
  public String show() {
    return TEMPLATE;
  }
}
