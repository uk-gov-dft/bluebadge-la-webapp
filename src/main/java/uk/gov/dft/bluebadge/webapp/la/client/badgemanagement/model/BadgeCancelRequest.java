package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.service.enums.CancelReason;

@Data
@Builder
public class BadgeCancelRequest {

  @Pattern(regexp = "^[0-9A-HJK]{6}$")
  @NotNull
  private String badgeNumber = null;

  @NotNull
  @Size(max = 10)
  private CancelReason cancelReasonCode;
}
