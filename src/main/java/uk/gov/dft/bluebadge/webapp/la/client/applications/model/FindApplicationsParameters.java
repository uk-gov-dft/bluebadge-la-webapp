package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;

@Data
@Builder
public class FindApplicationsParameters {
  private Optional<String> name;
  private Optional<String> postcode;
  private Optional<LocalDateTime> from;
  private Optional<LocalDateTime> to;
  private Optional<ApplicationTypeCodeField> applicationTypeCode;
  private PagingInfo pageInfo;
}
