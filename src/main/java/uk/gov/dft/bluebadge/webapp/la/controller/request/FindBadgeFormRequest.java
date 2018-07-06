package uk.gov.dft.bluebadge.webapp.la.controller.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindBadgeFormRequest {

  @NotBlank(message = "{NotNull.findBadge.findBadgeBy}")
  private String findBadgeBy;

  private String searchTerm;
}
