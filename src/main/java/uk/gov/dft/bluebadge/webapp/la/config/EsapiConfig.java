package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import uk.gov.dft.bluebadge.common.esapi.EsapiFilter;

@Configuration
public class EsapiConfig {

  @Bean
  @Order(500)
  public EsapiFilter getEsapiFilter() {
    return new EsapiFilter();
  }
}
