package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@EnableOAuth2Sso
@Order(52)
public class SpringSecurityConfig {}
