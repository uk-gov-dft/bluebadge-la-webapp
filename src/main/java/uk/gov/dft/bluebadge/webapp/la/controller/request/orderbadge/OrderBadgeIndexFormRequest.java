package uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderBadgeIndexFormRequest implements Serializable {
  @NotBlank(message = "{NotNull.badge.applicantType}")
  private String applicantType;
}
