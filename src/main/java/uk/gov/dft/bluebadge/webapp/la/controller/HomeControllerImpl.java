package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerImpl implements HomeController {

  public static final String URL_HOME = "/";

  public static final String TEMPLATE_HOME = "home";

  @GetMapping(URL_HOME)
  public String showHome(Model model, HttpSession session) {
    String email = (String) session.getAttribute("email");
    if (StringUtils.isEmpty(email)) {
      return "redirect:" + UserControllerImpl.URL_SIGN_IN;
    }
    model.addAttribute("email", email);
    return TEMPLATE_HOME;
  }
}
