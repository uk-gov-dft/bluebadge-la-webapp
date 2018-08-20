package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewApplicationsController {

  public static final String URL = "/new-applications";

  public static final String TEMPLATE = "new-applications";

  @GetMapping(URL)
  public String show() {
    return TEMPLATE;
  }
}
