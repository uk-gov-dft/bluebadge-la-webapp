package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/** Provided files/evidence */
@ApiModel(description = "Provided files/evidence")
@Validated
@Data
@Builder
public class Artifact {
  public enum TypeEnum {
    PROOF_ELIG,
    SUPPORT_DOCS,
    PROOF_ADD,
    PROOF_ID,
    PHOTO
  }

  private TypeEnum type;
  private String link;
}
