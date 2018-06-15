package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SetPasswordFormRequest;

public interface SetPasswordController {

  String showSetPassword(SetPasswordFormRequest formRequest, Model model, String uuid, HttpSession session);

  String setPassword(
      SetPasswordFormRequest formRequest,
      String uuid,
      BindingResult result,
      Model model,
      HttpSession session);
}
