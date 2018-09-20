package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

@Data
public class UserFormRequest {
  private String localAuthorityShortCode;
  private String emailAddress;
  private String name;
  // @NotBlank(message = "{NotNull.badge.applicantType}")
  private String roleName;
}
