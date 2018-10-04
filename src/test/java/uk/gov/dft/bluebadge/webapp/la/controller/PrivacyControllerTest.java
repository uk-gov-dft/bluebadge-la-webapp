package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;

public class PrivacyControllerTest {

  private MockMvc mockMvc;
  private PrivacyController controller;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    controller = new PrivacyController();

    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show() throws Exception {
    mockMvc
        .perform(get("/privacy-notice"))
        .andExpect(status().isOk())
        .andExpect(view().name("privacy"));
  }
}
