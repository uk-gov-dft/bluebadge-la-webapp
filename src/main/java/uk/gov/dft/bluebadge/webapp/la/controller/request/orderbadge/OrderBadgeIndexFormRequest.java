package uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderBadgeIndexFormRequest {
  @NotBlank(message = "{NotNull.badge.applicantType}")
  private String applicantType;
}
