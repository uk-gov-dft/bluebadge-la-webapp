package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateApplicationFormRequest implements Serializable {
  private String applicationStatus;
  private String transferToLaShortCode;
}
