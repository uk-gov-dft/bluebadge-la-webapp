package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;

@Data
@Builder
public class CredentialsFormRequest implements Serializable {
  @NotNull(message = "{NotNull.credentialsPage.service}")
  private String service;

  @Size(max = 200, message = "{Size.credentialsPage.payApiKey}")
  private String payApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.notifyApiKey}")
  private String notifyApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.applicationSubmittedTemplateId}")
  private String applicationSubmittedTemplateId;

  public boolean applicationSubmittedTemplateShouldBeUpdated(NotifyProfile notifyProfile) {
    return getService() != null
        && getService().contains("ApplicationSubmittedTemplateId")
        && notifyProfile.getTemplates() != null;
  }

  public boolean notifyApiKeyShouldBeUpdated(NotifyProfile notifyProfile) {
    return getService() != null
        && getService().contains("NotifyApiKey")
        && notifyProfile.getApiKey() != null;
  }

  public boolean isPayApiKeyIsPassed() {
    return getService() != null
        && getService().contains("PayApiKey")
        && !StringUtils.isBlank(getPayApiKey());
  }

  public boolean shouldContainApplicationSubmittedTemplateIdValue() {
    return getService() != null
        && getService().contains("ApplicationSubmittedTemplateId")
        && StringUtils.isBlank(getApplicationSubmittedTemplateId());
  }

  public boolean shouldContainNotifyApiKeyValue() {
    return getService() != null
        && getService().contains("NotifyApiKey")
        && StringUtils.isBlank(getNotifyApiKey());
  }

  public boolean shouldContainPayApiKeyValue() {
    return getService() != null
        && getService().contains("PayApiKey")
        && StringUtils.isBlank(getPayApiKey());
  }
}
