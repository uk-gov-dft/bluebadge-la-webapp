package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class BadgeReplaceRequest {
  @NotEmpty private String badgeNumber;

  @NotEmpty
  @Size(max = 10)
  private String replaceReasonCode;

  @NotEmpty
  @Size(max = 10)
  private String deliverToCode;

  @NotEmpty
  @Size(max = 10)
  private String deliveryOptionCode;

  public BadgeReplaceRequest(
      String badgeNumber,
      String replaceReasonCode,
      String deliverToCode,
      String deliveryOptionCode) {
    this.badgeNumber = badgeNumber;
    this.replaceReasonCode = replaceReasonCode;
    this.deliverToCode = deliverToCode;
    this.deliveryOptionCode = deliveryOptionCode;
  }
}
