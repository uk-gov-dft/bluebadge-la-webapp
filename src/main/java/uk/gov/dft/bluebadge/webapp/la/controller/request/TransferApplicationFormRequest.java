package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferApplicationFormRequest implements Serializable {
  @NotBlank(message = "{NotNull.application.details.transfer.la}")
  private String transferToLaShortCode;
}
