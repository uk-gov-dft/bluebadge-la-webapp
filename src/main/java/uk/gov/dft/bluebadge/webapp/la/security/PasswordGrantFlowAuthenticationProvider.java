package uk.gov.dft.bluebadge.webapp.la.security;

import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.AuthServerConnectionException;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.InvalidEmailFormatException;

@Component
@Slf4j
public class PasswordGrantFlowAuthenticationProvider implements AuthenticationProvider {

  private static final Pattern EMAIL_REGEX = Pattern.compile(".+\\@.+");
  public static final String USER_ACCOUNT_IS_LOCKED_MSG = "User account is locked";
  private final OAuth2RestTemplate oAuth2RestTemplate;
  private final ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails;
  private final ResourceOwnerPasswordAccessTokenProvider accessTokenProvider;
  private final ResourceServerTokenServices tokenService;

  @Autowired
  public PasswordGrantFlowAuthenticationProvider(
      OAuth2RestTemplate oAuth2RestTemplate,
      ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails,
      ResourceOwnerPasswordAccessTokenProvider accessTokenProvider,
      ResourceServerTokenServices tokenService) {
    this.oAuth2RestTemplate = oAuth2RestTemplate;
    this.resourceOwnerPasswordResourceDetails = resourceOwnerPasswordResourceDetails;
    this.accessTokenProvider = accessTokenProvider;
    this.tokenService = tokenService;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
  @Override
  public Authentication authenticate(Authentication authentication) {

    String username = (String) authentication.getPrincipal();
    log.debug("Attempting to authenticate username:{}", username);
    if (StringUtils.isEmpty(username) || !EMAIL_REGEX.matcher(username).find()) {
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

      return tokenService.loadAuthentication(oAuth2AccessToken.getValue());
    } catch (OAuth2AccessDeniedException ade) {
      if (ade.getCause() instanceof ResourceAccessException) {
        log.error("Authentication Failed. Failed to connect to authorisation service. {}", ade.getCause().getMessage(), ade);
        throw new AuthServerConnectionException("Failed to connect to authorisation service.");
      }
      log.debug("Authentication Failed. OAuth exception. {}", ade.getCause().getMessage(), ade);
      if (ade.getCause() instanceof InvalidGrantException
          && USER_ACCOUNT_IS_LOCKED_MSG.equals(ade.getCause().getMessage())) {
        throw new LockedException("Sign in failed. Account is locked.");
      }
      throw new BadCredentialsException("Failed to authenticate user.");
    }
  }
}
