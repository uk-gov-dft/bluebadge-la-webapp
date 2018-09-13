package uk.gov.dft.bluebadge.webapp.la.security;

import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

/**
 * Extension to the DefaultUserAuthenticationConverter to allow using the authorities from the
 * token, but loading other details from the user management service.
 */
public class BlueBadgeUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
  private final UserDetailsService userDetailsTokenService;

  public BlueBadgeUserAuthenticationConverter(UserDetailsTokenService userDetailsTokenService) {
    this.userDetailsTokenService = userDetailsTokenService;
  }

  @Override
  public Authentication extractAuthentication(Map<String, ?> map) {
    Authentication authentication = super.extractAuthentication(map);
    UserDetails user = userDetailsTokenService.loadUserByUsername((String) map.get(USERNAME));
    return new UsernamePasswordAuthenticationToken(user, "N/A", authentication.getAuthorities());
  }

  @Override
  public void setUserDetailsService(UserDetailsService userDetailsService) {
    throw new UnsupportedOperationException();
  }
}
