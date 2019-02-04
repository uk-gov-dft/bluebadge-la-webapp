package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@Validated
public class LocalCouncil implements Serializable {
  @NotEmpty(message = "{NotEmpty.localCouncil.description}")
  @Size(max = 100, message = "{Size.localCouncil.description}")
  private String description;

  @Size(max = 100, message = "{Size.localCouncil.welshDescription}")
  private String welshDescription;
}
