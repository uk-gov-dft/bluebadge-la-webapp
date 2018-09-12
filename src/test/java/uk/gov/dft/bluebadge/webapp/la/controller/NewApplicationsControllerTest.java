package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.ApplicationSummaryToApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class NewApplicationsControllerTest extends ApplicationTestData {

  private MockMvc mockMvc;

  @Mock private ApplicationService applicationServiceMock;

  @Mock private ApplicationSummaryToApplicationViewModel converterMock;

  private NewApplicationsController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    controller = new NewApplicationsController(applicationServiceMock, converterMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    when(converterMock.convert(APPLICATION_SUMMARY_PERSON_1))
        .thenReturn(APPLICATION_PERSON_VIEW_MODEL_1);
  }

  @Test
  public void show_shouldDisplayApplications_whenThereAreApplications() throws Exception {
    when(applicationServiceMock.findAllNew())
        .thenReturn(ApplicationTestData.APPLICATION_SUMMARIES_ONE_ITEM);
    mockMvc
        .perform(get("/new-applications"))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/index"))
        .andExpect(model().attribute("applications", APPLICATION_VIEW_MODELS_ONE_ITEM));
  }

  @Test
  public void findByName_shouldReturnEmptyResult_whenNameDoesntExist() throws Exception {

    when(applicationServiceMock.findNewApplicationsByName("anyone"))
        .thenReturn(Collections.emptyList());

    mockMvc
        .perform(get("/new-applications?searchBy=name&searchTerm=anyone"))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications"))
        .andExpect(model().attribute("applications", Collections.emptyList()));
  }

  @Test
  public void findByName_shouldReturnResult_whenNameDoesExist() throws Exception {

    when(applicationServiceMock.findNewApplicationsByName("john"))
        .thenReturn(applicationsForSearchByName);

    when(converterMock.convert(applicationsForSearchByName.get(0)))
        .thenReturn(applicationsForSearchByNameView.get(0));
    when(converterMock.convert(applicationsForSearchByName.get(1)))
        .thenReturn(applicationsForSearchByNameView.get(1));
    when(converterMock.convert(applicationsForSearchByName.get(2)))
        .thenReturn(applicationsForSearchByNameView.get(2));

    mockMvc
        .perform(get("/new-applications?searchBy=name&searchTerm=john"))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications"))
        .andExpect(model().attribute("applications", applicationsForSearchByNameView));
  }
}
