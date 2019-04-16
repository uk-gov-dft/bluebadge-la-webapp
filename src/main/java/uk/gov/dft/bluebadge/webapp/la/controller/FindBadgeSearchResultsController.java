package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeSummaryToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class FindBadgeSearchResultsController {

  public static final String URL = "/manage-badges/search-results";

  private static final String TEMPLATE = "manage-badges/search-results";

  private static final String MODEL_SEARCH_TERM = "searchTerm";
  private static final String MODEL_FIND_BADGE_BY = "findBadgeBy";
  private static final String MODEL_RESULTS = "results";
  private static final String MODEL_PAGING_INFO = "pagingInfo";

  private BadgeService badgeService;
  private BadgeToFindBadgeSearchResultViewModel converterToViewModel;
  private BadgeSummaryToFindBadgeSearchResultViewModel badgeSummaryToViewModelConvertor;

  @Autowired
  public FindBadgeSearchResultsController(
      BadgeService badgeService,
      BadgeToFindBadgeSearchResultViewModel converterToViewModel,
      BadgeSummaryToFindBadgeSearchResultViewModel badgeSummaryToViewModel) {
    this.badgeService = badgeService;
    this.converterToViewModel = converterToViewModel;
    this.badgeSummaryToViewModelConvertor = badgeSummaryToViewModel;
  }

  @GetMapping(URL)
  public String show(
      Model model,
      HttpSession session,
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
      @RequestParam(value = "pageSize", required = false, defaultValue = "50") Integer pageSize) {
    String searchTerm = (String) session.getAttribute(MODEL_SEARCH_TERM);
    String findBadgeBy = (String) session.getAttribute(MODEL_FIND_BADGE_BY);

    PagingInfo pagingInfo = new PagingInfo();
    pagingInfo.setPageNum(pageNum);
    pagingInfo.setPageSize(pageSize);

    List<FindBadgeSearchResultViewModel> results = Lists.newArrayList();

    switch (findBadgeBy) {
      case "badgeNumber":
        FindBadgeSearchResultViewModel badge = findBadgeByNumber(searchTerm);
        if (badge != null) results.add(badge);
        break;
      case "postcode":
        if (!StringUtils.isBlank(searchTerm)) {
          searchTerm = searchTerm.replaceAll("\\s+", "");
          BadgesResponse result = badgeService.findBadgeByPostcode(searchTerm, pagingInfo);
          pagingInfo = result.getPagingInfo();
          results.addAll(convertBadgeSummaryToViewModel(result.getData()));
        }
        break;
      case "name":
        if (!StringUtils.isBlank(searchTerm)) {
          BadgesResponse result = badgeService.findBadgeByName(searchTerm, pagingInfo);
          pagingInfo = result.getPagingInfo();
          results.addAll(convertBadgeSummaryToViewModel(result.getData()));
        }
        break;
      default:
        log.error(
            "Attempting to find a badge by:{}, expected values are (badgeNumber, postcode or name)",
            findBadgeBy);
        break;
    }

    // Redirect to the single badge if only one is returned
    if (results.size() == 1) {
      return "redirect:/manage-badges/" + results.get(0).getBadgeNumber() + "?prev-step=find-badge";
    }

    model.addAttribute(MODEL_FIND_BADGE_BY, findBadgeBy == null ? "" : findBadgeBy);
    model.addAttribute(MODEL_SEARCH_TERM, searchTerm == null ? "" : searchTerm);
    model.addAttribute(MODEL_RESULTS, results);
    model.addAttribute(MODEL_PAGING_INFO, pagingInfo);

    return TEMPLATE;
  }

  private FindBadgeSearchResultViewModel findBadgeByNumber(String searchTerm) {
    Optional<Badge> result = badgeService.retrieve(searchTerm);

    if (result.isPresent()) {
      return converterToViewModel.convert(result.get());
    }

    return null;
  }

  private List<FindBadgeSearchResultViewModel> convertBadgeSummaryToViewModel(
      List<BadgeSummary> badgeSummary) {
    return badgeSummary
        .stream()
        .map(badge -> badgeSummaryToViewModelConvertor.convert(badge))
        .collect(Collectors.toList());
  }
}
