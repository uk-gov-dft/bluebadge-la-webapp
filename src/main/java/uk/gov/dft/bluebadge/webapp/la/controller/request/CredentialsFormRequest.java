package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialsFormRequest implements Serializable {
  /*@NotNull(message = "{NotNull.credentialsPage.selectService}")
  private String service;*/

  @Size(max = 200, message = "{Size.credentialsPage.payApiKey}")
  private String payApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.notifyApiKey}")
  private String notifyApiKey;

  @Size(max = 200, message = "{Size.credentialsPage.applicationSubmittedTemplateId}")
  private String applicationSubmittedTemplateId;
}
