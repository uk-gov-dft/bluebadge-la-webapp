package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;

@Controller
public class FindBadgeSearchResultsController {

  public static final String URL = "/find-a-badge/search-results";

  private static final String TEMPLATE = "find-a-badge/search-results";

  @GetMapping(URL)
  public String show(Model model, HttpSession session) {
    String searchTerm = (String) session.getAttribute("searchTerm");
    List<FindBadgeSearchResultViewModel> results =
        (List<FindBadgeSearchResultViewModel>) session.getAttribute("results");

    model.addAttribute("searchTerm", (searchTerm == null ? "" : searchTerm));
    model.addAttribute("results", (results == null ? Lists.newArrayList() : results));
    return TEMPLATE;
  }
}
