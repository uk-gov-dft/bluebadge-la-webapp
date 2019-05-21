package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliverToCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliveryOptionCodeField;
import uk.gov.dft.bluebadge.webapp.la.service.enums.ReplaceReason;

@Data
@Builder
public class ReplaceBadgeFormRequest implements Serializable {
  @NotNull(message = "{NotNull.replaceBadge.reason}")
  private ReplaceReason reason;

  @NotNull(message = "{NotNull.badge.deliverTo}")
  private DeliverToCodeField deliverTo;

  private DeliveryOptionCodeField deliveryOptions;
}
