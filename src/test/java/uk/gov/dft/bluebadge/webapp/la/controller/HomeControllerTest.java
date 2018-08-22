package uk.gov.dft.bluebadge.webapp.la.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HomeControllerTest {

  private MockMvc mockMvc;

  private HomeController controller;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    controller = new HomeController();

    this.mockMvc =
      MockMvcBuilders.standaloneSetup(controller)
        .setViewResolvers(new StandaloneMvcTestViewResolver())
        .build();
  }

  @Test
  public void showHome_shouldDisplayHomePageAndAddEmailAttribute_WhenUserIsSignedIn()
    throws Exception {
    mockMvc.perform(get("/"))
      .andExpect(status().isFound())
      .andExpect(redirectedUrl("/new-applications"));
  }
}
