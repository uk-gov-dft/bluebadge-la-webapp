package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.ui.Model;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ApplicationCreateRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ApplicationUpdateRequest;

public interface ApplicationController {

  String show(Long id, Model model);

  String showAll(Model model);

  String showCreate(Model model);

  String create(ApplicationCreateRequest application);

  String showUpdate(Long id, Model model);

  String update(ApplicationUpdateRequest application);

  String delete(Long id, Model model);
}
