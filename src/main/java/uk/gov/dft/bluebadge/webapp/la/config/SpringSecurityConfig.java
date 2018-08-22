package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import uk.gov.dft.bluebadge.common.security.BBAccessTokenConverter;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.security.UserDetailsTokenService;

@Configuration
@EnableOAuth2Client
@Order(52)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  @Value("${blue-badge.auth-server.url}")
  private String authServerUrl;

  @Value("${blue-badge.auth-server.client-id}")
  private String clientId;

  @Value("${blue-badge.auth-server.client-secret}")
  private String clientSecret;

  private OAuth2ClientContext oauth2ClientContext;

  @Autowired
  public SpringSecurityConfig(OAuth2ClientContext oauth2ClientContext) {
    this.oauth2ClientContext = oauth2ClientContext;
  }

  @Bean
  @Primary
  OAuth2RestTemplate restTemplate(ResourceOwnerPasswordResourceDetails resourceDetails) {
    return new OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
  }

  @Bean
  ResourceOwnerPasswordAccessTokenProvider resourceOwnerPasswordAccessTokenProvider() {
    return new ResourceOwnerPasswordAccessTokenProvider();
  }

  @Bean
  @ConfigurationProperties(prefix = "security.oauth2.client")
  @Primary
  public ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails() {
    return new ResourceOwnerPasswordResourceDetails();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/sign-in", "/css/**", "/images/**", "/js/**", "/govuk/**")
        .permitAll()
        .anyRequest()
        .fullyAuthenticated()
        .and()
        .formLogin()
        .loginPage("/sign-in")
        .permitAll()
        .defaultSuccessUrl("/", true)
        .and()
        .logout()
        .logoutUrl("/sign-out");
  }

  @Bean
  public RemoteTokenServices tokenService() {
    RemoteTokenServices tokenService = new RemoteTokenServices();
    tokenService.setCheckTokenEndpointUrl(authServerUrl + "/oauth/check_token");
    tokenService.setClientId(clientId);
    tokenService.setClientSecret(clientSecret);
    tokenService.setAccessTokenConverter(jwtAccessTokenConverter());
    return tokenService;
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setAccessTokenConverter(accessTokenConverter());
    return converter;
  }

  @Bean
  public BBAccessTokenConverter accessTokenConverter() {
    BBAccessTokenConverter converter = new BBAccessTokenConverter();
    DefaultUserAuthenticationConverter userTokenConverter =
        new DefaultUserAuthenticationConverter();
    userTokenConverter.setUserDetailsService(userDetailsTokenService());
    converter.setUserTokenConverter(userTokenConverter);
    return converter;
  }

  @Bean
  public UserDetailsTokenService userDetailsTokenService() {
    return new UserDetailsTokenService();
  }

  @Bean
  public SecurityUtils securityUtils() {
    return new SecurityUtils();
  }
}
