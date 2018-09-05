package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;

public class ErrorControllerTest {

  private MockMvc mockMvc;

  private ErrorHandlerController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new ErrorHandlerController();

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void showHome_shouldDisplayErrorPageWithMessages_WhenUserIsSignedIn() throws Exception {
    String errorMessage = "error message";
    Exception ex = new Exception(errorMessage);
    mockMvc
        .perform(get("/unexpected-error").flashAttr("exception", ex))
        .andExpect(status().isOk())
        .andExpect(view().name("unexpected-error"))
        .andExpect(model().attribute("errorMessage", "java.lang.Exception"))
        .andExpect(model().attribute("exceptionMessage", errorMessage));
  }
}
