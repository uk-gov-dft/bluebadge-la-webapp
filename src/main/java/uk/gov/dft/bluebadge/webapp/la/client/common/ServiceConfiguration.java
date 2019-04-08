package uk.gov.dft.bluebadge.webapp.la.client.common;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class ServiceConfiguration {

  @NotNull private String scheme;

  @NotNull private String host;

  @NotNull private Integer port;

  private Integer connectiontimeout;

  private Integer requesttimeout;

  @NotNull private String contextpath;

  private String versionaccept;

  public String getUrlPrefix() {
    return UriComponentsBuilder.newInstance()
        .scheme(scheme)
        .host(host)
        .port(port)
        .path(contextpath)
        .build()
        .toUriString();
  }

  public UriComponentsBuilder getUriComponentsBuilder(String... pathSegments) {
    return UriComponentsBuilder.newInstance()
        .scheme(scheme)
        .host(host)
        .port(port)
        .path(contextpath)
        .pathSegment(pathSegments);
  }
}
