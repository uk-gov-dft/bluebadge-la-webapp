package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationStatusField;

@Data
@Builder
public class UpdateApplicationFormRequest implements Serializable {
  private ApplicationStatusField applicationStatus;
}
