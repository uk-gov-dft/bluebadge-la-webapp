package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationDetailsTestData;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(properties = {"management.server.port=18990"})
public class ApplicationDetailsControllerTest {
  @Mock private ApplicationService applicationServiceMock;

  @SuppressWarnings("unused")
  @Autowired
  private WebApplicationContext wac;

  MockMvc mockMvc;

  @SuppressWarnings("unused")
  @Autowired
  @InjectMocks
  private ApplicationDetailsController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void show_Org() throws Exception {
    Application application = ApplicationDetailsTestData.getOrganisationApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(
                    containsString(ApplicationDetailsTestData.ModelValues.ORG_BADGE_HOLDER_NAME)))
        .andExpect(
            content().string(containsString(ApplicationDetailsTestData.ModelValues.ORG_CHARITY_NO)))
        .andExpect(
            content()
                .string(
                    containsString(ApplicationDetailsTestData.DisplayValues.SUBMISSION_DATE_TIME)));
  }

  @Test
  public void show_person_pip() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonPipApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)));
  }

  @Test
  public void show_person_dla() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonDlaApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)));
  }

  @Test
  public void show_person_afrfcs() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonAfrFcsApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)));
  }

  @Test
  public void show_person_wpms() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonWpmsApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)));
  }

  @Test
  public void show_person_blind() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonBlindApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)));
  }

  @Test
  public void show_person_walking() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonWalkingApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)));
  }

  @Test
  public void show_person_arms() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonArmsApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)));
  }

  @Test
  public void show_person_childbulk() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonChildbulkApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)));
  }

  @Test
  public void show_person_childvehicle() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonChildvehicleApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)));
  }

  @Test
  public void delete_shouldRedirectToNewApplicationsPage() throws Exception {
    mockMvc
        .perform(
            delete("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID.toString()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/new-applications"));
    verify(applicationServiceMock).delete(ApplicationDetailsTestData.ModelValues.UUID.toString());
  }
}
