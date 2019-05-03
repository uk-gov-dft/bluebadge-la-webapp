package uk.gov.dft.bluebadge.webapp.la.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.MessageApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;

@Service
@Slf4j
public class MessageService {

  private MessageApiClient messageApiClient;

  @Autowired
  public MessageService(MessageApiClient messageApiClient) {
    this.messageApiClient = messageApiClient;
  }

  public void updateLocalNotifySecret(
      final String localAuthorityShortCode, final NotifyProfile notifyProfile) {
    log.debug("Update local authority gov uk notify secret.");
    Assert.notNull(localAuthorityShortCode, "localAuthorityShortCode should not be null");
    Assert.notNull(notifyProfile, "notifyProfile should not be null");
    messageApiClient.updateLocalNotifySecret(localAuthorityShortCode, notifyProfile);
  }
}
