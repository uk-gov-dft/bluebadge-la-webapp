package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/** Application */
@Validated
@Data
@Builder
public class ApplicationTransfer {
  private String transferToLaShortCode;
}
