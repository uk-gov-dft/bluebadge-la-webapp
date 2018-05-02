package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.Application2ApplicationViewModelConverterImpl;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationCreateRequest2ApplicationConverterImpl;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationUpdateRequest2ApplicationConverterImpl;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ListConverter;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.service.model.Application;

public class ApplicationControllerTest {

  private MockMvc mockMvc;

  @Mock private ApplicationService service;

  private ApplicationControllerImpl controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new ApplicationControllerImpl(
            service,
            new ApplicationCreateRequest2ApplicationConverterImpl(),
            new ApplicationUpdateRequest2ApplicationConverterImpl(),
            new Application2ApplicationViewModelConverterImpl(),
            new ListConverter<Application, ApplicationViewModel>());

    // Setup Spring test in standalone mode
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void show_ShouldAddApplicationToModelAndRenderShowApplicationView_WhenApplicationExists()
      throws Exception {
    Application application = new Application(1L, "my full name");

    when(service.findById(1L)).thenReturn(Optional.of(application));

    mockMvc
        .perform(get("/applications/1/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/show"))
        .andExpect(
            model()
                .attribute(
                    "application",
                    allOf(hasProperty("id", is(1L)), hasProperty("fullname", is("my full name")))));

    verify(service, times(1)).findById(1L);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void show_ShouldAddIdToModelAndRenderNotFoundApplicationView_WhenApplicationDoesNotExist()
      throws Exception {
    when(service.findById(1L)).thenReturn(Optional.empty());

    mockMvc
        .perform(get("/applications/1/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/notFound"))
        .andExpect(model().attribute("id", is(1L)));

    verify(service, times(1)).findById(1L);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void showAll_ShouldAddApplicationsToModelAndRenderShowAllView_WhenApplicationsExist()
      throws Exception {
    Application application1 = new Application(1L, "my full name 1");
    Application application2 = new Application(2L, "my full name 2");
    Application application3 = new Application(3L, "my full name 2");
    List<Application> applications = Arrays.asList(application1, application2, application3);

    ApplicationViewModel applicationVM1 = new ApplicationViewModel(1L, "my full name 1");
    ApplicationViewModel applicationVM2 = new ApplicationViewModel(2L, "my full name 2");
    ApplicationViewModel applicationVM3 = new ApplicationViewModel(3L, "my full name 2");
    List<ApplicationViewModel> applicationVMs =
        Arrays.asList(applicationVM1, applicationVM2, applicationVM3);

    when(service.findAll()).thenReturn(applications);

    mockMvc
        .perform(get("/applications/showAll"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/showAll"))
        .andExpect(model().attribute("applications", is(applicationVMs)));

    verify(service, times(1)).findAll();
    verifyNoMoreInteractions(service);
  }

  @Test
  public void showCreate_ShouldShowCreateApplicationView() throws Exception {

    mockMvc
        .perform(get("/applications/showCreate"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/showCreate"));
  }

  @Test
  public void create_ShouldCreateApplicationAndShowAllView() throws Exception {
    Application application = new Application(15L, "myfullname15");
    when(service.create(application)).thenReturn(1);

    mockMvc
        .perform(get("/applications/create?id=15&fullname=myfullname15"))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/applications/showAll"));

    verify(service, times(1)).create(application);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void create_ShouldShowNotCreatedView_WhenApplicationIsNotCreated() throws Exception {
    Application application = new Application(15L, "myfullname15");
    when(service.create(application)).thenReturn(0);

    mockMvc
        .perform(get("/applications/create?id=15&fullname=myfullname15"))
        .andExpect(status().isOk())
        .andExpect(view().name("notCreated"));

    verify(service, times(1)).create(application);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void
      showUpdate_ShouldShowUpdateApplicationViewAndAddApplicationToModel_WhenApplicationExists()
          throws Exception {
    Application application = new Application(1L, "my full name");
    ApplicationViewModel applicationVM = new ApplicationViewModel(1L, "my full name");
    when(service.findById(1L)).thenReturn(Optional.of(application));

    mockMvc
        .perform(get("/applications/1/showUpdate"))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/showUpdate"))
        .andExpect(model().attribute("application", is(applicationVM)));

    verify(service, times(1)).findById(1L);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void update_ShouldUpdateApplicationAndShowAllView_WhenApplicationExists()
      throws Exception {
    Application application = new Application(15L, "myfullname15");
    when(service.update(application)).thenReturn(1);

    mockMvc
        .perform(get("/applications/update?id=15&fullname=myfullname15"))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/applications/showAll"));

    verify(service, times(1)).update(application);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void update_ShouldShowNotUpdatedView_WhenApplicationIsNotUpdated() throws Exception {
    Application application = new Application(15L, "myfullname15");
    when(service.update(application)).thenReturn(0);

    mockMvc
        .perform(get("/applications/update?id=15&fullname=myfullname15"))
        .andExpect(status().isOk())
        .andExpect(view().name("notUpdated"));

    verify(service, times(1)).update(application);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void delete_ShouldDeleteApplicationAndShowAllView_WhenApplicationExists()
      throws Exception {
    when(service.delete(15L)).thenReturn(1);

    mockMvc
        .perform(get("/applications/15/delete"))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/applications/showAll"));

    verify(service, times(1)).delete(15L);
    verifyNoMoreInteractions(service);
  }

  @Test
  public void delete_ShouldShowNotDeletedView_WhenApplicationDoesNotExist() throws Exception {
    Application application = new Application(15L, "myfullname15");
    when(service.update(application)).thenReturn(0);

    mockMvc
        .perform(get("/applications/15/delete"))
        .andExpect(status().isOk())
        .andExpect(view().name("notDeleted"));

    verify(service, times(1)).delete(15L);
    verifyNoMoreInteractions(service);
  }
}
