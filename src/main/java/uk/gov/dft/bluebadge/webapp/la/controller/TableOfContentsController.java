package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableOfContentsController {

  @GetMapping("development/tableOfContents")
  public String showTableOfContents() {
    return "development/tableOfContents";
  }
}
