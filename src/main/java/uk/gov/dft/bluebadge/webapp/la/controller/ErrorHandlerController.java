package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ErrorHandlerController {

  public static final String ERROR_500_URL = "/something-went-wrong";
  private static final String ERROR_500_TEMPLATE = "error/500";
  public static final String START_AGAIN_URL = "/start-again";
  private static final String START_AGAIN_TEMPLATE = "error/start-again";

  @GetMapping(ERROR_500_URL)
  public String show(Model model) {
    return ERROR_500_TEMPLATE;
  }

  @GetMapping(START_AGAIN_URL)
  public String startAgain() {
    return START_AGAIN_TEMPLATE;
  }
}
