package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
@Slf4j
public class ApplicationDetailsController {
  private static final String URL = "/new-applications/{uuid}";
  private static final String TEMPLATE = "new-applications/application-details";

  private ApplicationService applicationService;

  @Autowired
  public ApplicationDetailsController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @GetMapping(URL)
  public String show(@PathVariable("uuid") UUID uuid, Model model) {
    Application application = applicationService.retrieve(uuid.toString());

    model.addAttribute("app", application);
    return TEMPLATE;
  }
}
