package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@Configuration
public class BadgeManagementServiceConfiguration {

  @Value("${badgemanagementservice.servicehost.scheme}")
  private String scheme;

  @Value("${badgemanagementservice.servicehost.host}")
  private String host;

  @Value("${badgemanagementservice.servicehost.port}")
  private Integer port;

  @Value("${badgemanagementservice.servicehost.connectiontimeout}")
  private Integer connectionTimeout;

  @Value("${badgemanagementservice.servicehost.requesttimeout}")
  private Integer requestTimeout;

  @Value("${badgemanagementservice.servicehost.contextpath}")
  private String contextPath;

  public String getUrlPrefix() {
    return UriComponentsBuilder.newInstance()
        .scheme(scheme)
        .host(host)
        .port(port)
        .path(contextPath)
        .build()
        .toUriString();
  }
}
