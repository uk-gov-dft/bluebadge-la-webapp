package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
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
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.ApplicationSummaryToApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class ApplicationsControllerTest extends ApplicationTestData {

  private MockMvc mockMvc;

  @Mock private ApplicationService applicationServiceMock;

  @Mock private ApplicationSummaryToApplicationViewModel converterMock;

  private ApplicationsController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    controller = new ApplicationsController(applicationServiceMock, converterMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    when(converterMock.convert(APPLICATION_SUMMARY_PERSON_1))
        .thenReturn(APPLICATION_PERSON_VIEW_MODEL_1);
  }

  @Test
  public void show_shouldDisplayApplications_whenThereAreApplications() throws Exception {

    when(applicationServiceMock.findAll(any(PagingInfo.class))).thenReturn(allApplications);
    mockMvc
        .perform(get("/applications?pageNum=1&pageSize=50"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(model().attribute("applications", APPLICATION_VIEW_MODELS_ONE_ITEM))
        .andExpect(model().attributeExists("pagingInfo"));
  }

  @Test
  public void show_shouldDisplayApplications_whenThereAreApplications_withDefaultPaging()
      throws Exception {

    when(applicationServiceMock.findAll(any(PagingInfo.class))).thenReturn(allApplications);
    mockMvc
        .perform(get("/applications"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(model().attribute("applications", APPLICATION_VIEW_MODELS_ONE_ITEM))
        .andExpect(model().attributeExists("pagingInfo"));
  }

  @Test
  public void findByName_shouldReturnEmptyResult_whenNameDoesntExist() throws Exception {

    when(applicationServiceMock.findByName(any(), any(), any())).thenReturn(noApplications);
    when(applicationServiceMock.findAll(any(PagingInfo.class))).thenReturn(allApplications);

    mockMvc
        .perform(get("/applications?searchBy=name&searchTerm=anyone&pageNum=1&pageSize=50"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(model().attribute("applications", Collections.emptyList()))
        .andExpect(model().attributeExists("pagingInfo"));
  }

  @Test
  public void findByName_shouldReturnResult_whenNameDoesExist() throws Exception {

    when(applicationServiceMock.findByName(any(), any(), any())).thenReturn(applicationsByName);
    when(applicationServiceMock.findAll(any(PagingInfo.class))).thenReturn(allApplications);

    when(converterMock.convert(applicationsForSearchByName.get(0)))
        .thenReturn(applicationsForSearchByNameView.get(0));
    when(converterMock.convert(applicationsForSearchByName.get(1)))
        .thenReturn(applicationsForSearchByNameView.get(1));
    when(converterMock.convert(applicationsForSearchByName.get(2)))
        .thenReturn(applicationsForSearchByNameView.get(2));

    mockMvc
        .perform(get("/applications?searchBy=name&searchTerm=john&pageNum=1&pageSize=50"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(model().attribute("applications", applicationsForSearchByNameView));
  }

  @Test
  public void findByName_andFilterByType_shouldReturnResult_whenNameDoesExist() throws Exception {

    when(applicationServiceMock.findByName(any(), any(), any()))
        .thenReturn(applicationsByNameFilteredByNewAppType);
    when(applicationServiceMock.findAll(any(PagingInfo.class))).thenReturn(allApplications);

    when(converterMock.convert(applicationsForSearchByName.get(0)))
        .thenReturn(applicationsForSearchByNameView.get(0));
    when(converterMock.convert(applicationsForSearchByName.get(1)))
        .thenReturn(applicationsForSearchByNameView.get(1));

    mockMvc
        .perform(get("/applications?searchBy=name&searchTerm=john&pageNum=1&pageSize=50"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(
            model().attribute("applications", applicationsForSearchByNameFilterByNewAppTypeView));
  }

  @Test
  public void findByName_shouldReturnBadRequest_whenPagingIsWrong() throws Exception {
    mockMvc
        .perform(get("/applications?searchBy=name&searchTerm=john&pageNum=1&pageSize=500"))
        .andExpect(status().isBadRequest());
  }
}
