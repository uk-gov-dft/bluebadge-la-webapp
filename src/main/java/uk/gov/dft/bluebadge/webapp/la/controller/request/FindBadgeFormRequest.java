package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindBadgeFormRequest implements Serializable {

  @NotBlank(message = "{NotNull.findBadge.findBadgeBy}")
  private String findBadgeBy;

  private String searchTerm;
}
