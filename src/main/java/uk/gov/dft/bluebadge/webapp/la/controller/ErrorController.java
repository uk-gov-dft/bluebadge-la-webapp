package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ErrorController {

  public static final String URL = "/unexpected-error";

  public static final String TEMPLATE = "unexpected-error";

  @GetMapping(URL)
  public String show(Model model) {
    Exception ex = (Exception) model.asMap().get("exception");
    model.addAttribute("errorMessage", ex.getClass().getName());
    model.addAttribute("exceptionMessage", ex.getMessage());
    log.debug("exception [()]", ex);
    return TEMPLATE;
  }
}
