package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SetPasswordFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class SetPasswordControllerImpl implements SetPasswordController {

  public static final String URL_SET_PASSWORD = "/set***REMOVED***";
  public static final String TEMPLATE_SET_PASSWORD = "set***REMOVED***";
  public static final String REDIRECT_URL_HOME = "redirect:" + HomeControllerImpl.URL_HOME;

  private UserService userService;

  @Autowired
  public SetPasswordControllerImpl(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(URL_SET_PASSWORD)
  public String showSetPassword(
      @ModelAttribute("formRequest") final SetPasswordFormRequest formRequest,
      HttpSession session) {

    return TEMPLATE_SET_PASSWORD;
  }

  @PostMapping(URL_SET_PASSWORD)
  public String setPassword(
      @Valid @ModelAttribute("formRequest") final SetPasswordFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    if (bindingResult.hasErrors()) {
      return TEMPLATE_SET_PASSWORD;
    } else {
      // Set Password in the service
      return REDIRECT_URL_HOME;
    }
  }
}
