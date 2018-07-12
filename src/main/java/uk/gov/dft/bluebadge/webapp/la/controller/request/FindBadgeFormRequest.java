package uk.gov.dft.bluebadge.webapp.la.controller.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindBadgeFormRequest {

  @NotBlank(message = "{NotNull.findBadge.findBadgeBy}")
  private String findBadgeBy;

  @NotBlank(message = "{Notnull.findBadge.searchTerm}")
  private String searchTerm;
}
