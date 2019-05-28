package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import uk.gov.dft.bluebadge.webapp.la.service.enums.ReplaceReason;

@Data
@Validated
@Builder
public class BadgeReplaceRequest {
  @NotEmpty private String badgeNumber;

  @NotNull private ReplaceReason replaceReasonCode;

  @NotNull private DeliverToCodeField deliverToCode;

  @NotNull private DeliveryOptionCodeField deliveryOptionCode;
}
