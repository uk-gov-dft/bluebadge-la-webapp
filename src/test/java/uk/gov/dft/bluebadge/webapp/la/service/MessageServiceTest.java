package uk.gov.dft.bluebadge.webapp.la.service;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.MessageApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.TemplateName;

public class MessageServiceTest {
  private static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
  private static final String NOTIFY_API_KEY = "notifyApiKey0123";
  private static final String APPLICATION_SUBMITTED_TEMPLATE_ID =
      "applicationSubmittedTemplateId0123";
  private static final NotifyProfile NOTIFY_PROFILE =
      NotifyProfile.builder()
          .apiKey(NOTIFY_API_KEY)
          .templates(
              ImmutableMap.of(
                  TemplateName.APPLICATION_SUBMITTED, APPLICATION_SUBMITTED_TEMPLATE_ID))
          .build();

  private MessageService messageService;

  @Mock MessageApiClient messageApiClientMock;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    messageService = new MessageService(messageApiClientMock);
  }

  @Test
  public void updateLocalNotifySecret_shouldWork() {
    messageService.updateLocalNotifySecret(LOCAL_AUTHORITY_SHORT_CODE, NOTIFY_PROFILE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void
      updateLocalNotifySecret_shouldThrowIllegalArgumentException_whenLocalAuthorityShoutCodeIsNull() {
    messageService.updateLocalNotifySecret(null, NOTIFY_PROFILE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateLocalNotifySecret_shouldThrowIllegalArgumentException_whenProfileIsNull() {
    messageService.updateLocalNotifySecret(LOCAL_AUTHORITY_SHORT_CODE, null);
  }
}
