package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import uk.gov.dft.bluebadge.common.esapi.EsapiFilter;
import uk.gov.dft.bluebadge.common.logging.JwtMdcFilter;

@Configuration
public class FilterConfig {

  @Bean
  @Order(500)
  public EsapiFilter getEsapiFilter() {
    return new EsapiFilter();
  }

  @Bean
  public JwtMdcFilter getJwtMdcFilter() {
    return new JwtMdcFilter();
  }
}
