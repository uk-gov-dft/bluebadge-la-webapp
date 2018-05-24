package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CreateANewUserFormRequest;

import javax.servlet.http.HttpSession;

public interface CreateANewUserController {
  String showCreateANewUser(CreateANewUserFormRequest formRequest, HttpSession session);

  String createANewUser(
          CreateANewUserFormRequest formRequest,
          BindingResult bindingResul,
          Model model,
          HttpSession session);
}
