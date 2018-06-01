package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

@Data
public class UserDetailsFormRequest {
  private Integer localAuthorityId;
  private String emailAddress;
  private String name;
}
