package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SetPasswordFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

import java.util.UUID;

@Controller
public class SetPasswordController {

  private static final String URL_SET = "/set***REMOVED***/{uuid}";
  private static final String TEMPLATE_SET = "set***REMOVED***";
  private static final String URL_HOME_PAGE = "/";
  private static final String REDIRECT_URL_HOME_PAGE = "redirect:" + URL_HOME_PAGE;

  private UserService userService;
  private static final Logger log = LoggerFactory.getLogger(SetPasswordController.class);

  @Autowired
  public SetPasswordController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(URL_SET)
  public String showSetPassword(
      @ModelAttribute("formRequest") final SetPasswordFormRequest formRequest,
      Model model,
      @PathVariable("uuid") String uuid,
      HttpSession session) {

    model.addAttribute("uuid", uuid);

    return TEMPLATE_SET;
  }

  @PostMapping(URL_SET)
  public String setPassword(
      @Valid @ModelAttribute("formRequest") final SetPasswordFormRequest formRequest,
      @PathVariable("uuid") UUID uuid,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    log.debug("Posting set password form.");

    String password = formRequest.getPassword();
    String passwordConfirm = formRequest.getPasswordConfirm();

    try {
      userService.updatePassword(uuid, password, passwordConfirm);
      return REDIRECT_URL_HOME_PAGE;
    } catch (BadRequestException e) {
      ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);
      return TEMPLATE_SET;
    }
  }
}
