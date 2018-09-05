package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class ApplicationDetailsControllerTest extends ApplicationTestData {
  @Mock private ApplicationService applicationServiceMock;
  @Mock private Model modelMock;

  MockMvc mockMvc;

  private ApplicationDetailsController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    controller = new ApplicationDetailsController(applicationServiceMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayApplicationDetails_WhenApplicationExists() throws Exception {
    when(applicationServiceMock.retrieve(APPLICATION_ID.toString()))
        .thenReturn(APPLICATION_FOR_PERSON_WALKING_DIFFICULTIES);
    controller.show(APPLICATION_ID, modelMock);

    mockMvc
        .perform(get("/new-applications/" + APPLICATION_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", APPLICATION_FOR_PERSON_WALKING_DIFFICULTIES));
  }
}
