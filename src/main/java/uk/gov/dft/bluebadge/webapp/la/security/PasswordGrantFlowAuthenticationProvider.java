package uk.gov.dft.bluebadge.webapp.la.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.AuthServerConnectionException;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.InvalidEmailFormatException;

@Component
@Slf4j
public class PasswordGrantFlowAuthenticationProvider implements AuthenticationProvider {

  private final OAuth2RestTemplate oAuth2RestTemplate;
  private final ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails;
  private final ResourceOwnerPasswordAccessTokenProvider accessTokenProvider;
  private final UserInfoTokenServices userInfoTokenServices;

  @Autowired
  public PasswordGrantFlowAuthenticationProvider(
      OAuth2RestTemplate oAuth2RestTemplate,
      ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails,
      ResourceOwnerPasswordAccessTokenProvider accessTokenProvider,
      UserInfoTokenServices userInfoTokenServices) {
    this.oAuth2RestTemplate = oAuth2RestTemplate;
    this.resourceOwnerPasswordResourceDetails = resourceOwnerPasswordResourceDetails;
    this.accessTokenProvider = accessTokenProvider;
    this.userInfoTokenServices = userInfoTokenServices;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String username = (String) authentication.getPrincipal();
    log.debug("Attempting to authenticate username:{}", username);
    if (StringUtils.isEmpty(username)) {
      throw new InvalidEmailFormatException("Username is not in an email format: " + username);
    }

    String password = (String) authentication.getCredentials();
    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      throw new BadCredentialsException("Username and password are mandatory");
    }

    AccessTokenRequest accessTokenRequest =
        oAuth2RestTemplate.getOAuth2ClientContext().getAccessTokenRequest();
    accessTokenRequest.set("username", username);
    accessTokenRequest.set("password", password);

    try {
      log.debug("Obtaining access token for username:{}", username);
      OAuth2AccessToken oAuth2AccessToken =
          accessTokenProvider.obtainAccessToken(
              resourceOwnerPasswordResourceDetails, accessTokenRequest);

      OAuth2Authentication oAuth2Authentication =
          userInfoTokenServices.loadAuthentication(oAuth2AccessToken.getValue());
      return oAuth2Authentication;
    } catch (OAuth2AccessDeniedException ade) {
      if (ade.getCause() instanceof ResourceAccessException) {
        throw new AuthServerConnectionException("Failed to connect to authorisation service.", ade);
      }
      throw new BadCredentialsException("Failed to authenticate user.", ade);
    }
  }
}
