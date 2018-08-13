package uk.gov.dft.bluebadge.webapp.la;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"management.server.port=19990"})
public class LocalAuthorityApplicationTests {

  @Test
  public void contextLoads() {}
}
