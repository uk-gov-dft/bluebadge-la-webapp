package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

@Data
public class CreateANewUserFormRequest {
  private String emailAddress;
  private String name;
}
