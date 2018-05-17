package uk.gov.dft.bluebadge.webapp.la;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableSwagger2
@ComponentScan(
  basePackages = {
    "uk.gov.dft.bluebadge.webapp.la.*",
    "uk.gov.dft.bluebadge.client.usermanagement.*"
  }
)
public class LocalAuthorityApplication {

  public static void main(String[] args) {
    SpringApplication.run(LocalAuthorityApplication.class, args);
  }
}
