package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FindBadgeSearchResultsController {

  public static final String URL = "/find-a-badge/search-results";

  private static final String TEMPLATE = "find-a-badge/search-results";

  @GetMapping(URL)
  public String show(Model model) {

    List<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();

    model.addAttribute("results", results);
    return TEMPLATE;
  }
}
