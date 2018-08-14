package uk.gov.dft.bluebadge.webapp.la.controller.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelBadgeFormRequest {
  @NotBlank(message = "{NotNull.cancelBadge.reason}")
  private String reason;
}
