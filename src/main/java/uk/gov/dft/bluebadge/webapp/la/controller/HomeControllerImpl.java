package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerImpl implements HomeController {

  public static final String URL_HOME = "/";

  public static final String TEMPLATE_HOME = "/home";

  @GetMapping(URL_HOME)
  public String showHome(@PathParam("email") String email, Model model) {
    model.addAttribute("email", email);
    return TEMPLATE_HOME;
  }
}
