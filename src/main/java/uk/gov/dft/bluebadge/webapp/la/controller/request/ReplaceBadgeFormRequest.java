package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReplaceBadgeFormRequest implements Serializable {
  @NotBlank(message = "{NotNull.replaceBadge.reason}")
  private String reason;

  @NotBlank(message = "{NotNull.badge.deliverTo}")
  private String deliverTo;

  private String deliveryOptions;
}
