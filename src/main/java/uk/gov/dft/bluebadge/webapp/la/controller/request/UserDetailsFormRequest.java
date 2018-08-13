package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

@Data
public class UserDetailsFormRequest {
  private String localAuthorityShortCode;
  private String emailAddress;
  private String name;
}
