package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FindBadgeSearchResultsController {

  public static final String URL = "/find-a-badge/search-results";

  private static final String TEMPLATE = "find-a-badge/search-results";

  private static final String REDIRECT_FIND_BADGE = "redirect" + FindBadgeController.URL;

  @GetMapping(URL)
  public String show(Model model, RedirectAttributes redirectAttributes) {

    List<HashMap<String, String>> results =
        (List<HashMap<String, String>>) redirectAttributes.getFlashAttributes().get("results");
    if (results == null) {
      return REDIRECT_FIND_BADGE;
    }

    model.addAttribute("results", results);
    return TEMPLATE;
  }
}
