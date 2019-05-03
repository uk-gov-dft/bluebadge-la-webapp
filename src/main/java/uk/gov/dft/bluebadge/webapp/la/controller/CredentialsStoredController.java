package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CredentialsStoredController {

  public static final String URL = "/credentials/stored";

  private static final String TEMPLATE = "credentials/stored";

  @GetMapping(URL)
  public String show() {
    return TEMPLATE;
  }
}
