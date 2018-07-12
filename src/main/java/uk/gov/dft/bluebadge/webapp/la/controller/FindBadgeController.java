package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;

@Controller
public class FindBadgeController {

  public static final String URL = "/find-a-badge";

  private static final String TEMPLATE = "find-a-badge/index";

  private static final String REDIRECT_FIND_BADGE_SEARCH_RESULTS =
      "redirect:" + FindBadgeSearchResultsController.URL;

  private static final String FORM_REQUEST_SESSION = "formRequest-find-badge";

  @GetMapping(URL)
  public String show(
      @ModelAttribute("formRequest") FindBadgeFormRequest formRequest, HttpSession session) {
    Object sessionFormRequest = session.getAttribute(FORM_REQUEST_SESSION);
    if (sessionFormRequest != null) {
      BeanUtils.copyProperties(sessionFormRequest, formRequest);
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @Valid @ModelAttribute("formRequest") FindBadgeFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session,
      RedirectAttributes redirectAttributes) {
    model.addAttribute("errorSummary", new ErrorViewModel());

    session.setAttribute(FORM_REQUEST_SESSION, formRequest);

    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }

    HashMap<String, String> data = new HashMap<String, String>();
    data.put("badgeNumber", "HAS67SDDS3");
    data.put("name", "Joe BLoggs");
    data.put("postCode", "M12 8N");
    data.put("council", "Manchester city council");
    data.put("expiry", "12/03/2018");
    data.put("status", "Active");

    List<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
    results.add(data);

    redirectAttributes.addFlashAttribute("results", results);

    return REDIRECT_FIND_BADGE_SEARCH_RESULTS;
  }
}
