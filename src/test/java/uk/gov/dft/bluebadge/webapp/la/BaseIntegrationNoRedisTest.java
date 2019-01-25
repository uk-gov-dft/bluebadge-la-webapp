package uk.gov.dft.bluebadge.webapp.la;

import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = {"management.server.port=0"},
  classes = IntegrationTestNoRedisConfig.class
)
@ActiveProfiles({"test", "dev"})
public abstract class BaseIntegrationNoRedisTest {
  @LocalServerPort protected int port;
  @LocalManagementPort protected int managementPort;
}
