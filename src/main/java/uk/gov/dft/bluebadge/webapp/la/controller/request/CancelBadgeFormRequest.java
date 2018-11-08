package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelBadgeFormRequest implements Serializable {
  @NotBlank(message = "{NotNull.cancelBadge.reason}")
  private String reason;
}
