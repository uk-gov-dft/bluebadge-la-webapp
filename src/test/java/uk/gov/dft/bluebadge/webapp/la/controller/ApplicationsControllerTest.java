package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
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

    when(applicationServiceMock.findAll(any(), any(PagingInfo.class))).thenReturn(allApplications);
    mockMvc
        .perform(get("/applications?pageNum=2&pageSize=23"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(model().attribute("applications", APPLICATION_VIEW_MODELS_ONE_ITEM))
        .andExpect(model().attribute("applicationCount", 1L))
        .andExpect(model().attributeExists("pagingInfo"));

    ArgumentCaptor<PagingInfo> captor = ArgumentCaptor.forClass(PagingInfo.class);
    verify(applicationServiceMock, times(2)).findAll(isNull(), captor.capture());

    assertThat(captor.getAllValues()).hasSize(2);
    PagingInfo pagingInfo = captor.getAllValues().get(0);
    assertThat(pagingInfo).isNotNull();
    assertThat(pagingInfo.getPageNum()).isEqualTo(2);
    assertThat(pagingInfo.getPageSize()).isEqualTo(23);

    // This is within the setup model - where the total application count is required
    pagingInfo = captor.getAllValues().get(1);
    assertThat(pagingInfo).isNotNull();
    assertThat(pagingInfo.getPageNum()).isEqualTo(1);
    assertThat(pagingInfo.getPageSize()).isEqualTo(1);
  }

  @Test
  public void show_shouldDisplayApplications_whenThereAreApplications_withDefaultPaging()
      throws Exception {

    when(applicationServiceMock.findAll(any(), any(PagingInfo.class))).thenReturn(allApplications);
    mockMvc
        .perform(get("/applications"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(model().attribute("applications", APPLICATION_VIEW_MODELS_ONE_ITEM))
        .andExpect(model().attributeExists("pagingInfo"));

    ArgumentCaptor<PagingInfo> captor = ArgumentCaptor.forClass(PagingInfo.class);
    verify(applicationServiceMock, times(2)).findAll(isNull(), captor.capture());

    assertThat(captor.getAllValues()).hasSize(2);
    PagingInfo pagingInfo = captor.getAllValues().get(0);
    assertThat(pagingInfo).isNotNull();
    assertThat(pagingInfo.getPageNum()).isEqualTo(1);
    assertThat(pagingInfo.getPageSize()).isEqualTo(50);

    // This is within the setup model - where the total application count is required
    pagingInfo = captor.getAllValues().get(1);
    assertThat(pagingInfo).isNotNull();
    assertThat(pagingInfo.getPageNum()).isEqualTo(1);
    assertThat(pagingInfo.getPageSize()).isEqualTo(1);
  }

  @Test
  public void findByName_shouldReturnEmptyResult_whenNameDoesntExist() throws Exception {

    when(applicationServiceMock.findByName(any(), any(), any())).thenReturn(noApplications);
    when(applicationServiceMock.findAll(any(), any(PagingInfo.class))).thenReturn(allApplications);

    MvcResult mvcResult =
        mockMvc
            .perform(get("/applications?searchBy=name&searchTerm=anyone&pageNum=1&pageSize=50"))
            .andExpect(status().isOk())
            .andExpect(view().name("applications/index"))
            .andExpect(model().attribute("applications", Collections.emptyList()))
            .andExpect(model().attribute("applicationCount", 1L))
            .andExpect(model().attributeExists("pagingInfo"))
            .andReturn();

    PagingInfo pagingInfo =
        (PagingInfo) mvcResult.getModelAndView().getModelMap().get("pagingInfo");
    assertThat(pagingInfo).isNotNull();
    assertThat(pagingInfo.getTotal()).isEqualTo(0);
    assertThat(pagingInfo.getCount()).isEqualTo(0);

    verifyZeroInteractions(converterMock);
    verify(applicationServiceMock, times(1)).findByName(eq("anyone"), isNull(), any());
    verify(applicationServiceMock, times(1)).findAll(isNull(), any());
  }

  @Test
  public void findByName_shouldReturnResult_whenNameDoesExist() throws Exception {

    when(applicationServiceMock.findByName(any(), any(), any())).thenReturn(applicationsByName);
    when(applicationServiceMock.findAll(any(), any(PagingInfo.class))).thenReturn(allApplications);

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

    verify(applicationServiceMock, times(1)).findByName(eq("john"), isNull(), any());
    verify(applicationServiceMock, times(1)).findAll(isNull(), any());
    verify(applicationServiceMock, never()).findByPostCode(any(), any(), any());
  }

  @Test
  public void findByName_andFilterByNewType_shouldReturnResult_whenNameDoesExist()
      throws Exception {
    when(applicationServiceMock.findByName(any(), any(), any()))
        .thenReturn(applicationsByNameFilteredByNewAppType);
    when(applicationServiceMock.findAll(any(), any(PagingInfo.class))).thenReturn(allApplications);

    when(converterMock.convert(applicationsForSearchByNameAndFilteredByNewAppType.get(0)))
        .thenReturn(applicationsForSearchByNameFilterByNewAppTypeView.get(0));
    when(converterMock.convert(applicationsForSearchByNameAndFilteredByNewAppType.get(1)))
        .thenReturn(applicationsForSearchByNameFilterByNewAppTypeView.get(1));

    mockMvc
        .perform(
            get(
                "/applications?searchBy=name&searchTerm=john&applicationTypeCode=CANCEL&pageNum=1&pageSize=50"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(
            model().attribute("applications", applicationsForSearchByNameFilterByNewAppTypeView));

    verify(applicationServiceMock, times(1))
        .findByName(eq("john"), eq(ApplicationTypeCodeField.CANCEL), any());
    verify(applicationServiceMock, times(1)).findAll(isNull(), any());
  }

  @Test
  public void findByPostcode() throws Exception {
    when(applicationServiceMock.findByPostCode(any(), any(), any()))
        .thenReturn(applicationsByNameFilteredByNewAppType);
    when(applicationServiceMock.findAll(any(), any(PagingInfo.class))).thenReturn(allApplications);

    when(converterMock.convert(applicationsForSearchByNameAndFilteredByNewAppType.get(0)))
        .thenReturn(applicationsForSearchByNameFilterByNewAppTypeView.get(0));
    when(converterMock.convert(applicationsForSearchByNameAndFilteredByNewAppType.get(1)))
        .thenReturn(applicationsForSearchByNameFilterByNewAppTypeView.get(1));

    mockMvc
        .perform(get("/applications?searchBy=postcode&searchTerm=M39BR"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(
            model().attribute("applications", applicationsForSearchByNameFilterByNewAppTypeView));

    verify(applicationServiceMock, times(1)).findByPostCode(eq("M39BR"), isNull(), any());
    verify(applicationServiceMock, times(1)).findAll(isNull(), any());

    verify(applicationServiceMock, never()).findByName(any(), any(), any());
  }

  @Test
  public void findByApplicationType() throws Exception {
    when(applicationServiceMock.findAll(eq(ApplicationTypeCodeField.RENEW), any()))
        .thenReturn(applicationsByNameFilteredByNewAppType);
    when(applicationServiceMock.findAll(isNull(), any(PagingInfo.class)))
        .thenReturn(allApplications);

    when(converterMock.convert(applicationsForSearchByNameAndFilteredByNewAppType.get(0)))
        .thenReturn(applicationsForSearchByNameFilterByNewAppTypeView.get(0));
    when(converterMock.convert(applicationsForSearchByNameAndFilteredByNewAppType.get(1)))
        .thenReturn(applicationsForSearchByNameFilterByNewAppTypeView.get(1));

    mockMvc
        .perform(get("/applications?searchBy=name&applicationTypeCode=RENEW"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/index"))
        .andExpect(
            model().attribute("applications", applicationsForSearchByNameFilterByNewAppTypeView));

    verify(applicationServiceMock, times(1)).findAll(eq(ApplicationTypeCodeField.RENEW), any());
    verify(applicationServiceMock, times(1)).findAll(isNull(), any());

    verify(applicationServiceMock, never()).findByPostCode(any(), any(), any());
    verify(applicationServiceMock, never()).findByName(any(), any(), any());
  }

  @Test
  public void findByName_shouldReturnBadRequest_whenPagingIsWrong() throws Exception {
    mockMvc
        .perform(get("/applications?searchBy=name&searchTerm=john&pageNum=1&pageSize=500"))
        .andExpect(status().isBadRequest());
  }
}
