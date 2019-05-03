package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;

@Data
@Builder
public class CredentialsFormRequest implements Serializable {
  @NotEmpty(message = "{NotEmpty.credentialsPage.service}")
  private List<String> service;

  @Size(max = 200, message = "{Size.credentialsPage.payApiKey}")
  private String payApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.notifyApiKey}")
  private String notifyApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.applicationSubmittedTemplateId}")
  private String applicationSubmittedTemplateId;

  public boolean applicationSubmittedTemplateIdShouldBeUpdated(NotifyProfile notifyProfile) {
    return getService() != null
        && getService().contains("ApplicationSubmittedTemplateId")
        && notifyProfile != null
        && notifyProfile.getTemplates() != null;
  }

  public boolean notifyApiKeyShouldBeUpdated(NotifyProfile notifyProfile) {
    return getService() != null
        && getService().contains("NotifyApiKey")
        && notifyProfile != null
        && notifyProfile.getApiKey() != null;
  }

  public boolean payApiKeyShouldBeUpdated() {
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
