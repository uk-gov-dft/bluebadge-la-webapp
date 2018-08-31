package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.ApplicationToApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
public class ApplicationDetailsController {
  private static final String URL = "/new-applications/{uuid}";
  private static final String TEMPLATE = "new-applications/application-details";

  private ApplicationService applicationService;
  private ApplicationToApplicationViewModel converterToViewModel;

  @Autowired
  public ApplicationDetailsController(
      ApplicationService applicationService,
      ApplicationToApplicationViewModel converterToViewModel) {
    this.applicationService = applicationService;
    this.converterToViewModel = converterToViewModel;
  }

  @GetMapping(URL)
  public String show(@PathVariable("uuid") UUID uuid, Model model) {
    Optional<Application> application = applicationService.retrieve(uuid.toString());
    if (!application.isPresent()) {
      throw new NotFoundException(new CommonResponse());
    }
    ApplicationViewModel applicationViewModel = converterToViewModel.convert(application.get());
    model.addAttribute("app", applicationViewModel);
    return TEMPLATE;
  }
}
