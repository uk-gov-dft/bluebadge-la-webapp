package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class OrderBadgeBadgeOrderedController {
  public static final String URL = "/order-a-badge/badge-ordered";

  public static final String TEMPLATE = "order-a-badge/badge-ordered";

  @GetMapping(URL)
  public String show(Model model) {
    List<String> badgeNumbers = (List<String>) model.asMap().get("badgeNumbers");
    model.addAttribute("badgeNumbers", badgeNumbers);
    return TEMPLATE;
  }
}
