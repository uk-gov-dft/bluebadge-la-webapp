package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationToOrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeBaseDetailsController;
import uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeIndexController;
import uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeProcessingController;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class ApplicationDetailsController {
  private static final String PARAM_ID = "uuid";
  private static final String URL = "/new-applications/{uuid}";
  private static final String TEMPLATE = "new-applications/application-details";
  private static final String REDIRECT_URL_NEW_APPLICATION =
      "redirect:" + NewApplicationsController.URL;
  private static final String REDIRECT_URL_ORDER_BADGE_FOR_APPLICATION=
          "redirect:" + OrderBadgeIndexController.URL;

  private ApplicationService applicationService;
  private ApplicationToOrderBadgeIndexFormRequest applicationToOrderBadgeIndexFormRequest;

  @Autowired
  public ApplicationDetailsController(ApplicationService applicationService, ApplicationToOrderBadgeIndexFormRequest applicationToOrderBadgeIndexFormRequest) {
    this.applicationService = applicationService;
    this.applicationToOrderBadgeIndexFormRequest = applicationToOrderBadgeIndexFormRequest;
  }

  @GetMapping(URL)
  public String show(@PathVariable(PARAM_ID) UUID uuid, Model model) {
    Application application = applicationService.retrieve(uuid.toString());

    model.addAttribute("altHealthConditionLabel", useAlternativeConditionLabel(application));
    model.addAttribute("app", application);
    model.addAttribute("uuid", uuid);

    return TEMPLATE;
  }

  @PostMapping(URL)
  public String orderABadgeForApplication(@PathVariable(PARAM_ID) UUID uuid, Model model, HttpSession session) {
    Application application = applicationService.retrieve(uuid.toString());
    model.addAttribute("app", application);

    session.setAttribute(OrderBadgeIndexController.SESSION_FORM_REQUEST, applicationToOrderBadgeIndexFormRequest.convert(application));
    session.setAttribute(OrderBadgeBaseDetailsController.SESSION_FORM_REQUEST, null);
    session.setAttribute(OrderBadgeProcessingController.SESSION_FORM_REQUEST, null);
    return REDIRECT_URL_ORDER_BADGE_FOR_APPLICATION;
  }

  @DeleteMapping(URL)
  public String delete(@PathVariable(PARAM_ID) UUID uuid, Model model) {
    applicationService.delete(uuid.toString());
    return REDIRECT_URL_NEW_APPLICATION;
  }

  @SuppressWarnings("squid:S2589")
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
