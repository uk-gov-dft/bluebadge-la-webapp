package uk.gov.dft.bluebadge.webapp.la.controller;

import static uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeApplicationController.ORDER_A_BADGE_APPLICATION_URL;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationStatusField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationUpdate;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UpdateApplicationFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
@RequestMapping(path = "/new-applications/{uuid}")
@Slf4j
public class ApplicationDetailsController {
  private static final String PARAM_UUID = "uuid";
  private static final String TEMPLATE = "new-applications/application-details";
  private static final String REDIRECT_URL_NEW_APPLICATION =
      "redirect:" + NewApplicationsController.URL;

  private ApplicationService applicationService;
  private MessageSource messageSource;

  @Autowired
  public ApplicationDetailsController(
      ApplicationService applicationService, MessageSource messageSource) {
    this.applicationService = applicationService;
    this.messageSource = messageSource;
  }

  @GetMapping()
  public String show(
      @PathVariable(PARAM_UUID) UUID uuid,
      Model model,
      @ModelAttribute("updateApplicationFormRequest")
          final UpdateApplicationFormRequest updateFormRequest) {
    Application application = applicationService.retrieve(uuid.toString());
    updateFormRequest.setApplicationStatus(application.getApplicationStatus().name());

    model.addAttribute("altHealthConditionLabel", useAlternativeConditionLabel(application));
    model.addAttribute("app", application);
    model.addAttribute("uuid", uuid);
    model.addAttribute(
        "renderOrderBadgeButton", application.getParty().getTypeCode() != PartyTypeCodeField.ORG);

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

  @PutMapping()
  public String update(
      @PathVariable(PARAM_UUID) UUID uuid,
      Model model,
      @ModelAttribute("updateApplicationFormRequest")
          final UpdateApplicationFormRequest updateFormRequest) {

    ApplicationUpdate applicationUpdate =
        ApplicationUpdate.builder()
            .applicationId(uuid)
            .applicationStatus(
                ApplicationStatusField.fromValue(updateFormRequest.getApplicationStatus()))
            .build();
    applicationService.update(applicationUpdate);
    return this.show(uuid, model, updateFormRequest);
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
    return Lists.newArrayList(ApplicationStatusField.values())
        .stream()
        .map(
            status ->
                ReferenceData.builder()
                    .description(
                        messageSource.getMessage(
                            "application.details.status." + status.name(),
                            null,
                            LocaleContextHolder.getLocale()))
                    .shortCode(status.name())
                    .build())
        .collect(Collectors.toList());
  }
}
