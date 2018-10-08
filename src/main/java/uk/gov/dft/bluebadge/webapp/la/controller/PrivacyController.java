package uk.gov.
        dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivacyController {

  public static final String TEMPLATE = "privacy";
  public static final String URL = "/privacy-notice";

  @GetMapping(URL)
  public String show() {
    return TEMPLATE;
  }
}
