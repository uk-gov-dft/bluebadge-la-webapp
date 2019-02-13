package uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import uk.gov.dft.bluebadge.webapp.la.controller.request.FlowForm;

@Data
@Builder
public class OrderBadgeIndexFormRequest implements FlowForm, Serializable {
  private String flowId;

  @NotBlank(message = "{NotNull.badge.applicantType}")
  private String applicantType;
}
