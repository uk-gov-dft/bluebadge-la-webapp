package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TransferApplicationFormRequest implements Serializable {
  private String transferToLaShortCode;
}
