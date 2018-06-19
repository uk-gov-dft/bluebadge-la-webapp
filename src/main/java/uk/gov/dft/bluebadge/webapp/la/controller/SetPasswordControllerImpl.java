package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SetPasswordFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@Controller
public class SetPasswordControllerImpl implements SetPasswordController {

  public static final String URL_SET_PASSWORD = "/set***REMOVED***/{uuid}";
  public static final String TEMPLATE_SET_PASSWORD = "set***REMOVED***";
  public static final String URL_HOME_PAGE = "/";
  public static final String REDIRECT_URL_HOME_PAGE = "redirect:" + URL_HOME_PAGE;

  private UserService userService;
  private static final Logger logger = LoggerFactory.getLogger(SetPasswordControllerImpl.class);

  @Autowired
  public SetPasswordControllerImpl(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(URL_SET_PASSWORD)
  public String showSetPassword(
      @ModelAttribute("formRequest") final SetPasswordFormRequest formRequest,
      Model model,
      @PathVariable("uuid") String uuid,
      HttpSession session) {

    model.addAttribute("uuid", uuid);

    return TEMPLATE_SET_PASSWORD;
  }

  @PostMapping(URL_SET_PASSWORD)
  public String setPassword(
      @Valid @ModelAttribute("formRequest") final SetPasswordFormRequest formRequest,
      @PathVariable("uuid") String uuid,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {

    try {

      String password = formRequest.getPassword();
      String passwordConfirm = formRequest.getPasswordConfirm();

      UserResponse userResponse = this.userService.updatePassword(uuid, password, passwordConfirm);

      //    return "redirect:/manage-users";
      /*
            if (userResponse.getData() != null) {
              String emailAddress = userResponse.getData().getEmailAddress();
              Optional<UserResponse> user = signInService.signIn(emailAddress);
              if (user.isPresent()) {
                session.setAttribute("user", user.get().getData());
                return "redirect:/manage-users";
              }
            }
      */
      return ErrorHandlingUtils.handleError(
          userResponse.getError(),
          REDIRECT_URL_HOME_PAGE,
          TEMPLATE_SET_PASSWORD,
          bindingResult,
          model);

    } catch (Exception ex) {
      logger.error("There was an unexpected exception", ex);
      return TEMPLATE_SET_PASSWORD;
    }
  }
}
