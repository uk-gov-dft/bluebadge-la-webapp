package uk.gov.dft.bluebadge.webapp.la.integration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
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
public class SecuredPageTests extends BaseIntegrationNoRedisTest {

  @Autowired private WebApplicationContext wac;
  protected MockMvc mockMvc;

  @Before
  public void setupMockMVC() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
  }

  @Test
  @SneakyThrows
  public void givenNoAuth_whenHomepageRequested_thenRedirected() {
    mockMvc
        .perform(post("/").with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/sign-in"));
  }
}
