package uk.gov.dft.bluebadge.webapp.la.integration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.dft.bluebadge.webapp.la.BaseIntegrationNoRedisTest;

@RunWith(SpringRunner.class)
public class SetPasswordTests extends BaseIntegrationNoRedisTest {

  @Autowired private WebApplicationContext wac;
  protected MockMvc mockMvc;

  @Before
  public void setupMockMVC() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
  }

  @Test
  public void givenNoAuth_whenSetPasswordRequested_thenSuccess() throws Exception {
    mockMvc
        .perform(
            get("/set***REMOVED***/" + UUID.randomUUID().toString())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isOk());
  }
}
