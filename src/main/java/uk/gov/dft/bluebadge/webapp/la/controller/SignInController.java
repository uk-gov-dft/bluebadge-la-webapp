package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {
  @GetMapping("/sign-in")
  public String startSignIn(Model model) {
    model.addAttribute("formRequest", new SignInForm());
    return "sign-in";
  }

  @Data
  private class SignInForm {
    private String username;
    private String password;
  }
}
