package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
@Slf4j
public class ApplicationDetailsController {
  private static final String URL = "/new-applications/{uuid}";
  private static final String TEMPLATE = "new-applications/application-details";
  private static final String REDIRECT_URL_NEW_APPLICATION =
      "redirect:" + NewApplicationsController.URL;

  private ApplicationService applicationService;

  @Autowired
  public ApplicationDetailsController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @GetMapping(URL)
  public String show(@PathVariable("uuid") UUID uuid, Model model) {
    Application application = applicationService.retrieve(uuid.toString());

    model.addAttribute("altHealthConditionLabel", useAlternativeConditionLabel(application));
    model.addAttribute("app", application);
    model.addAttribute("uuid", uuid);

    return TEMPLATE;
  }

  @DeleteMapping(URL)
  public String delete(@PathVariable("uuid") UUID uuid, Model model) {
    applicationService.delete(uuid.toString());
    return REDIRECT_URL_NEW_APPLICATION;
  }

  private boolean useAlternativeConditionLabel(Application application) {

    if (application == null
        || application.getEligibility() == null
        || application.getEligibility().getTypeCode() == null) {
      return false;
    }

    EligibilityCodeField typeCode = application.getEligibility().getTypeCode();
    return EligibilityCodeField.WALKD == typeCode || EligibilityCodeField.ARMS == typeCode;
  }
}
