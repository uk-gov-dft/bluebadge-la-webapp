package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.BBPrincipal;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.TemplateName;
import uk.gov.dft.bluebadge.webapp.la.client.payment.model.GovPayProfile;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CredentialsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.MessageService;
import uk.gov.dft.bluebadge.webapp.la.service.PaymentService;

@Slf4j
@Controller
public class CredentialsController {

  public static final String URL = "/credentials";

  private static final String REDIRECT_URL_CREDENTIALS_UPDATED =
      "redirect:" + CredentialsStoredController.URL;
  private static final String TEMPLATE = "credentials";

  private static final String MODEL_FORM_REQUEST = "formRequest";

  private final PaymentService paymentService;
  private final MessageService messageService;
  private final SecurityUtils securityUtils;

  @Autowired
  public CredentialsController(
      final PaymentService paymentService,
      final MessageService messageService,
      final SecurityUtils securityUtils) {
    this.paymentService = paymentService;
    this.messageService = messageService;
    this.securityUtils = securityUtils;
  }

  @GetMapping(URL)
  public String show(@ModelAttribute(MODEL_FORM_REQUEST) final CredentialsFormRequest formRequest) {
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @Valid @ModelAttribute(MODEL_FORM_REQUEST) final CredentialsFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    model.addAttribute("errorSummary", new ErrorViewModel());

    if (formRequest.shouldContainPayApiKeyValue()) {
      bindingResult.rejectValue("payApiKey", "NotBlank.credentialsPage.payApiKey");
    }

    if (formRequest.shouldContainNotifyApiKeyValue()) {
      bindingResult.rejectValue("notifyApiKey", "NotBlank.credentialsPage.notifyApiKey");
    }

    if (formRequest.shouldContainApplicationSubmittedTemplateIdValue()) {
      bindingResult.rejectValue(
          "applicationSubmittedTemplateId",
          "NotBlank.credentialsPage.applicationSubmittedTemplateId");
    }

    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }

    BBPrincipal authUser = securityUtils.getCurrentAuth();
    String localAuthorityShortCode = authUser.getLocalAuthorityShortCode();

    if (formRequest.payApiKeyShouldBeUpdated()) {
      GovPayProfile payProfile = GovPayProfile.builder().apiKey(formRequest.getPayApiKey()).build();
      paymentService.updateLocalAuthoritySecret(localAuthorityShortCode, payProfile);
    }

    if (formRequest.notifyApiKeyShouldBeUpdated()
        || formRequest.applicationSubmittedTemplateIdShouldBeUpdated()) {
      Map<TemplateName, String> templates =
          StringUtils.isBlank(formRequest.getApplicationSubmittedTemplateId())
              ? null
              : ImmutableMap.of(
                  TemplateName.APPLICATION_SUBMITTED,
                  formRequest.getApplicationSubmittedTemplateId());

      NotifyProfile notifyProfile =
          NotifyProfile.builder()
              .apiKey(StringUtils.trimToNull(formRequest.getNotifyApiKey()))
              .templates(templates)
              .build();
      messageService.updateLocalNotifySecret(localAuthorityShortCode, notifyProfile);
    }

    return REDIRECT_URL_CREDENTIALS_UPDATED;
  }
}
