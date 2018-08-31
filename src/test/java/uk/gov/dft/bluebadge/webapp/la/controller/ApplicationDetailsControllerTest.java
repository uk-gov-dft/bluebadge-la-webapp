package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.ApplicationToApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class ApplicationDetailsControllerTest extends ApplicationTestData {
  @Mock private ApplicationService applicationServiceMock;
  @Mock private ApplicationToApplicationViewModel converterToViewModelMock;
  @Mock private Model modelMock;

  MockMvc mockMvc;

  private ApplicationDetailsController controller;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    controller = new ApplicationDetailsController(applicationServiceMock, converterToViewModelMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayApplicationDetails_WhenApplicationExists() throws Exception {
    when(applicationServiceMock.retrieve(APPLICATION_ID.toString()))
        .thenReturn(Optional.of(APPLICATION_FOR_PERSON_WALKING_DIFFICULTIES));
    controller.show(APPLICATION_ID, modelMock);

    when(converterToViewModelMock.convert(APPLICATION_FOR_PERSON_WALKING_DIFFICULTIES))
        .thenReturn(APPLICATION_VIEW_MODEL_FOR_PERSON_WALKING_DIFFICULTIES);
    mockMvc
        .perform(get("/new-applications/" + APPLICATION_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(
            model().attribute("app", APPLICATION_VIEW_MODEL_FOR_PERSON_WALKING_DIFFICULTIES));
  }

  @Test(expected = NotFoundException.class)
  public void show_shouldThrowNotFoundException_WhenApplicationDoesNotExist() throws Exception {
    when(applicationServiceMock.retrieve(APPLICATION_ID.toString())).thenReturn(Optional.empty());
    controller.show(APPLICATION_ID, modelMock);
    verify(converterToViewModelMock, times(0)).convert(any());
  }
}
