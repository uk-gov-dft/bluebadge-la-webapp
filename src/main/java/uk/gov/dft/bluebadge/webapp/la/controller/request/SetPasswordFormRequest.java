package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import lombok.Data;

@Data
public class SetPasswordFormRequest implements Serializable {
  private String password;
  private String passwordConfirm;
}
