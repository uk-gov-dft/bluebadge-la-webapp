package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.BBPrincipal;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.TemplateName;
import uk.gov.dft.bluebadge.webapp.la.client.payment.model.GovPayProfile;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CredentialsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.MessageService;
import uk.gov.dft.bluebadge.webapp.la.service.PaymentService;

public class CredentialsControllerTest extends BaseControllerTest {
  private static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
  private static final String OVER_200_STRING = StringUtils.rightPad("", 201, "a");
  private static final String PAY_API_KEY = "payApiKey1";
  private static final String NOTIFY_API_KEY = "notifyApiKey1";
  private static final String TEMPLATE_ID = "templateId";

  @Mock private PaymentService paymentServiceMock;
  @Mock private MessageService messageServiceMock;
  @Mock private SecurityUtils securityUtilsMock;

  MockMvc mockMvc;

  private CredentialsController controller;

  private BBPrincipal authUser;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    controller =
        new CredentialsController(paymentServiceMock, messageServiceMock, securityUtilsMock);
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    authUser =
        BBPrincipal.builder()
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .emailAddress("test@mailinator.com")
            .roleName("LA_ADMIN")
            .clientId("clientId")
            .build();
    when(securityUtilsMock.getCurrentAuth()).thenReturn(authUser);
  }

  @Test
  @SneakyThrows
  public void show_shouldWork() {
    CredentialsFormRequest formRequest = CredentialsFormRequest.builder().build();
    mockMvc
        .perform(get("/credentials"))
        .andExpect(status().isOk())
        .andExpect(view().name("credentials"));
  }

  @Test
  @SneakyThrows
  public void submit_shouldDisplayValidationError_whenNoValueIsSupplied() {
    mockMvc
        .perform(
            post("/credentials")
                .param("service", "")
                .param("payApiKey", "")
                .param("notifyApiKey", "")
                .param("applicationSubmittedTemplateId", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("credentials"))
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, "service", "NotEmpty"))
        .andExpect(model().errorCount(1));
  }

  @Test
  @SneakyThrows
  public void submit_shouldDisplayValidationError_whenAllServicesAreCheckedButNoValueIsProvided() {
    mockMvc
        .perform(
            post("/credentials")
                .param("service", "PayApiKey")
                .param("service", "NotifyApiKey")
                .param("service", "ApplicationSubmittedTemplateId")
                .param("payApiKey", "")
                .param("notifyApiKey", "")
                .param("applicationSubmittedTemplateId", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("credentials"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, "payApiKey", "NotBlank.credentialsPage.payApiKey"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, "notifyApiKey", "NotBlank.credentialsPage.notifyApiKey"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST,
                    "applicationSubmittedTemplateId",
                    "NotBlank.credentialsPage.applicationSubmittedTemplateId"))
        .andExpect(model().errorCount(3));
  }

  @Test
  @SneakyThrows
  public void
      submit_shouldDisplayValidationError_whenAllServicesAreCheckedButAllValuesAreOver200Size() {
    mockMvc
        .perform(
            post("/credentials")
                .param("service", "PayApiKey")
                .param("service", "NotifyApiKey")
                .param("service", "ApplicationSubmittedTemplateId")
                .param("payApiKey", OVER_200_STRING)
                .param("notifyApiKey", OVER_200_STRING)
                .param("applicationSubmittedTemplateId", OVER_200_STRING))
        .andExpect(status().isOk())
        .andExpect(view().name("credentials"))
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, "payApiKey", "Size"))
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, "notifyApiKey", "Size"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, "applicationSubmittedTemplateId", "Size"))
        .andExpect(model().errorCount(3));
  }

  @Test
  @SneakyThrows
  public void submit_shouldUpdatePayKey_whenPayKeyIsProvided() {
    mockMvc
        .perform(post("/credentials").param("service", "PayApiKey").param("payApiKey", PAY_API_KEY))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/credentials/stored"));

    GovPayProfile payProfile = GovPayProfile.builder().apiKey(PAY_API_KEY).build();
    verify(paymentServiceMock).updateLocalAuthoritySecret(LOCAL_AUTHORITY_SHORT_CODE, payProfile);
    verify(messageServiceMock, never()).updateLocalNotifySecret(any(), any());
    ;
  }

  @Test
  @SneakyThrows
  public void submit_shouldUpdateNotifyKey_whenNotifyKeyIsProvided() {
    mockMvc
        .perform(
            post("/credentials")
                .param("service", "NotifyApiKey")
                .param("notifyApiKey", NOTIFY_API_KEY))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/credentials/stored"));

    NotifyProfile notifyProfile = NotifyProfile.builder().apiKey(NOTIFY_API_KEY).build();
    verify(paymentServiceMock, never()).updateLocalAuthoritySecret(any(), any());
    verify(messageServiceMock).updateLocalNotifySecret(LOCAL_AUTHORITY_SHORT_CODE, notifyProfile);
  }

  @Test
  @SneakyThrows
  public void submit_shouldUpdateApplicationSubmittedTemplate_whenTemplateIdIsProvided() {
    mockMvc
        .perform(
            post("/credentials")
                .param("service", "ApplicationSubmittedTemplateId")
                .param("applicationSubmittedTemplateId", TEMPLATE_ID))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/credentials/stored"));

    NotifyProfile notifyProfile =
        NotifyProfile.builder()
            .templates(ImmutableMap.of(TemplateName.APPLICATION_SUBMITTED, TEMPLATE_ID))
            .build();
    verify(paymentServiceMock, never()).updateLocalAuthoritySecret(any(), any());
    verify(messageServiceMock).updateLocalNotifySecret(LOCAL_AUTHORITY_SHORT_CODE, notifyProfile);
  }

  @Test
  @SneakyThrows
  public void submit_shouldUpdatePayKeyNotifyKeyAndTemplateId_whenAllValuesArePassed() {
    mockMvc
        .perform(
            post("/credentials")
                .param("service", "PayApiKey")
                .param("service", "NotifyApiKey")
                .param("service", "ApplicationSubmittedTemplateId")
                .param("payApiKey", PAY_API_KEY)
                .param("notifyApiKey", NOTIFY_API_KEY)
                .param("applicationSubmittedTemplateId", TEMPLATE_ID))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/credentials/stored"));

    GovPayProfile payProfile = GovPayProfile.builder().apiKey(PAY_API_KEY).build();
    NotifyProfile notifyProfile =
        NotifyProfile.builder()
            .apiKey(NOTIFY_API_KEY)
            .templates(ImmutableMap.of(TemplateName.APPLICATION_SUBMITTED, TEMPLATE_ID))
            .build();

    verify(paymentServiceMock).updateLocalAuthoritySecret(LOCAL_AUTHORITY_SHORT_CODE, payProfile);
    verify(messageServiceMock).updateLocalNotifySecret(LOCAL_AUTHORITY_SHORT_CODE, notifyProfile);
  }
}
