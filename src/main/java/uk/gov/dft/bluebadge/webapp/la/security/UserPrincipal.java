package uk.gov.dft.bluebadge.webapp.la.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Getter
public class UserPrincipal implements UserDetails {

  private UUID uuid;
  private String emailAddress;
  private String name;
  @JsonIgnore private String password;
  private String localAuthorityShortCode;
  private String localAuthorityName;
  private String roleName;

  private Set<? extends GrantedAuthority> authorities;

  @Override
  public String getUsername() {
    return getEmailAddress();
  }

  @Builder.Default private boolean accountNonExpired = true;
  @Builder.Default private boolean accountNonLocked = true;
  @Builder.Default private boolean credentialsNonExpired = true;
  @Builder.Default private boolean enabled = true;
}
