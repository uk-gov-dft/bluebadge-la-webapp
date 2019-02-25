package uk.gov.dft.bluebadge.webapp.la;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import uk.gov.dft.bluebadge.webapp.la.config.ActuatorSecurityConfig;
import uk.gov.dft.bluebadge.webapp.la.config.ApiConfig;
import uk.gov.dft.bluebadge.webapp.la.config.CustomTomcatConnectorConfiguration;
import uk.gov.dft.bluebadge.webapp.la.config.FilterConfig;
import uk.gov.dft.bluebadge.webapp.la.config.MessagesConfig;
import uk.gov.dft.bluebadge.webapp.la.config.MethodSecurityConfig;
import uk.gov.dft.bluebadge.webapp.la.config.OpenEndpointsSecurityConfig;
import uk.gov.dft.bluebadge.webapp.la.config.SpringSecurityConfig;
import uk.gov.dft.bluebadge.webapp.la.config.SwaggerConfig;
import uk.gov.dft.bluebadge.webapp.la.config.TemplateEngineAndResolverConfig;

/**
 * When running spring context based integration tests in jenkins as junit tests, they fail because
 * we do not have an instance of redis at that point, only for acceptance tests. As we do not really
 * need redis for this tests, we use this test config to not include RedisSessionConfig class.
 */
@TestConfiguration
@Import({
  ActuatorSecurityConfig.class,
  ApiConfig.class,
  CustomTomcatConnectorConfiguration.class,
  FilterConfig.class,
  MessagesConfig.class,
  MethodSecurityConfig.class,
  OpenEndpointsSecurityConfig.class,
  SpringSecurityConfig.class,
  SwaggerConfig.class,
  TemplateEngineAndResolverConfig.class
})
public class IntegrationTestNoRedisConfig {

  IntegrationTestNoRedisConfig() {}
}
