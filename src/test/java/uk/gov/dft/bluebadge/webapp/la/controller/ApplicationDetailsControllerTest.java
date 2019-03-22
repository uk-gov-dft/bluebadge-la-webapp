package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationStatusField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationUpdate;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UpdateApplicationFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationDetailsTestData;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationToOrderBadgeTestData;

public class ApplicationDetailsControllerTest {
  @Mock private ApplicationService applicationServiceMock;
  @Mock private ReferenceDataService referenceDataServiceMock;
  @Mock private SecurityUtils securityUtils;

  MockMvc mockMvc;

  private ApplicationDetailsController controller;

  List<ReferenceData> applicationStatusOptions;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    controller = new ApplicationDetailsController(applicationServiceMock, referenceDataServiceMock, securityUtils);
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
    applicationStatusOptions =
        Lists.newArrayList(
            ReferenceData.builder().description("To do").shortCode("TODO").build(),
            ReferenceData.builder().description("In progress").shortCode("INPROGRESS").build(),
            ReferenceData.builder().description("Completed").shortCode("COMPLETED").build());
    when(referenceDataServiceMock.retrieveApplicationReferenceDataList(RefDataGroupEnum.APPSTATUS))
        .thenReturn(applicationStatusOptions);
  }

  public void show_Org() throws Exception {
    Application application = ApplicationDetailsTestData.getOrganisationApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(
                    containsString(ApplicationDetailsTestData.ModelValues.ORG_BADGE_HOLDER_NAME)))
        .andExpect(
            content().string(containsString(ApplicationDetailsTestData.ModelValues.ORG_CHARITY_NO)))
        .andExpect(
            content()
                .string(
                    containsString(ApplicationDetailsTestData.DisplayValues.SUBMISSION_DATE_TIME)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
  }

  public void show_person_pip() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonPipApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
  }

  public void show_person_dla() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonDlaApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
  }

  public void show_person_afrfcs() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonAfrFcsApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
  }

  public void show_person_wpms() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonWpmsApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
  }

  public void show_person_blind() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonBlindApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
  }

  public void show_person_walking() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonWalkingApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
  }

  public void show_person_arms() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonArmsApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
  }

  public void show_person_childbulk() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonChildbulkApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
  }

  public void show_person_childvehicle() throws Exception {
    Application application = ApplicationDetailsTestData.getPersonChildvehicleApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus().name())
            .build();

    mockMvc
        .perform(get("/new-applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(
            content()
                .string(containsString(ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME)))
        .andExpect(
            content()
                .string(
                    containsString(
                        ApplicationDetailsTestData.ModelValues.BADGE_HOLDER_NAME_AT_BIRTH)))
        .andExpect(content().string(containsString(ApplicationStatusField.COMPLETED.name())));
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

  @Test
  public void
      orderABadgeForApplication_shouldCreateFormRequestsOnSessionAndRedirectToOrderABadgeStepPersonDetails()
          throws Exception {
    String applicationId = ApplicationToOrderBadgeTestData.getApplication().getApplicationId();

    mockMvc
        .perform(post("/new-applications/" + applicationId))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge/application/" + applicationId));
  }

  @Test
  @SneakyThrows
  public void update_shouldUpdateAndRedirectToNewApplications() {
    ApplicationStatusField NEW_STATUS = ApplicationStatusField.TODO;
    Application application = ApplicationDetailsTestData.getPersonChildvehicleApp();
    application.setApplicationStatus(NEW_STATUS);
    String applicationId = application.getApplicationId();
    when(applicationServiceMock.retrieve(applicationId)).thenReturn(application);

    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder().applicationStatus(NEW_STATUS.name()).build();

    mockMvc
        .perform(
            put("/new-applications/" + applicationId).param("applicationStatus", NEW_STATUS.name()))
        .andExpect(status().isOk())
        .andExpect(view().name("new-applications/application-details"))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions));

    ApplicationUpdate applicationUpdate =
        ApplicationUpdate.builder()
            .applicationId(UUID.fromString(applicationId))
            .applicationStatus(NEW_STATUS)
            .build();
    verify(applicationServiceMock).update(applicationUpdate);
  }

  @Test
  public void applicationStatusOptions_shouldReturnApplicationStatusOptions() {
    List<ReferenceData> options = controller.applicationStatusOptions();
    assertThat(options).isEqualTo(applicationStatusOptions);
  }
}
