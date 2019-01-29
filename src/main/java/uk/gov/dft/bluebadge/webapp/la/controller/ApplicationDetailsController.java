package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.UUID;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationToOrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationToOrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationToOrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeBaseDetailsController;
import uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeIndexController;
import uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeOrganisationDetailsController;
import uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgePersonDetailsController;
import uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeProcessingController;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
@Slf4j
public class ApplicationDetailsController {
  private static final String PARAM_ID = "uuid";
  private static final String URL = "/new-applications/{uuid}";
  private static final String TEMPLATE = "new-applications/application-details";
  private static final String REDIRECT_URL_NEW_APPLICATION =
      "redirect:" + NewApplicationsController.URL;
  private static final String REDIRECT_URL_ORDER_BADGE_FOR_PERSON_APPLICATION =
      "redirect:" + OrderBadgePersonDetailsController.URL;
  private static final String REDIRECT_URL_ORDER_BADGE_FOR_ORGANISATION_APPLICATION =
      "redirect:" + OrderBadgeOrganisationDetailsController.URL;

  private ApplicationService applicationService;
  private ApplicationToOrderBadgeIndexFormRequest applicationToOrderBadgeIndexFormRequest;
  private ApplicationToOrderBadgePersonDetailsFormRequest
      applicationToOrderBadgePersonDetailsFormRequest;
  private ApplicationToOrderBadgeProcessingFormRequest applicationToOrderBadgeProcessingFormRequest;

  @Autowired
  public ApplicationDetailsController(
      ApplicationService applicationService,
      ApplicationToOrderBadgeIndexFormRequest applicationToOrderBadgeIndexFormRequest,
      ApplicationToOrderBadgePersonDetailsFormRequest
          applicationToOrderBadgePersonDetailsFormRequest,
      ApplicationToOrderBadgeProcessingFormRequest applicationToOrderBadgeProcessingFormRequest) {
    this.applicationService = applicationService;
    this.applicationToOrderBadgeIndexFormRequest = applicationToOrderBadgeIndexFormRequest;
    this.applicationToOrderBadgePersonDetailsFormRequest =
        applicationToOrderBadgePersonDetailsFormRequest;
    this.applicationToOrderBadgeProcessingFormRequest =
        applicationToOrderBadgeProcessingFormRequest;
  }

  @GetMapping(URL)
  public String show(@PathVariable(PARAM_ID) UUID uuid, Model model) {
    Application application = applicationService.retrieve(uuid.toString());

    model.addAttribute("altHealthConditionLabel", useAlternativeConditionLabel(application));
    model.addAttribute("app", application);
    model.addAttribute("uuid", uuid);
    model.addAttribute(
        "renderOrderBadgeButton", application.getParty().getTypeCode() != PartyTypeCodeField.ORG);

    return TEMPLATE;
  }

  @PostMapping(URL)
  public String orderABadgeForApplication(@PathVariable(PARAM_ID) UUID uuid, HttpSession session) {
      Application application = applicationService.retrieve(uuid.toString());

      OrderBadgeIndexFormRequest orderBadgeIndexFormRequest =
              applicationToOrderBadgeIndexFormRequest.convert(application);
      session.setAttribute(
              OrderBadgeIndexController.SESSION_FORM_REQUEST, orderBadgeIndexFormRequest);

      session.setAttribute(
        OrderBadgeBaseDetailsController.SESSION_FORM_REQUEST,
        applicationToOrderBadgePersonDetailsFormRequest.convert(application));

      session.setAttribute(
        OrderBadgeProcessingController.SESSION_FORM_REQUEST,
        applicationToOrderBadgeProcessingFormRequest.convert(application));

      return REDIRECT_URL_ORDER_BADGE_FOR_PERSON_APPLICATION;
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
