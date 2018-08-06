package uk.gov.dft.bluebadge.webapp.la.controller.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CancelBadgeFormRequest {
  @NotBlank(message = "{NotNull.cancelBadge.reason}")
  private String reason;
}
