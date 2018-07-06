package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;

@Controller
public class FindBadgeController {

  public static final String URL_FIND_A_BADGE = "/find-a-badge";
  public static final String URL_SEARCH_RESULTS = "/find-a-badge/search-results";

  public static final String TEMPLATE_FIND_A_BADGE = "find-a-badge/index";
  public static final String TEMPLATE_SEARCH_RESULTS = "find-a-badge/search-results";

  @GetMapping(URL_FIND_A_BADGE)
  public String show(@ModelAttribute("formRequest") FindBadgeFormRequest formRequest) {
    return TEMPLATE_FIND_A_BADGE;
  }

  @PostMapping(URL_FIND_A_BADGE)
  public String submit(
      @Valid @ModelAttribute("formRequest") FindBadgeFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    model.addAttribute("errorSummary", new ErrorViewModel());

    if (bindingResult.hasErrors()) {
      return TEMPLATE_FIND_A_BADGE;
    }

    HashMap<String, String> data = new HashMap<String, String>();
    data.put("badgeNumber", "HAS67SDDS3");
    data.put("name", "Joe BLoggs");
    data.put("postCode", "M12 8N");
    data.put("council", "Manchester city council");
    data.put("expiry", "12/03/2018");
    data.put("status", "Active");

    List<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
    //results.add(data);

    model.addAttribute("results", results);

    return TEMPLATE_SEARCH_RESULTS;
  }
}
