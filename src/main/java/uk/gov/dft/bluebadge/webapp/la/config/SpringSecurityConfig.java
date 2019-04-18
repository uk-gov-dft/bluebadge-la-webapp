package uk.gov.dft.bluebadge.webapp.la.config;

import brave.spring.web.TracingClientHttpRequestInterceptor;
import com.google.common.collect.ImmutableList;
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
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import uk.gov.dft.bluebadge.common.security.BBAccessTokenConverter;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.security.BlueBadgeUserAuthenticationConverter;
import uk.gov.dft.bluebadge.webapp.la.security.CustomAccessDeniedHandler;
import uk.gov.dft.bluebadge.webapp.la.security.UserDetailsTokenService;

@Configuration
@EnableOAuth2Client
@Order(52)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String LANDING_PAGE_URL = "/";
  private static final String SIGN_IN_URL = "/sign-in";

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
  OAuth2RestTemplate restTemplate(
      ResourceOwnerPasswordResourceDetails resourceDetails,
      TracingClientHttpRequestInterceptor tracingClientHttpRequestInterceptor) {
    OAuth2RestTemplate oAuth2RestTemplate =
        new OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
    oAuth2RestTemplate.setInterceptors(ImmutableList.of(tracingClientHttpRequestInterceptor));
    return oAuth2RestTemplate;
  }

  @Bean
  ResourceOwnerPasswordAccessTokenProvider resourceOwnerPasswordAccessTokenProvider(
      TracingClientHttpRequestInterceptor tracingClientHttpRequestInterceptor) {
    ResourceOwnerPasswordAccessTokenProvider resourceOwnerPasswordAccessTokenProvider =
        new ResourceOwnerPasswordAccessTokenProvider();
    resourceOwnerPasswordAccessTokenProvider.setInterceptors(
        ImmutableList.of(tracingClientHttpRequestInterceptor));
    return resourceOwnerPasswordAccessTokenProvider;
  }

  @Bean
  @ConfigurationProperties(prefix = "security.oauth2.client")
  @Primary
  public ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails() {
    return new ResourceOwnerPasswordResourceDetails();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**")
        .authorizeRequests()
        .antMatchers(SIGN_IN_URL, "/css/**", "/images/**", "/js/**", "/govuk/**")
        .permitAll()
        .antMatchers("/applications", "/applications/**")
        .hasAuthority(Permissions.FIND_APPLICATION.getPermissionName())
        .antMatchers("/order-a-badge", "/order-a-badge/**")
        .hasAuthority(Permissions.ORDER_BADGE.getPermissionName())
        .antMatchers("/manage-badges/export-all-la-badges")
        .hasAuthority(Permissions.VIEW_BADGE_DETAILS_ZIP.getPermissionName())
        .antMatchers("/manage-badges", "/manage-badges/**")
        .hasAuthority(Permissions.FIND_BADGES.getPermissionName())
        .antMatchers("/manage-users", "/manage-users/**")
        .hasAuthority(Permissions.FIND_USERS.getPermissionName())
        .antMatchers("/manage-local-authorities", "/manage-local-authorities/**")
        .hasAuthority(Permissions.MANAGE_LOCAL_AUTHORITIES.getPermissionName())
        .antMatchers("/manage-local-councils", "/manage-local-councils/**")
        .hasAuthority(Permissions.MANAGE_LOCAL_AUTHORITIES.getPermissionName())
        .anyRequest()
        .fullyAuthenticated()
        .and()
        .formLogin()
        .loginPage(SIGN_IN_URL)
        .permitAll()
        .defaultSuccessUrl(LANDING_PAGE_URL, true)
        .and()
        .logout()
        .logoutUrl("/sign-out")
        .and()
        .csrf()
        .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
        .and()
        .exceptionHandling()
        .accessDeniedPage("/something-went-wrong")
        .accessDeniedHandler(accessDeniedHandler())
        .and()
        .sessionManagement()
        .invalidSessionUrl(SIGN_IN_URL);
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
    BlueBadgeUserAuthenticationConverter userTokenConverter =
        new BlueBadgeUserAuthenticationConverter(userDetailsTokenService());
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
