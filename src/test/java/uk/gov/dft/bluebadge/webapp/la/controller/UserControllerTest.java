package uk.gov.dft.bluebadge.webapp.la.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String PASSWORD = "password";

  private MockMvc mockMvc;

  @Mock private UserService service;

  private UserController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new UserControllerImpl(service);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void shouldDisplaySignInPage() throws Exception {
    mockMvc
        .perform(get("/sign-in"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(
            model()
                .attribute(
                    "formRequest",
                    allOf(
                        hasProperty("email", isEmptyOrNullString()),
                        hasProperty("password", isEmptyOrNullString()))));
  }

  @Test
  public void shouldRedirectToHomePageWithEmail_WhenSignInIsSuccessful() throws Exception {
    when(service.isAuthorised(EMAIL, PASSWORD)).thenReturn(true);
    mockMvc
        .perform(post("/sign-in").param("email", EMAIL).param("password", PASSWORD))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/?email=" + EMAIL));
  }

  @Test
  public void
      shouldDisplaySignInTemplateAndShowAccessDeniedMessageAndHttpStatusIsOK_WhenSignInIsNotSuccessful()
          throws Exception {
    when(service.isAuthorised(EMAIL, PASSWORD)).thenReturn(false);
    mockMvc
        .perform(post("/sign-in").param("email", EMAIL).param("password", PASSWORD))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("accessDenied", is(true)));
  }

  @Test
  public void shouldDisplaySignInTemplateWithErrorMessageForEmailAndPasswordAndHttpStatusIsOK_WhenEmailAndPasswordAreEmpty() throws Exception {
    mockMvc
            .perform(post("/sign-in").param("email", "").param(" ***REMOVED***))
            .andExpect(status().isOk())
            .andExpect(view().name("sign-in"))
            .andExpect(model().attributeHasErrors("email"));
  }

  /*
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
  }*/
}
