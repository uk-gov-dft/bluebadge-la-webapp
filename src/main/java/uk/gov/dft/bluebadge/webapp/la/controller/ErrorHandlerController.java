package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ErrorHandlerController {

  public static final String ERROR_500_URL = "/something-went-wrong";
  public static final String ERROR_500_TEMPLATE = "error/500";

  @GetMapping(ERROR_500_URL)
  public String show(Model model) {
    return ERROR_500_TEMPLATE;
  }
}
