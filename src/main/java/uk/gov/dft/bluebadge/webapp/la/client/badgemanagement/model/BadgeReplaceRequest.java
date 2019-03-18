package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Builder
public class BadgeReplaceRequest {
  @NotEmpty private String badgeNumber;

  @NotEmpty
  @Size(max = 10)
  private String replaceReasonCode;

  @NotNull private DeliverToCodeField deliverToCode;

  @NotNull private DeliveryOptionCodeField deliveryOptionCode;
}
