package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.dft.bluebadge.webapp.la.LocalAuthorityApplication;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;

@WebAppConfiguration
@SpringBootTest(
  classes = LocalAuthorityApplication.class,
  properties = {"management.server.port=19991"}
)
@ActiveProfiles("test")
public abstract class MockMVCWithSecurityTests {
  public static final String VALID_USERNAME_1 = "abc@dft.gov.uk";
  public static final String USERNAME_1_PASSWORD = "password";

  @Autowired protected WebApplicationContext wac;
  protected MockMvc mockMvc;

  @Autowired private RestTemplateFactory restTemplateFactory;
  protected MockRestServiceServer mockServer;

  @Before
  public void setupMockMVC() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();

    mockServer = MockRestServiceServer.createServer(restTemplateFactory.getInstance());
  }
}
