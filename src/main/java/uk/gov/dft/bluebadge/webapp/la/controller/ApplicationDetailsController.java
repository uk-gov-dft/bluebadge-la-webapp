package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTransfer;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationUpdate;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.TransferApplicationFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UpdateApplicationFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

import javax.validation.Valid;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.ARMS;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.CHILDBULK;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.CHILDVEHIC;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.DLA;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.PIP;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.WALKD;
import static uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeApplicationController.ORDER_A_BADGE_APPLICATION_URL;

@Controller
@Slf4j
public class ApplicationDetailsController extends BaseController {
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
  private static final String URL_NEW_APPLICATIONS_UUID = "/new-applications/{uuid}";

  private ApplicationService applicationService;
  private ReferenceDataService referenceDataService;
  private final SecurityUtils securityUtils;

  @Autowired
  ApplicationDetailsController(
      ApplicationService applicationService,
      ReferenceDataService referenceDataService,
      SecurityUtils securityUtils) {
    this.applicationService = applicationService;
    this.referenceDataService = referenceDataService;
    this.securityUtils = securityUtils;
  }

  @GetMapping(path = "/new-applications/{uuid}")
  public String show(@PathVariable(PARAM_UUID) UUID uuid, Model model) {
    Application application = applicationService.retrieve(uuid.toString());

    model.addAttribute("altHealthConditionLabel", useAlternativeConditionLabel(application));
    model.addAttribute("app", application);
    model.addAttribute("uuid", uuid);
    model.addAttribute(
        "renderOrderBadgeButton", application.getParty().getTypeCode() != PartyTypeCodeField.ORG);
    model.addAttribute(
        "updateApplicationFormRequest", UpdateApplicationFormRequest.builder().applicationStatus(application.getApplicationStatus()).build());
    // Can add a new transfer model.  1 field.  Only validation is notnull, so no data to preserve.
    if (!model.containsAttribute("transferApplicationFormRequest")) {
      model.addAttribute("transferApplicationFormRequest", TransferApplicationFormRequest.builder().build());
    }

    if (application.getEligibility() != null) {
      EligibilityCodeField eligibilityTypeCodeField = application.getEligibility().getTypeCode();

      model.addAttribute(
          "renderSupportingDocs", SUPPORT_DOCS_ELIG_TYPES.contains(eligibilityTypeCodeField));
      model.addAttribute("renderHCP", HCP_ELIG_TYPES.contains(eligibilityTypeCodeField));
      model.addAttribute(
          "renderBenefitUploads", BENEFIT_UPLOAD_ELIG_TYPES.contains(eligibilityTypeCodeField));
    }

    return TEMPLATE;
  }

  @PostMapping(path = URL_NEW_APPLICATIONS_UUID)
  public String orderABadgeForApplication(
      @PathVariable(PARAM_UUID) UUID uuid, RedirectAttributes ra) {
    ra.addAttribute("applicationId", uuid);
    return "redirect:" + ORDER_A_BADGE_APPLICATION_URL;
  }

  @PostMapping(path = "/new-applications/{uuid}/transfers")
  public String transferApplication(
      @PathVariable(PARAM_UUID) UUID uuid,
      @Valid @ModelAttribute("transferApplicationFormRequest") final TransferApplicationFormRequest formRequest,
      BindingResult bindingResult,
      RedirectAttributes attr) {

    if (bindingResult.hasErrors()) {
      return redirectToOnBindingError("/new-applications/{uuid}", formRequest, bindingResult, attr, "transferApplicationFormRequest");
    }

    ApplicationTransfer applicationTransfer =
        ApplicationTransfer.builder()
            .transferToLaShortCode(formRequest.getTransferToLaShortCode())
            .build();
    applicationService.transfer(uuid.toString(), applicationTransfer);

    return REDIRECT_URL_NEW_APPLICATION;
  }

  @DeleteMapping(path = "/new-applications/{uuid}")
  public String delete(@PathVariable(PARAM_UUID) UUID uuid) {
    applicationService.delete(uuid.toString());
    return REDIRECT_URL_NEW_APPLICATION;
  }

  @PutMapping(path = URL_NEW_APPLICATIONS_UUID)
  public String update(
      @PathVariable(PARAM_UUID) UUID uuid,
      Model model,
      @ModelAttribute("updateApplicationFormRequest") final UpdateApplicationFormRequest updateApplicationFormRequest) {

    ApplicationUpdate applicationUpdate =
        ApplicationUpdate.builder()
            .applicationId(uuid)
            .applicationStatus(
                updateApplicationFormRequest.getApplicationStatus())
            .build();
    applicationService.update(applicationUpdate);
    return TEMPLATE;
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

  @ModelAttribute("applicationStatusOptions")
  public List<ReferenceData> applicationStatusOptions() {
    return referenceDataService.retrieveApplicationReferenceDataList(RefDataGroupEnum.APPSTATUS);
  }

  @ModelAttribute("allOtherLocalAuthorities")
  public List<ReferenceData> allOtherLocalAuthorities() {
    List<ReferenceData> las = referenceDataService.retrieveBadgeLocalAuthorities();
    las.removeIf(la -> la.getShortCode().equals(securityUtils.getCurrentLocalAuthorityShortCode()));
    return las;
  }
}
