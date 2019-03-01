package uk.gov.dft.bluebadge.webapp.la.controller;

import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.ARMS;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.CHILDBULK;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.CHILDVEHIC;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.DLA;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.PIP;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.WALKD;
import static uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeApplicationController.ORDER_A_BADGE_APPLICATION_URL;

import java.util.EnumSet;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
@RequestMapping(path = "/new-applications/{uuid}")
@Slf4j
public class ApplicationDetailsController {
  private static final String PARAM_UUID = "uuid";
  private static final String TEMPLATE = "new-applications/application-details";
  private static final String REDIRECT_URL_NEW_APPLICATION =
      "redirect:" + NewApplicationsController.URL;

  private static final EnumSet<EligibilityCodeField> BENEFIT_UPLOAD_ELIG_TYPES =
      EnumSet.of(PIP, DLA);
  private static final EnumSet<EligibilityCodeField> HCP_ELIG_TYPES =
      EnumSet.of(WALKD, CHILDBULK, CHILDVEHIC);
  private static final EnumSet<EligibilityCodeField> SUPPORT_DOCS_ELIG_TYPES =
      EnumSet.of(WALKD, ARMS, CHILDBULK, CHILDVEHIC);

  private ApplicationService applicationService;

  @Autowired
  public ApplicationDetailsController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @GetMapping()
  public String show(@PathVariable(PARAM_UUID) UUID uuid, Model model) {
    Application application = applicationService.retrieve(uuid.toString());
    model.addAttribute("altHealthConditionLabel", useAlternativeConditionLabel(application));
    model.addAttribute("app", application);
    model.addAttribute("uuid", uuid);
    model.addAttribute(
        "renderOrderBadgeButton", application.getParty().getTypeCode() != PartyTypeCodeField.ORG);

    if (application.getEligibility() != null) {
      EligibilityCodeField eligibilityTypeCodeField = application.getEligibility().getTypeCode();

      model.addAttribute(
          "renderSupportingDocs",
          SUPPORT_DOCS_ELIG_TYPES.contains(eligibilityTypeCodeField));
      model.addAttribute(
          "renderHCP", HCP_ELIG_TYPES.contains(eligibilityTypeCodeField));
      model.addAttribute(
          "renderBenefitUploads",
          BENEFIT_UPLOAD_ELIG_TYPES.contains(eligibilityTypeCodeField));
    }

    return TEMPLATE;
  }

  @PostMapping()
  public String orderABadgeForApplication(
      @PathVariable(PARAM_UUID) UUID uuid, RedirectAttributes ra) {
    ra.addAttribute("applicationId", uuid);
    return "redirect:" + ORDER_A_BADGE_APPLICATION_URL;
  }

  @DeleteMapping()
  public String delete(@PathVariable(PARAM_UUID) UUID uuid, Model model) {
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
