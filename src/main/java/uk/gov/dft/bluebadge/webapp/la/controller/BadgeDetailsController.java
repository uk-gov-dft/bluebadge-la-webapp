package uk.gov.dft.bluebadge.webapp.la.controller;

import static uk.gov.dft.bluebadge.webapp.la.controller.FindBadgeController.URL_FIND_BADGE;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToBadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.BadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class BadgeDetailsController {
  public static final String URL = "/manage-badges/{badgeNumber}";
  private static final String URL_DELETE_BADGE = "/manage-badges/delete-badge/{badgeNumber}";
  public static final String REDIRECT_URL_MANAGE_BADGES = "redirect:" + URL_FIND_BADGE;

  private static final String TEMPLATE = "manage-badges/badge-details";

  private static final String PARAM_BADGE_NUMBER = "badgeNumber";

  private BadgeToBadgeDetailsViewModel toViewModelConverter;

  private BadgeService badgeService;

  @Autowired
  public BadgeDetailsController(
      final BadgeService badgeService, final BadgeToBadgeDetailsViewModel toViewModelConverter) {
    this.badgeService = badgeService;
    this.toViewModelConverter = toViewModelConverter;
  }

  @GetMapping(URL)
  public String show(
      @PathVariable(PARAM_BADGE_NUMBER) String badgeNumber,
      Model model,
      @RequestParam(name = "prev-step", required = false) String prevStep) {
    Optional<Badge> badge = badgeService.retrieve(badgeNumber);

    Badge badgeDetails = badge.orElseThrow(() -> new NotFoundException(new CommonResponse()));

    model.addAttribute("canBeCancelled", badgeDetails.canBeCancelled());
    model.addAttribute("canBeReplaced", badgeDetails.canBeReplaced());

    BadgeDetailsViewModel viewModel = toViewModelConverter.convert(badgeDetails);
    model.addAttribute("badge", viewModel);

    String backLink =
        null != prevStep && prevStep.equals("find-badge")
            ? "/manage-badges"
            : "/manage-badges/search-results";
    model.addAttribute("backLink", backLink);

    return TEMPLATE;
  }

  @PreAuthorize("hasAuthority('PERM_DELETE_BADGE')")
  @DeleteMapping(URL_DELETE_BADGE)
  public String deleteBadge(@PathVariable(PARAM_BADGE_NUMBER) String badgeNumber, Model model) {
    badgeService.deleteBadge(badgeNumber);
    return REDIRECT_URL_MANAGE_BADGES;
  }
}
