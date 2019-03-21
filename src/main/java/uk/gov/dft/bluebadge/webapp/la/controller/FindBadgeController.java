package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeSummaryToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class FindBadgeController {

  public static final String URL_FIND_BADGE = "/manage-badges";
  private static final String URL_EXPORT_ALL_LA_BADGES = "/manage-badges/export-all-la-badges";

  private static final String TEMPLATE = "manage-badges/index";

  private static final String REDIRECT_FIND_BADGE_SEARCH_RESULTS =
      "redirect:" + FindBadgeSearchResultsController.URL;

  private BadgeService badgeService;
  private BadgeToFindBadgeSearchResultViewModel converterToViewModel;
  private BadgeSummaryToFindBadgeSearchResultViewModel badgeSummaryToViewModelConvertor;
  private SecurityUtils securityUtils;

  @Autowired
  public FindBadgeController(
      BadgeService badgeService,
      BadgeToFindBadgeSearchResultViewModel converterToViewModel,
      BadgeSummaryToFindBadgeSearchResultViewModel badgeSummaryToViewModel,
      SecurityUtils securityUtils) {
    this.badgeService = badgeService;
    this.converterToViewModel = converterToViewModel;
    this.badgeSummaryToViewModelConvertor = badgeSummaryToViewModel;
    this.securityUtils = securityUtils;
  }

  @GetMapping(URL_FIND_BADGE)
  public String show(
      @ModelAttribute("formRequest") FindBadgeFormRequest formRequest, HttpSession session) {
    // Reset previous search results
    session.removeAttribute("searchTerm");
    session.removeAttribute("results");

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

    String findBadgeBy = formRequest.getFindBadgeBy();
    String searchTerm = formRequest.getSearchTerm();
    List<FindBadgeSearchResultViewModel> results = Lists.newArrayList();

    switch (findBadgeBy) {
      case "badgeNumber":
        FindBadgeSearchResultViewModel badge = findBadgeByNumber(searchTerm);
        if (badge != null) results.add(badge);
        break;
      case "postcode":
        results.addAll(findBadgeByPostCode(searchTerm));
        break;
      case "name":
        results.addAll(findBadgesByName(searchTerm));
        break;
      default:
        log.error(
            "Attempting to find a badge by:{}, expected values are (badgeNumber, postcode or name)",
            findBadgeBy);
        break;
    }

    session.setAttribute("searchTerm", searchTerm);
    session.setAttribute("results", results);

    return REDIRECT_FIND_BADGE_SEARCH_RESULTS;
  }

  @PreAuthorize("hasAuthority('PERM_VIEW_BADGE_DETAILS_ZIP')")
  @GetMapping(value = URL_EXPORT_ALL_LA_BADGES, produces = "application/zip")
  public ResponseEntity<byte[]> exportAllLaBadges(final HttpServletResponse response) {
    String localAuthorityShortCode = securityUtils.getCurrentLocalAuthorityShortCode();
    return badgeService.exportBadgesByLa(localAuthorityShortCode);
  }

  private FindBadgeSearchResultViewModel findBadgeByNumber(String searchTerm) {
    Optional<Badge> result = badgeService.retrieve(searchTerm);

    if (result.isPresent()) {
      return converterToViewModel.convert(result.get());
    }

    return null;
  }

  private List<FindBadgeSearchResultViewModel> findBadgeByPostCode(String searchTerm) {

    if (!StringUtils.isEmpty(searchTerm)) {
      searchTerm = searchTerm.replaceAll("\\s+", "");
      List<BadgeSummary> result = badgeService.findBadgeByPostcode(searchTerm);
      return convertBadgeSummaryToViewModel(result);
    }

    return Lists.newArrayList();
  }

  private List<FindBadgeSearchResultViewModel> findBadgesByName(String searchTerm) {
    List<BadgeSummary> result = badgeService.findBadgeByName(searchTerm);
    return convertBadgeSummaryToViewModel(result);
  }

  private List<FindBadgeSearchResultViewModel> convertBadgeSummaryToViewModel(
      List<BadgeSummary> badgeSummary) {
    return badgeSummary
        .stream()
        .map(badge -> badgeSummaryToViewModelConvertor.convert(badge))
        .collect(Collectors.toList());
  }
}
