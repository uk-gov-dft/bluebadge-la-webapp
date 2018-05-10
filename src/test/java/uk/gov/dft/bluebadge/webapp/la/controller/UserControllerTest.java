package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

@ContextConfiguration
@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
//@WebMvcTest(UserControllerImpl.class)
public class UserControllerTest extends ControllerTest {

  @Autowired private WebApplicationContext ctx;

  //@Autowired
  private MockMvc mockMvc;

  @Mock private UserService service;

  private UserController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new UserControllerImpl(service);

    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(this.ctx);
    templateResolver.setPrefix("classpath:/templates/");
    templateResolver.setSuffix(".html");
    // HTML is the default value, added here for the sake of clarity.
    templateResolver.setTemplateMode(TemplateMode.HTML);
    // Template cache is true by default. Set to false if you want
    // templates to be automatically updated when modified.
    templateResolver.setCacheable(true);

    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
    // speed up execution in most scenarios, but might be incompatible
    // with specific cases when expressions in one template are reused
    // across different data types, so this flag is "false" by default
    // for safer backwards compatibility.
    templateEngine.setEnableSpringELCompiler(true);

    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine);
    // NOTE 'order' and 'viewNames' are optional
    viewResolver.setOrder(1);
    viewResolver.setCache(false);
    viewResolver.setViewNames(new String[] {".html", ".xhtml"});

    // Setup Spring test in standalone mode
    //this.mockMvc =
    //MockMvcBuilders.webAppContextSetup(ctx).build();
    //this.mockMvc =
    //  MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
    //MockMvcBuilders.standaloneSetup(controller).setViewResolvers(new ThymeleafViewResolver()).build();
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void myTest() throws Exception {
    mockMvc.perform(get("/sign-in")).andExpect(status().isOk());
    //        .andExpect(view().name("sign-in"));
    ;
  }
  /*
  @Test
  public void shouldDisplaySignInPage() throws Exception {
    ResultActions resultActions = whenIPerformAGetOnSignInEndpoint();
    thenSignInPageIsDisplayed(resultActions);
  }

  private ResultActions whenIPerformAGetOnSignInEndpoint() throws Exception {
    return mockMvc.perform(get("/sign-in"));
  }

  private void thenSignInPageIsDisplayed(ResultActions resultActions) throws Exception {
    resultActions.andExpect(status().isOk()).andExpect(view().name("/sign-in"));
  }*/
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
