package uk.gov.dft.bluebadge.webapp.la.client.common;

import lombok.Data;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class ServiceConfiguration {

  private String scheme;

  private String host;

  private Integer port;

  private Integer connectiontimeout;

  private Integer requesttimeout;

  private String contextpath;

  public String getUrlPrefix() {
    return UriComponentsBuilder.newInstance()
        .scheme(scheme)
        .host(host)
        .port(port)
        .path(contextpath)
        .build()
        .toUriString();
  }
}
