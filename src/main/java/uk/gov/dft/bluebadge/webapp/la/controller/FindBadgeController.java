package uk.gov.dft.bluebadge.webapp.la.controller;

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
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
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
  private SecurityUtils securityUtils;

  @Autowired
  public FindBadgeController(BadgeService badgeService, SecurityUtils securityUtils) {
    this.badgeService = badgeService;
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
    session.setAttribute("findBadgeBy", findBadgeBy);
    session.setAttribute("searchTerm", searchTerm);

    return REDIRECT_FIND_BADGE_SEARCH_RESULTS;
  }

  @PreAuthorize("hasAuthority('PERM_VIEW_BADGE_DETAILS_ZIP')")
  @GetMapping(value = URL_EXPORT_ALL_LA_BADGES, produces = "application/zip")
  public ResponseEntity<byte[]> exportAllLaBadges(final HttpServletResponse response) {
    String localAuthorityShortCode = securityUtils.getCurrentLocalAuthorityShortCode();
    return badgeService.exportBadgesByLa(localAuthorityShortCode);
  }
}
