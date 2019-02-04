package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

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
