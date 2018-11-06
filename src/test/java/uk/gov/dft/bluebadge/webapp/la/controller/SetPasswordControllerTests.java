package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.dft.bluebadge.webapp.la.BaseSpringBootTest;

@RunWith(SpringRunner.class)
public class SetPasswordControllerTests extends BaseSpringBootTest {

  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setupMockMVC() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
  }

  @Test
  public void givenNoAuth_whenGetSetPasswordRequest_thenSuccess() throws Exception {
    mockMvc
        .perform(get("/set***REMOVED***/{uuid}", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andExpect(view().name("set***REMOVED***"));
  }
}
