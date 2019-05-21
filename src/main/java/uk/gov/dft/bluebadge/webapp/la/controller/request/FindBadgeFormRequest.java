package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindBadgeFormRequest implements Serializable {

  @NotBlank(message = "{NotNull.findBadge.findBadgeBy}")
  private String findBadgeBy = "badgeNumber";

  @Pattern(regexp = "^[^\\/]*$", message = "{Pattern.findBadge.searchTerm}")
  private String searchTermBadgeNumber;

  @Pattern(regexp = "^[^\\/]*$", message = "{Pattern.findBadge.searchTerm}")
  private String searchTermName;

  @Pattern(regexp = "^[^\\/]*$", message = "{Pattern.findBadge.searchTerm}")
  private String searchTermPostcode;
}
