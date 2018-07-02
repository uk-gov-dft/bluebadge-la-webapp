package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

@Data
public class SetPasswordFormRequest {
  private String password;
  private String passwordConfirm;
}
