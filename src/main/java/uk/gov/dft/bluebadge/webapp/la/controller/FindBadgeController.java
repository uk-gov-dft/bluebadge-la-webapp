package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeEntityToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Controller
public class FindBadgeController {

  public static final String URL = "/find-a-badge";

  private static final String TEMPLATE = "find-a-badge/index";

  private static final String REDIRECT_FIND_BADGE_SEARCH_RESULTS =
      "redirect:" + FindBadgeSearchResultsController.URL;

  private BadgeService badgeService;
  private BadgeEntityToFindBadgeSearchResultViewModel converterToViewModel;

  @Autowired
  public FindBadgeController(
      BadgeService badgeService, BadgeEntityToFindBadgeSearchResultViewModel converterToViewModel) {
    this.badgeService = badgeService;
    this.converterToViewModel = converterToViewModel;
  }

  @GetMapping(URL)
  public String show(@ModelAttribute("formRequest") FindBadgeFormRequest formRequest) {
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

    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }

    String searchTerm = formRequest.getSearchTerm();
    List<FindBadgeSearchResultViewModel> results = Lists.newArrayList();

    if ("badgeNumber".equalsIgnoreCase(formRequest.getFindBadgeBy())) {
      Optional<Badge> resultMaybe = badgeService.retrieve(searchTerm);
      if (resultMaybe.isPresent()) {
        FindBadgeSearchResultViewModel viewModel = converterToViewModel.convert(resultMaybe.get());
        results.add(viewModel);
      }
    }

    redirectAttributes.addFlashAttribute("searchTerm", searchTerm);
    redirectAttributes.addFlashAttribute("results", results);

    return REDIRECT_FIND_BADGE_SEARCH_RESULTS;
  }
}
