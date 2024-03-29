package uk.gov.dft.bluebadge.webapp.la.controller;

import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.ARMS;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.CHILDBULK;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.CHILDVEHIC;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.DLA;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.PIP;
import static uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField.WALKD;
import static uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeApplicationController.ORDER_A_BADGE_APPLICATION_URL;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
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
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToLookupBadgeViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.TransferApplicationFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UpdateApplicationFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.LookupBadgeViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Controller
@Slf4j
public class ApplicationDetailsController extends BaseController {
  private static final String PARAM_UUID = "uuid";
  private static final String TEMPLATE = "applications/application-details";
  private static final String REDIRECT = "redirect:";
  private static final String REDIRECT_URL_NEW_APPLICATION = REDIRECT + ApplicationsController.URL;

  private static final EnumSet<EligibilityCodeField> BENEFIT_UPLOAD_ELIG_TYPES =
      EnumSet.of(PIP, DLA);
  private static final EnumSet<EligibilityCodeField> HCP_ELIG_TYPES =
      EnumSet.of(WALKD, CHILDBULK, CHILDVEHIC);
  private static final EnumSet<EligibilityCodeField> SUPPORT_DOCS_ELIG_TYPES =
      EnumSet.of(WALKD, ARMS, CHILDBULK, CHILDVEHIC);
  static final String URL_NEW_APPLICATIONS_UUID = "/applications/{uuid}";
  private static final String TRANSFER_APPLICATION_FORM_REQUEST = "transferApplicationFormRequest";

  private ApplicationService applicationService;
  private ReferenceDataService referenceDataService;
  private BadgeService badgeService;
  private BadgeToLookupBadgeViewModel converterToViewModel;
  private final SecurityUtils securityUtils;

  @Autowired
  ApplicationDetailsController(
      ApplicationService applicationService,
      ReferenceDataService referenceDataService,
      BadgeService badgeService,
      BadgeToLookupBadgeViewModel converterToViewModel,
      SecurityUtils securityUtils) {
    this.applicationService = applicationService;
    this.referenceDataService = referenceDataService;
    this.badgeService = badgeService;
    this.converterToViewModel = converterToViewModel;
    this.securityUtils = securityUtils;
  }

  @GetMapping(path = URL_NEW_APPLICATIONS_UUID)
  public String show(@PathVariable(PARAM_UUID) UUID uuid, Model model) {
    Application application = applicationService.retrieve(uuid.toString());

    LookupBadgeViewModel existingBadge =
        null != application.getExistingBadgeNumber()
            ? findBadgeByNumber(application.getExistingBadgeNumber())
            : null;

    model.addAttribute("altHealthConditionLabel", useAlternativeConditionLabel(application));
    model.addAttribute("app", application);
    model.addAttribute("existingBadge", existingBadge);
    model.addAttribute("uuid", uuid);
    model.addAttribute(
        "renderOrderBadgeButton", application.getParty().getTypeCode() != PartyTypeCodeField.ORG);
    model.addAttribute(
        "updateApplicationFormRequest",
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build());
    // Can add a new transfer model.  1 field.  Only validation is notnull, so no data to preserve.
    if (!model.containsAttribute(TRANSFER_APPLICATION_FORM_REQUEST)) {
      model.addAttribute(
          TRANSFER_APPLICATION_FORM_REQUEST, TransferApplicationFormRequest.builder().build());
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
    return REDIRECT + ORDER_A_BADGE_APPLICATION_URL;
  }

  @PostMapping(path = "/applications/{uuid}/transfers")
  public String transferApplication(
      @PathVariable(PARAM_UUID) UUID uuid,
      @Valid @ModelAttribute(TRANSFER_APPLICATION_FORM_REQUEST)
          final TransferApplicationFormRequest formRequest,
      BindingResult bindingResult,
      RedirectAttributes attr) {

    if (bindingResult.hasErrors()) {
      return redirectToOnBindingError(
          URL_NEW_APPLICATIONS_UUID,
          formRequest,
          bindingResult,
          attr,
          TRANSFER_APPLICATION_FORM_REQUEST);
    }

    ApplicationTransfer applicationTransfer =
        ApplicationTransfer.builder()
            .transferToLaShortCode(formRequest.getTransferToLaShortCode())
            .build();
    applicationService.transfer(uuid.toString(), applicationTransfer);

    return REDIRECT_URL_NEW_APPLICATION;
  }

  @DeleteMapping(path = URL_NEW_APPLICATIONS_UUID)
  public String delete(@PathVariable(PARAM_UUID) UUID uuid) {
    applicationService.delete(uuid.toString());
    return REDIRECT_URL_NEW_APPLICATION;
  }

  @PutMapping(path = URL_NEW_APPLICATIONS_UUID)
  public String update(
      @PathVariable(PARAM_UUID) UUID uuid,
      Model model,
      @ModelAttribute("updateApplicationFormRequest")
          final UpdateApplicationFormRequest updateApplicationFormRequest) {

    ApplicationUpdate applicationUpdate =
        ApplicationUpdate.builder()
            .applicationId(uuid)
            .applicationStatus(updateApplicationFormRequest.getApplicationStatus())
            .build();
    applicationService.update(applicationUpdate);
    return REDIRECT + URL_NEW_APPLICATIONS_UUID;
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

  private LookupBadgeViewModel findBadgeByNumber(String searchTerm) {
    Optional<Badge> result = badgeService.retrieve(searchTerm);

    if (result.isPresent()) {
      return converterToViewModel.convert(result.get());
    }

    return null;
  }
}
