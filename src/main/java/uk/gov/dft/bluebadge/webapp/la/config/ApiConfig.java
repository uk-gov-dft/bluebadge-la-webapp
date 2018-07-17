package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.webapp.la.client.common.ServiceConfiguration;

@Configuration
public class ApiConfig {

  @Autowired OAuth2ClientContext oauth2ClientContext;

  @ConfigurationProperties("blue-badge.badgemanagementservice.servicehost")
  @Bean
  public ServiceConfiguration badgeManagementApiConfig() {
    return new ServiceConfiguration();
  }

  @ConfigurationProperties("blue-badge.usermanagementservice.servicehost")
  @Bean
  public ServiceConfiguration userManagementApiConfig() {
    return new ServiceConfiguration();
  }

  @Bean("userManagementRestTemplate")
  OAuth2RestTemplate userManagementRestTemplate(
      ResourceOwnerPasswordResourceDetails resourceDetails,
      ServiceConfiguration userManagementApiConfig) {
    OAuth2RestTemplate oAuth2RestTemplate =
        new OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
    oAuth2RestTemplate.setUriTemplateHandler(
        new DefaultUriBuilderFactory(userManagementApiConfig.getUrlPrefix()));
    return oAuth2RestTemplate;
  }
}
