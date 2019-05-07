package uk.gov.dft.bluebadge.webapp.la.controller.request;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static uk.gov.dft.bluebadge.webapp.la.controller.request.CredentialsFormRequest.FieldToUpdate.APPLICATION_SUBMITTED_TEMPLATE_ID;
import static uk.gov.dft.bluebadge.webapp.la.controller.request.CredentialsFormRequest.FieldToUpdate.NOTIFY_API_KEY;
import static uk.gov.dft.bluebadge.webapp.la.controller.request.CredentialsFormRequest.FieldToUpdate.PAY_API_KEY;

public class CredentialsFormRequestTest {
  private static final String TEMPLATE_ID_VALUE = "templateId1";
  private static final String PAY_API_KEY_VALUE = "payApiKey1";
  private static final String NOTIFY_API_KEY_VALUE = "notifyApiKey1";

  @Before
  public void setup() {}

  @Test
  public void
      applicationSubmittedTemplateShouldBeUpdated_shouldReturnTrue_whenCheckBoxAndValueAreProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(APPLICATION_SUBMITTED_TEMPLATE_ID))
            .applicationSubmittedTemplateId(TEMPLATE_ID_VALUE)
            .build();
    assertThat(formRequest.applicationSubmittedTemplateIdShouldBeUpdated()).isTrue();
  }

  @Test
  public void
      applicationSubmittedTemplateShouldBeUpdated_shouldReturnFalse_whenCheckBoxAndValueAreNotProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().service(Lists.newArrayList()).build();
    assertThat(formRequest.applicationSubmittedTemplateIdShouldBeUpdated()).isFalse();
  }

  @Test
  public void
      applicationSubmittedTemplateShouldBeUpdated_shouldReturnFalse_whenCheckBoxIsTickedButValueIsNotProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(APPLICATION_SUBMITTED_TEMPLATE_ID))
            .build();
    assertThat(formRequest.applicationSubmittedTemplateIdShouldBeUpdated()).isFalse();
  }

  @Test
  public void
      applicationSubmittedTemplateShouldBeUpdated_shouldReturnFalse_whenCheckBoxIsNotTickedButValueIsProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList())
            .applicationSubmittedTemplateId(TEMPLATE_ID_VALUE)
            .build();
    assertThat(formRequest.applicationSubmittedTemplateIdShouldBeUpdated()).isFalse();
  }

  @Test
  public void notifyApiKeyShouldBeUpdated_shouldReturnTrue_whenCheckboxAndValueAreProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(NOTIFY_API_KEY))
            .notifyApiKey(NOTIFY_API_KEY_VALUE)
            .build();
    assertThat(formRequest.notifyApiKeyShouldBeUpdated()).isTrue();
  }

  @Test
  public void
      notifyApiKeyShouldBeUpdated_shouldReturnFalse_whenCheckboxIsProvidedButValueIsMissing() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().service(Lists.newArrayList(NOTIFY_API_KEY)).build();
    assertThat(formRequest.notifyApiKeyShouldBeUpdated()).isFalse();
  }

  @Test
  public void
      notifyApiKeyShouldBeUpdated_shouldReturnFalse_whenCheckboxIsNotProvidedButValueIsProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().payApiKey(NOTIFY_API_KEY_VALUE).build();
    assertThat(formRequest.notifyApiKeyShouldBeUpdated()).isFalse();
  }

  @Test
  public void payApiKeyShouldBeUpdated_shouldReturnTrue_whenCheckboxAndValueAreProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(PAY_API_KEY))
            .payApiKey(PAY_API_KEY_VALUE)
            .build();
    assertThat(formRequest.payApiKeyShouldBeUpdated()).isTrue();
  }

  @Test
  public void payApiKeyShouldBeUpdated_shouldReturnFalse_whenCheckboxAndValueAreProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().service(Lists.newArrayList(PAY_API_KEY)).build();
    assertThat(formRequest.payApiKeyShouldBeUpdated()).isFalse();
  }

  @Test
  public void
      payApiKeyShouldBeUpdated_shouldReturnFalse_whenCheckboxIsNotProvidedButValueIsProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().payApiKey(PAY_API_KEY_VALUE).build();
    assertThat(formRequest.payApiKeyShouldBeUpdated()).isFalse();
  }

  @Test
  public void
      shouldContainApplicationSubmittedTemplateIdValue_shouldReturnTrue_whenServiceIsTickedButValueIsNotProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(APPLICATION_SUBMITTED_TEMPLATE_ID))
            .build();
    assertThat(formRequest.shouldContainApplicationSubmittedTemplateIdValue()).isTrue();
  }

  @Test
  public void
      shouldContainApplicationSubmittedTemplateIdValue_shouldReturnFalse_whenServiceIsTickedButValueIsProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(APPLICATION_SUBMITTED_TEMPLATE_ID))
            .applicationSubmittedTemplateId(TEMPLATE_ID_VALUE)
            .build();
    assertThat(formRequest.shouldContainApplicationSubmittedTemplateIdValue()).isFalse();
  }

  @Test
  public void
      shouldContainNotifyApiKeyValue_shouldReturnTrue_whenServiceIsTickedButValueIsNotProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().service(Lists.newArrayList(NOTIFY_API_KEY)).build();
    assertThat(formRequest.shouldContainNotifyApiKeyValue()).isTrue();
  }

  @Test
  public void
      shouldContainNotifyApiKeyValue_shouldReturnFalse_whenServiceIsTickedButValueIsProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(NOTIFY_API_KEY))
            .notifyApiKey(NOTIFY_API_KEY_VALUE)
            .build();
    assertThat(formRequest.shouldContainNotifyApiKeyValue()).isFalse();
  }

  @Test
  public void
      shouldContainPayApiKeyValue_shouldReturnTrue_whenServiceIsTickedButValueIsNotProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder().service(Lists.newArrayList(PAY_API_KEY)).build();
    assertThat(formRequest.shouldContainPayApiKeyValue()).isTrue();
  }

  @Test
  public void
      shouldContainPayApiKeyValue_shouldReturnFalse_whenServiceIsTickedButValueIsProvided() {
    CredentialsFormRequest formRequest =
        CredentialsFormRequest.builder()
            .service(Lists.newArrayList(PAY_API_KEY))
            .payApiKey(PAY_API_KEY_VALUE)
            .build();
    assertThat(formRequest.shouldContainPayApiKeyValue()).isFalse();
  }
}
