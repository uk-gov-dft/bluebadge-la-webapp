package uk.gov.dft.bluebadge.webapp.la.controller.request;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.TemplateName;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CredentialsFormRequestTest {
  private static final String SERVICE_APPLICATION_SUBMITTED_TEMPLATE_ID =
      "ApplicationSubmittedTemplateId";
  private static final String SERVICE_PAY_API_KEY = "PayApiKey";
  private static final String SERVICE_NOTIFY_API_KEY = "NotifyApiKey";
  private static final String TEMPLATE_ID = "templateId1";
  private static final String PAY_API_KEY = "payApiKey1";
  private static final String NOTIFY_API_KEY = "notifyApiKey1";

  @Before
  public void setup() {}

  @Test
  public void
      applicationSubmittedTemplateShouldBeUpdated_shouldReturnTrue_whenCheckBoxAndValueAreProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(SERVICE_APPLICATION_SUBMITTED_TEMPLATE_ID))
            .applicationSubmittedTemplateId(TEMPLATE_ID)
            .build();
    NotifyProfile notifyProfile =
        NotifyProfile.builder()
            .templates(ImmutableMap.of(TemplateName.APPLICATION_SUBMITTED, TEMPLATE_ID))
            .build();
    assertThat(formRequest.applicationSubmittedTemplateIdShouldBeUpdated(notifyProfile)).isTrue();
  }

  @Test
  public void
      applicationSubmittedTemplateShouldBeUpdated_shouldReturnFalse_whenCheckBoxAndValueAreNotProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().service(Lists.newArrayList()).build();
    NotifyProfile notifyProfile = NotifyProfile.builder().build();
    assertThat(formRequest.applicationSubmittedTemplateIdShouldBeUpdated(notifyProfile)).isFalse();
  }

  @Test
  public void
      applicationSubmittedTemplateShouldBeUpdated_shouldReturnFalse_whenCheckBoxIsTickedButValueIsNotProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(SERVICE_APPLICATION_SUBMITTED_TEMPLATE_ID))
            .build();
    NotifyProfile notifyProfile = NotifyProfile.builder().build();
    assertThat(formRequest.applicationSubmittedTemplateIdShouldBeUpdated(notifyProfile)).isFalse();
  }

  @Test
  public void
      applicationSubmittedTemplateShouldBeUpdated_shouldReturnFalse_whenCheckBoxIsNotTickedButValueIsProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList())
            .applicationSubmittedTemplateId(TEMPLATE_ID)
            .build();
    NotifyProfile notifyProfile =
        NotifyProfile.builder()
            .templates(ImmutableMap.of(TemplateName.APPLICATION_SUBMITTED, TEMPLATE_ID))
            .build();
    assertThat(formRequest.applicationSubmittedTemplateIdShouldBeUpdated(notifyProfile)).isFalse();
  }

  @Test
  public void notifyApiKeyShouldBeUpdated_shouldReturnTrue_whenCheckboxAndValueAreProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(SERVICE_NOTIFY_API_KEY))
            .payApiKey(NOTIFY_API_KEY)
            .build();
    NotifyProfile notifyProfile = NotifyProfile.builder().apiKey(NOTIFY_API_KEY).build();
    assertThat(formRequest.notifyApiKeyShouldBeUpdated(notifyProfile)).isTrue();
  }

  @Test
  public void
      notifyApiKeyShouldBeUpdated_shouldReturnFalse_whenCheckboxIsProvidedButValueIsMissing() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().service(Lists.newArrayList(SERVICE_NOTIFY_API_KEY)).build();
    NotifyProfile notifyProfile = NotifyProfile.builder().build();
    assertThat(formRequest.notifyApiKeyShouldBeUpdated(notifyProfile)).isFalse();
  }

  @Test
  public void
      notifyApiKeyShouldBeUpdated_shouldReturnFalse_whenCheckboxIsNotProvidedButValueIsProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().payApiKey(NOTIFY_API_KEY).build();
    NotifyProfile notifyProfile = NotifyProfile.builder().apiKey(NOTIFY_API_KEY).build();
    assertThat(formRequest.notifyApiKeyShouldBeUpdated(notifyProfile)).isFalse();
  }

  @Test
  public void payApiKeyShouldBeUpdated_shouldReturnTrue_whenCheckboxAndValueAreProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(SERVICE_PAY_API_KEY))
            .payApiKey(PAY_API_KEY)
            .build();
    assertThat(formRequest.payApiKeyShouldBeUpdated()).isTrue();
  }

  @Test
  public void payApiKeyShouldBeUpdated_shouldReturnFalse_whenCheckboxAndValueAreProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().service(Lists.newArrayList(SERVICE_PAY_API_KEY)).build();
    assertThat(formRequest.payApiKeyShouldBeUpdated()).isFalse();
  }

  @Test
  public void
      payApiKeyShouldBeUpdated_shouldReturnFalse_whenCheckboxIsNotProvidedButValueIsProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().payApiKey(PAY_API_KEY).build();
    assertThat(formRequest.payApiKeyShouldBeUpdated()).isFalse();
  }

  @Test
  public void shouldContainApplicationSubmittedTemplateIdValue_shouldReturnTrue_whenServiceIsTickedButValueIsNotProvided() {
    CredentialsFormRequest formRequest = CredentialsFormRequest.builder().service(Lists.newArrayList(SERVICE_APPLICATION_SUBMITTED_TEMPLATE_ID)).build();
    assertThat(formRequest.shouldContainApplicationSubmittedTemplateIdValue()).isTrue();
  }

  @Test
  public void shouldContainApplicationSubmittedTemplateIdValue_shouldReturnFalse_whenServiceIsTickedButValueIsProvided() {
    CredentialsFormRequest formRequest = CredentialsFormRequest.builder().service(Lists.newArrayList(SERVICE_APPLICATION_SUBMITTED_TEMPLATE_ID)).applicationSubmittedTemplateId(TEMPLATE_ID).build();
    assertThat(formRequest.shouldContainApplicationSubmittedTemplateIdValue()).isFalse();
  }

  @Test
  public void shouldContainNotifyApiKeyValue_shouldReturnTrue_whenServiceIsTickedButValueIsNotProvided() {
    CredentialsFormRequest formRequest = CredentialsFormRequest.builder().service(Lists.newArrayList(SERVICE_NOTIFY_API_KEY)).build();
    assertThat(formRequest.shouldContainNotifyApiKeyValue()).isTrue();
  }

  @Test
  public void shouldContainNotifyApiKeyValue_shouldReturnFalse_whenServiceIsTickedButValueIsProvided() {
    CredentialsFormRequest formRequest = CredentialsFormRequest.builder().service(Lists.newArrayList(SERVICE_NOTIFY_API_KEY)).notifyApiKey(NOTIFY_API_KEY).build();
    assertThat(formRequest.shouldContainNotifyApiKeyValue()).isFalse();
  }

  @Test
  public void shouldContainPayApiKeyValue_shouldReturnTrue_whenServiceIsTickedButValueIsNotProvided() {
    CredentialsFormRequest formRequest = CredentialsFormRequest.builder().service(Lists.newArrayList(SERVICE_PAY_API_KEY)).build();
    assertThat(formRequest.shouldContainPayApiKeyValue()).isTrue();
  }

  @Test
  public void shouldContainPayApiKeyValue_shouldReturnFalse_whenServiceIsTickedButValueIsProvided() {
    CredentialsFormRequest formRequest = CredentialsFormRequest.builder().service(Lists.newArrayList(SERVICE_PAY_API_KEY)).payApiKey(PAY_API_KEY).build();
    assertThat(formRequest.shouldContainPayApiKeyValue()).isFalse();
  }
}
