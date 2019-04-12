package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.common.api.common.VersionHeaderRestTemplateInterceptor;
import uk.gov.dft.bluebadge.common.logging.LoggingAspect;
import uk.gov.dft.bluebadge.webapp.la.client.common.ServiceConfiguration;

@Configuration
public class ApiConfig {

  private OAuth2ClientContext oauth2ClientContext;

  @Autowired
  public ApiConfig(OAuth2ClientContext oauth2ClientContext) {
    this.oauth2ClientContext = oauth2ClientContext;
  }

  @ConfigurationProperties("security.oauth2.client")
  @Bean
  public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
    return new ClientCredentialsResourceDetails();
  }

  @Validated
  @ConfigurationProperties("blue-badge.badgemanagementservice.servicehost")
  @Bean
  public ServiceConfiguration badgeManagementApiConfig() {
    return new ServiceConfiguration();
  }

  @Validated
  @ConfigurationProperties("blue-badge.usermanagementservice.servicehost")
  @Bean
  public ServiceConfiguration userManagementApiConfig() {
    return new ServiceConfiguration();
  }

  @Validated
  @ConfigurationProperties("blue-badge.applicationsservice.servicehost")
  @Bean
  public ServiceConfiguration applicationsApiConfig() {
    return new ServiceConfiguration();
  }

  @Validated
  @ConfigurationProperties("blue-badge.referencedataservice.servicehost")
  @Bean
  public ServiceConfiguration referenceDataManagementApiConfig() {
    return new ServiceConfiguration();
  }

  @Bean("userManagementRestTemplate")
  OAuth2RestTemplate userManagementRestTemplate(
      ResourceOwnerPasswordResourceDetails resourceDetails,
      ServiceConfiguration userManagementApiConfig) {
    return createOAuthRestTemplate(resourceDetails, userManagementApiConfig);
  }

  @Bean("badgeManagementRestTemplate")
  OAuth2RestTemplate badgeManagementRestTemplate(
      ResourceOwnerPasswordResourceDetails resourceDetails,
      ServiceConfiguration badgeManagementApiConfig) {
    return createOAuthRestTemplate(resourceDetails, badgeManagementApiConfig);
  }

  @Bean("applicationsRestTemplate")
  OAuth2RestTemplate applicationsRestTemplate(
      ResourceOwnerPasswordResourceDetails resourceDetails,
      ServiceConfiguration applicationsApiConfig) {
    return createOAuthRestTemplate(resourceDetails, applicationsApiConfig);
  }

  @Bean("referenceDataRestTemplate")
  OAuth2RestTemplate referenceDataRestTemplate(
      ResourceOwnerPasswordResourceDetails resourceDetails,
      ServiceConfiguration referenceDataManagementApiConfig) {
    return createOAuthRestTemplate(resourceDetails, referenceDataManagementApiConfig);
  }

  private OAuth2RestTemplate createOAuthRestTemplate(
      ResourceOwnerPasswordResourceDetails resourceDetails, ServiceConfiguration apiConfig) {
    OAuth2RestTemplate oAuth2RestTemplate =
        new OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();
    oAuth2RestTemplate.setRequestFactory(requestFactory);
    oAuth2RestTemplate.setUriTemplateHandler(
        new DefaultUriBuilderFactory(apiConfig.getUrlPrefix()));
    // If a request is denied by the api, the default action is to clear the token and try again.
    // But this results in the user needing to request a new access token and hence essentially being logged out.
    oAuth2RestTemplate.setRetryBadAccessTokens(false);
    oAuth2RestTemplate
        .getInterceptors()
        .add(new VersionHeaderRestTemplateInterceptor(apiConfig.getVersionaccept()));
    return oAuth2RestTemplate;
  }

  /**
   * Creates a rest template with the LA Webapp client credentials, as the Set Password client is
   * dealing with unauthenticated requests.
   */
  @Bean("setPasswordRestTemplate")
  RestTemplate setPasswordRestTemplate(
      ClientCredentialsResourceDetails clientCredentialsResourceDetails,
      ServiceConfiguration userManagementApiConfig) {
    OAuth2RestTemplate result = new OAuth2RestTemplate(clientCredentialsResourceDetails);
    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();
    result.setRequestFactory(requestFactory);
    result.setUriTemplateHandler(
        new DefaultUriBuilderFactory(userManagementApiConfig.getUrlPrefix()));
    // If a request is denied by the api, the default action is to clear the token and try again.
    // But this results in the user needing to request a new access token and hence essentially being logged out.
    result.setRetryBadAccessTokens(false);
    return result;
  }

  @Bean
  LoggingAspect getControllerLoggingAspect() {
    return new LoggingAspect();
  }
}
