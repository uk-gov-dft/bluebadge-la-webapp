package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CredentialsUpdatedController {

  public static final String URL = "/credentials/updated";

  private static final String TEMPLATE = "credentials/updated";

  @GetMapping(URL)
  public String show() {
    return TEMPLATE;
  }
}
