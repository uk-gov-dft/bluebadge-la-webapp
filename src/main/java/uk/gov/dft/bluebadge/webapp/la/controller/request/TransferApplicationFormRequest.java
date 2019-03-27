package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class TransferApplicationFormRequest implements Serializable {
  @NotBlank(message = "{NotNull.application.details.transfer.la}")
  private String transferToLaShortCode;
}
