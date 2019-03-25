package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliverToCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliveryOptionCodeField;

@Data
@Builder
public class ReplaceBadgeFormRequest implements Serializable {
  @NotBlank(message = "{NotNull.replaceBadge.reason}")
  private String reason;

  @NotNull(message = "{NotNull.badge.deliverTo}")
  private DeliverToCodeField deliverTo;

  private DeliveryOptionCodeField deliveryOptions;
}
