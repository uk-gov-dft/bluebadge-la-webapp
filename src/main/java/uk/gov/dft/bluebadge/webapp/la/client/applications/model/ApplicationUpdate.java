package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/** Application */
@Validated
@Data
@Builder
public class ApplicationUpdate {
  private ApplicationStatusField applicationStatus;
  private UUID applicationId;
}
