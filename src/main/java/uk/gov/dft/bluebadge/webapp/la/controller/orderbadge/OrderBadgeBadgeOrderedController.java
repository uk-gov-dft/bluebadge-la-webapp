package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.exceptions.InvalidSessionException;

@Slf4j
@Controller
public class OrderBadgeBadgeOrderedController {
  public static final String URL = "/order-a-badge/badge-ordered";

  public static final String TEMPLATE = "order-a-badge/badge-ordered";

  @GetMapping(URL)
  public String show(Model model) {
    if (!model.containsAttribute("badgeNumbers")) {
      throw new InvalidSessionException(
          "Badge numbers attr not on model", OrderBadgeIndexController.ORDER_BADGE_RESET_URL);
    }
    return TEMPLATE;
  }
}
