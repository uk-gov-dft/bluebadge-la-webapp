package uk.gov.dft.bluebadge.webapp.la.client;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory {
  private CloseableHttpClient httpClient =
      HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

  private RestTemplate restTemplate;

  public RestTemplateFactory() {
    this.restTemplate = new RestTemplate();

    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    this.restTemplate.setRequestFactory(requestFactory);
  }

  public RestTemplate getInstance() {
    return restTemplate;
  }
}
