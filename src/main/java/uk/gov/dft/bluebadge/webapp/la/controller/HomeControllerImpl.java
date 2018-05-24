package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerImpl implements HomeController {

  public static final String URL_HOME = "/";

  public static final String TEMPLATE_HOME = "home";

  @GetMapping(URL_HOME)
  public String showHome(Model model, HttpSession session) {
    Optional<String> email = SignInUtils.getEmailSignedIn(session);
    if (!email.isPresent()) {
      return "redirect:" + SignInControllerImpl.URL_SIGN_IN;
    }
    model.addAttribute("email", email);
    return TEMPLATE_HOME;
  }
}
