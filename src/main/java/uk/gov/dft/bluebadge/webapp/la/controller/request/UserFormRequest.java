package uk.gov.dft.bluebadge.webapp.la.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserFormRequest {
  private String localAuthorityShortCode;
  @NotBlank(message = "{NotNull.user.emailAddress}")
  private String emailAddress;
  @NotBlank(message = "{NotNull.user.name}")
  private String name;
  @NotBlank(message = "{NotNull.user.roleName}")
  private String roleName;
}
