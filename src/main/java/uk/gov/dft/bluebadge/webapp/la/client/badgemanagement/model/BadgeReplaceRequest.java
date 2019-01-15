package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class BadgeReplaceRequest {
  @NotEmpty private String badgeNumber = null;

  @NotEmpty
  @Size(max = 10)
  private String replaceReasonCode = null;

  @NotEmpty
  @Size(max = 10)
  private String deliverToCode = null;

  @NotEmpty
  @Size(max = 10)
  private String deliveryOptionCode = null;
}
