package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CredentialsFormRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CredentialsStoredControllerTest extends BaseControllerTest {

  MockMvc mockMvc;

  private CredentialsStoredController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    controller = new CredentialsStoredController();
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  @SneakyThrows
  public void show_shouldWork() {
    CredentialsFormRequest formRequest = CredentialsFormRequest.builder().build();
    mockMvc
        .perform(get("/credentials/stored"))
        .andExpect(status().isOk())
        .andExpect(view().name("credentials/stored"));
  }
}
