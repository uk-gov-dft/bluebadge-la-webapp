package uk.gov.dft.bluebadge.webapp.la.client.usermanagement;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

@Setter
@Getter
@Configuration
public class UserManagementServiceConfiguration {

  @Value("${usermanagementservice.servicehost.scheme}")
  private String scheme;

  @Value("${usermanagementservice.servicehost.host}")
  private String host;

  @Value("${usermanagementservice.servicehost.port}")
  private Integer port;

  @Value("${usermanagementservice.servicehost.connectiontimeout}")
  private Integer connectionTimeout;

  @Value("${usermanagementservice.servicehost.requesttimeout}")
  private Integer requestTimeout;

  @Value("${usermanagementservice.servicehost.contextpath}")
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
