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

  private static final String MODEL_SEARCH_TERM = "searchTerm";
  private static final String MODEL_RESULTS = "results";

  @GetMapping(URL)
  public String show(Model model, HttpSession session) {
    String searchTerm = (String) session.getAttribute(MODEL_SEARCH_TERM);
    List<FindBadgeSearchResultViewModel> results =
        (List<FindBadgeSearchResultViewModel>) session.getAttribute(MODEL_RESULTS);

    model.addAttribute(MODEL_SEARCH_TERM, (searchTerm == null ? "" : searchTerm));
    model.addAttribute(MODEL_RESULTS, (results == null ? Lists.newArrayList() : results));
    return TEMPLATE;
  }
}
