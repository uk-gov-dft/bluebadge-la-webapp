package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

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

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public Integer getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(Integer connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public Integer getRequestTimeout() {
    return requestTimeout;
  }

  public void setRequestTimeout(Integer requestTimeout) {
    this.requestTimeout = requestTimeout;
  }

  public String getContextPath() {
    return contextPath;
  }

  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
  }

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
