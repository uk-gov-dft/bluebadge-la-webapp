package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CookiesController {

  public static final String TEMPLATE = "cookies";
  public static final String URL = "/cookies";

  @GetMapping(URL)
  public String show() {
    return TEMPLATE;
  }
}
