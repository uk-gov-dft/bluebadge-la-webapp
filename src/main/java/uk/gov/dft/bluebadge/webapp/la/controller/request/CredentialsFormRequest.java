package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
public class CredentialsFormRequest implements Serializable {
  @NotEmpty(message = "{NotEmpty.credentialsPage.service}")
  private List<FieldToUpdate> service;

  @Size(max = 200, message = "{Size.credentialsPage.payApiKey}")
  private String payApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.notifyApiKey}")
  private String notifyApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.applicationSubmittedTemplateId}")
  private String applicationSubmittedTemplateId;

  public boolean applicationSubmittedTemplateIdShouldBeUpdated() {
    return getService() != null
        && getService().contains(FieldToUpdate.APPLICATION_SUBMITTED_TEMPLATE_ID)
        && !StringUtils.isBlank(applicationSubmittedTemplateId);
  }

  public boolean notifyApiKeyShouldBeUpdated() {
    return getService() != null
        && getService().contains(FieldToUpdate.NOTIFY_API_KEY)
        && !StringUtils.isBlank(notifyApiKey);
  }

  public boolean payApiKeyShouldBeUpdated() {
    return getService() != null
        && getService().contains(FieldToUpdate.PAY_API_KEY)
        && !StringUtils.isBlank(getPayApiKey());
  }

  public boolean shouldContainApplicationSubmittedTemplateIdValue() {
    return getService() != null
        && getService().contains(FieldToUpdate.APPLICATION_SUBMITTED_TEMPLATE_ID)
        && StringUtils.isBlank(getApplicationSubmittedTemplateId());
  }

  public boolean shouldContainNotifyApiKeyValue() {
    return getService() != null
        && getService().contains(FieldToUpdate.NOTIFY_API_KEY)
        && StringUtils.isBlank(getNotifyApiKey());
  }

  public boolean shouldContainPayApiKeyValue() {
    return getService() != null
        && getService().contains(FieldToUpdate.PAY_API_KEY)
        && StringUtils.isBlank(getPayApiKey());
  }

  public enum FieldToUpdate {
    NOTIFY_API_KEY,
    APPLICATION_SUBMITTED_TEMPLATE_ID,
    PAY_API_KEY
  }
}
