package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialsFormRequest implements Serializable {
  @NotNull(message = "{NotNull.credentialsPage.service}")
  private String service;

  private Boolean servicePayApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.payApiKey}")
  private String payApiKey;

  private Boolean serviceNotifyApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.notifyApiKey}")
  private String notifyApiKey;

  private Boolean serviceApplicationSubmittedTemplateId;

  @Size(max = 200, message = "{Size.credentialsPage.applicationSubmittedTemplateId}")
  private String applicationSubmittedTemplateId;
}
