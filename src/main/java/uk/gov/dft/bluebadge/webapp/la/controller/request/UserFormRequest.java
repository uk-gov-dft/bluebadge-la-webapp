package uk.gov.dft.bluebadge.webapp.la.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.ValidationPatterns;

@Data
public class UserFormRequest {
  private String localAuthorityShortCode;

  @NotBlank(message = "{NotNull.user.emailAddress}")
  private String emailAddress;

  @NotBlank(message = "{NotNull.user.name}")
  private String name;

  @NotNull(message = "{NotNull.user.role}")
  private Role role;
}
