package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public String show(@PathVariable(PARAM_BADGE_NUMBER) String badgeNumber, Model model) {
    Optional<Badge> badge = badgeService.retrieve(badgeNumber);

    Badge badgeDetails = badge.orElseThrow(() -> new NotFoundException(new CommonResponse()));

    Boolean canBeCancelled = "ISSUED".equals(badgeDetails.getStatusCode()) || "ORDERED".equals(badgeDetails.getStatusCode());
    model.addAttribute("canBeCancelled", canBeCancelled);

    BadgeDetailsViewModel viewModel = toViewModelConverter.convert(badgeDetails);
    model.addAttribute("partyTypeCode", badgeDetails.getParty().getTypeCode());
    model.addAttribute("badge", viewModel);
    return TEMPLATE;
  }
}
