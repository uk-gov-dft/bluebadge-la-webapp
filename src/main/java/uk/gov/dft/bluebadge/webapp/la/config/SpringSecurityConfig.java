package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;

@Configuration
@EnableOAuth2Client
@Order(52)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String LANDING_PAGE_URL = "/new-applications";

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
        .defaultSuccessUrl(LANDING_PAGE_URL, true)
        .and()
        .logout()
        .logoutUrl("/sign-out");
  }

  @Bean
  UserInfoTokenServices userInfoTokenServices(
      ResourceServerProperties authServerProps, OAuth2RestOperations restTemplate) {
    UserInfoTokenServices services =
        new UserInfoTokenServices(authServerProps.getUserInfoUri(), authServerProps.getClientId());
    services.setTokenType(authServerProps.getTokenType());
    services.setRestTemplate(restTemplate);
    return services;
  }

  @Bean
  public SecurityUtils securityUtils() {
    return new SecurityUtils();
  }
}
