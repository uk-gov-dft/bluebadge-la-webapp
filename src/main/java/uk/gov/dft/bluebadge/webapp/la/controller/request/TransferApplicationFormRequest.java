package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferApplicationFormRequest implements Serializable {
  private String transferToLaShortCode;
}
