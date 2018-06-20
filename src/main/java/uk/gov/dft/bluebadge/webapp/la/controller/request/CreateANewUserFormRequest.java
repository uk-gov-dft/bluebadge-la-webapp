package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

import java.util.Objects;

@Data
public class CreateANewUserFormRequest {
  private String emailAddress;
  private String name;
}
