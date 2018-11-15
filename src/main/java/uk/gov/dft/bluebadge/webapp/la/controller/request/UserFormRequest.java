package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import uk.gov.dft.bluebadge.common.security.Role;

@Data
public class UserFormRequest implements Serializable {
  private String localAuthorityShortCode;

  @NotBlank(message = "{NotNull.user.emailAddress}")
  private String emailAddress;

  @NotBlank(message = "{NotNull.user.name}")
  private String name;

  @NotNull(message = "{NotNull.user.role}")
  private Role role;
}
