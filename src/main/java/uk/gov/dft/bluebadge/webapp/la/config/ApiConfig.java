package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.dft.bluebadge.webapp.la.client.common.ServiceConfiguration;

@Configuration
public class ApiConfig {

  @ConfigurationProperties("badgemanagementservice.servicehost")
  @Bean
  public ServiceConfiguration badgeManagementApiConfig() {
    return new ServiceConfiguration();
  }

  @ConfigurationProperties("usermanagementservice.servicehost")
  @Bean
  public ServiceConfiguration userManagementApiConfig() {
    return new ServiceConfiguration();
  }
}
