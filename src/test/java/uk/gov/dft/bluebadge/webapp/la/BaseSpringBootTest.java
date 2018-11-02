package uk.gov.dft.bluebadge.webapp.la;

import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = {"management.server.port=0", "spring.redis.port=35623"}
)
@ActiveProfiles({"test", "dev"})
@Slf4j
public abstract class BaseSpringBootTest {
  @LocalServerPort protected int port;
  @LocalManagementPort protected int managementPort;
  private static RedisServer REDISSERVER = new RedisServer(35623);

  @BeforeClass
  public static void before() {
    log.info("Starting embedded redis on port:35623 xxxxxxxxxxxxxxxxxxxxx");
    REDISSERVER.start();
  }

  @AfterClass
  public static void after() {
    REDISSERVER.stop();
  }
}
