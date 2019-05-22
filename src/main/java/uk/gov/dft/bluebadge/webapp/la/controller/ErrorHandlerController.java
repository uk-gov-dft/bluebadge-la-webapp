package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
public class ErrorHandlerController {

  public static final String ERROR_500_URL = "/something-went-wrong";
  public static final String ERROR_404_URL = "/not-found";
  public static final String ERROR_400_URL = "/bad-request";
  private static final String ERROR_500_TEMPLATE = "error/500";
  private static final String SPECIFIC_ERROR_TEMPLATE = "error/specificErrorPage";
  public static final String START_AGAIN_URL = "/start-again";
  private static final String START_AGAIN_TEMPLATE = "error/start-again";

  @GetMapping(ERROR_500_URL)
  public String show(Model model) {
    return ERROR_500_TEMPLATE;
  }

  @GetMapping({ERROR_404_URL, ERROR_400_URL})
  public String showNotFound(Model model, @ModelAttribute("errorCode") String errorCode) {
    model.addAttribute("errorTitle", errorCode + ".title");
    model.addAttribute("errorP1", errorCode + ".p1");
    model.addAttribute("errorP2", errorCode + ".p2");
    return SPECIFIC_ERROR_TEMPLATE;
  }

  @GetMapping(START_AGAIN_URL)
  public String startAgain() {
    return START_AGAIN_TEMPLATE;
  }
}
