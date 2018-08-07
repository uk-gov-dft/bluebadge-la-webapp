package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class OrderBadgeBadgeOrderedController {
  public static final String URL = "/order-a-badge/person/badge-ordered";

  public static final String TEMPLATE = "order-a-badge/person/badge-ordered";

  @GetMapping(URL)
  public String show(Model model) {
    String badgeNumber = (String) model.asMap().get("badgeNumber");
    model.addAttribute("badgeNumber", badgeNumber);
    return TEMPLATE;
  }
}
