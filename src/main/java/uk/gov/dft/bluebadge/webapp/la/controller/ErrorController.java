package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {

  public static final String URL = "/unexpected-error";

  public static final String TEMPLATE = "unexpected-error";

  @GetMapping(URL)
  public String show(@RequestParam(name = "message") String message, Model model) {
    model.addAttribute("errorMessage", message);
    return TEMPLATE;
  }
}
