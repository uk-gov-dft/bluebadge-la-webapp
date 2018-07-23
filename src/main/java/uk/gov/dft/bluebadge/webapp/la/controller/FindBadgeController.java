package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeSummaryToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Controller
public class FindBadgeController {

  public static final String URL_FIND_BADGE = "/find-a-badge";

  private static final String TEMPLATE = "find-a-badge/index";

  private static final String REDIRECT_FIND_BADGE_SEARCH_RESULTS =
      "redirect:" + FindBadgeSearchResultsController.URL;

  private BadgeService badgeService;
  private BadgeToFindBadgeSearchResultViewModel converterToViewModel;
  private BadgeSummaryToFindBadgeSearchResultViewModel badgeSummaryToViewModelConvertor;

  @Autowired
  public FindBadgeController(
      BadgeService badgeService,
      BadgeToFindBadgeSearchResultViewModel converterToViewModel,
      BadgeSummaryToFindBadgeSearchResultViewModel badgeSummaryToViewModel) {
    this.badgeService = badgeService;
    this.converterToViewModel = converterToViewModel;
    this.badgeSummaryToViewModelConvertor = badgeSummaryToViewModel;
  }

  @GetMapping(URL_FIND_BADGE)
  public String show(@ModelAttribute("formRequest") FindBadgeFormRequest formRequest) {
    return TEMPLATE;
  }

  @PostMapping(URL_FIND_BADGE)
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
      Optional<Badge> result = badgeService.retrieve(searchTerm);
      if (result.isPresent()) {
        FindBadgeSearchResultViewModel viewModel = converterToViewModel.convert(result.get());
        results.add(viewModel);
      }
    }

    if ("postcode".equalsIgnoreCase(formRequest.getFindBadgeBy())) {
      List<BadgeSummary> result = badgeService.findBadgesByPostcode(searchTerm);
      results.addAll(
          result
              .stream()
              .map(
                  r -> {
                    return badgeSummaryToViewModelConvertor.convert(r);
                  })
              .collect(Collectors.toList()));
    }

    redirectAttributes.addFlashAttribute("searchTerm", searchTerm);
    redirectAttributes.addFlashAttribute("results", results);

    return REDIRECT_FIND_BADGE_SEARCH_RESULTS;
  }
}
