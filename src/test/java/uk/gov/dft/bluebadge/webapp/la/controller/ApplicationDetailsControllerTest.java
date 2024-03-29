package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
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
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTransfer;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationUpdate;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToLookupBadgeViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UpdateApplicationFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.LookupBadgeViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationDetailsTestData;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationToOrderBadgeTestData;

public class ApplicationDetailsControllerTest extends BaseControllerTest {
  private static final String BADGE_NUMBER = "123";
  private static final LocalDate BADGE_EXPIRY_DATE = LocalDate.of(2050, 2, 24);
  private static final String BADGE_EXPIRY_DATE_VIEW_MODEL = "2050-02-24";
  private static final String BADGE_STATUS = "ISSUED";
  private static final Badge BADGE =
      new Badge().badgeNumber(BADGE_NUMBER).expiryDate(BADGE_EXPIRY_DATE).statusCode(BADGE_STATUS);
  private static final LookupBadgeViewModel LOOKUP_BADGE_VIEW_MODEL =
      LookupBadgeViewModel.builder()
          .badgeNumber(BADGE_NUMBER)
          .status(BADGE_STATUS)
          .expiryDate(BADGE_EXPIRY_DATE_VIEW_MODEL)
          .build();

  @Mock private ApplicationService applicationServiceMock;
  @Mock private ReferenceDataService referenceDataServiceMock;
  @Mock private BadgeService badgeServiceMock;
  @Mock private BadgeToLookupBadgeViewModel badgeToLookupViewModelMock;
  @Mock private SecurityUtils securityUtilsMock;

  MockMvc mockMvc;

  private ApplicationDetailsController controller;

  private List<ReferenceData> applicationStatusOptions;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    controller =
        new ApplicationDetailsController(
            applicationServiceMock,
            referenceDataServiceMock,
            badgeServiceMock,
            badgeToLookupViewModelMock,
            securityUtilsMock);
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
    when(badgeServiceMock.retrieve(any())).thenReturn(Optional.empty());
  }

  @Test
  @SneakyThrows
  public void show_Org() {
    Application application = ApplicationDetailsTestData.getOrganisationApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", false));
  }

  @Test
  @SneakyThrows
  public void show_person_pip() {
    Application application = ApplicationDetailsTestData.getPersonPipApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  @SneakyThrows
  public void show_person_dla() {
    Application application = ApplicationDetailsTestData.getPersonDlaApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  @SneakyThrows
  public void show_person_dla_with_existing_badge() {
    Application application = ApplicationDetailsTestData.getPersonDlaApp();
    application.setExistingBadgeNumber(BADGE_NUMBER);
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();
    when(badgeServiceMock.retrieve(BADGE_NUMBER)).thenReturn(Optional.of(BADGE));
    when(badgeToLookupViewModelMock.convert(BADGE)).thenReturn(LOOKUP_BADGE_VIEW_MODEL);

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", LOOKUP_BADGE_VIEW_MODEL))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  @SneakyThrows
  public void show_person_afrfcs() {
    Application application = ApplicationDetailsTestData.getPersonAfrFcsApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  @SneakyThrows
  public void show_person_wpms() {
    Application application = ApplicationDetailsTestData.getPersonWpmsApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  @SneakyThrows
  public void show_person_blind() {
    Application application = ApplicationDetailsTestData.getPersonBlindApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  @SneakyThrows
  public void show_person_walking() {
    Application application = ApplicationDetailsTestData.getPersonWalkingApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  @SneakyThrows
  public void show_person_arms() {
    Application application = ApplicationDetailsTestData.getPersonArmsApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  @SneakyThrows
  public void show_person_childbulk() {
    Application application = ApplicationDetailsTestData.getPersonChildbulkApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  @SneakyThrows
  public void show_person_childvehicle() {
    Application application = ApplicationDetailsTestData.getPersonChildvehicleApp();
    when(applicationServiceMock.retrieve(ApplicationDetailsTestData.ModelValues.ID))
        .thenReturn(application);
    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder()
            .applicationStatus(application.getApplicationStatus())
            .build();

    mockMvc
        .perform(get("/applications/" + ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(status().isOk())
        .andExpect(view().name("applications/application-details"))
        .andExpect(model().attribute("app", application))
        .andExpect(model().attribute("applicationStatusOptions", applicationStatusOptions))
        .andExpect(model().attribute("updateApplicationFormRequest", expectedUpdateFormRequest))
        .andExpect(model().attribute("uuid", ApplicationDetailsTestData.ModelValues.UUID))
        .andExpect(model().attribute("existingBadge", Matchers.nullValue()))
        .andExpect(model().attribute("renderOrderBadgeButton", true));
  }

  @Test
  public void delete_shouldRedirectToApplicationsPage() throws Exception {
    mockMvc
        .perform(delete("/applications/" + ApplicationDetailsTestData.ModelValues.UUID.toString()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/applications"));
    verify(applicationServiceMock).delete(ApplicationDetailsTestData.ModelValues.UUID.toString());
  }

  @Test
  public void
      orderABadgeForApplication_shouldCreateFormRequestsOnSessionAndRedirectToOrderABadgeStepPersonDetails()
          throws Exception {
    String applicationId = ApplicationToOrderBadgeTestData.getApplication().getApplicationId();

    mockMvc
        .perform(post("/applications/" + applicationId))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge/application/" + applicationId));
  }

  @Test
  @SneakyThrows
  public void update_shouldUpdateAndRedirectToApplications() {
    ApplicationStatusField NEW_STATUS = ApplicationStatusField.TODO;
    Application application = ApplicationDetailsTestData.getPersonChildvehicleApp();
    application.setApplicationStatus(NEW_STATUS);
    String applicationId = application.getApplicationId();
    when(applicationServiceMock.retrieve(applicationId)).thenReturn(application);

    UpdateApplicationFormRequest expectedUpdateFormRequest =
        UpdateApplicationFormRequest.builder().applicationStatus(NEW_STATUS).build();

    mockMvc
        .perform(
            put("/applications/" + applicationId).param("applicationStatus", NEW_STATUS.name()))
        .andExpect(status().is3xxRedirection())
        .andExpect(
            redirectedUrl(
                ApplicationDetailsController.URL_NEW_APPLICATIONS_UUID.replace(
                    "{uuid}", applicationId)))
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
  @SneakyThrows
  public void transfer_shouldTransferApplicationAndRedirectToNewApplications() {
    Application application = ApplicationDetailsTestData.getPersonChildvehicleApp();
    String applicationId = application.getApplicationId();
    String TRANSFER_TO_LA_SHORTCODE = "KENTCC";
    when(applicationServiceMock.retrieve(applicationId)).thenReturn(application);

    mockMvc
        .perform(
            post("/applications/" + applicationId + "/transfers")
                .param("transferToLaShortCode", TRANSFER_TO_LA_SHORTCODE))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/applications"));

    ApplicationTransfer applicationTransfer =
        ApplicationTransfer.builder().transferToLaShortCode(TRANSFER_TO_LA_SHORTCODE).build();
    verify(applicationServiceMock).transfer(applicationId, applicationTransfer);
  }

  @Test
  @SneakyThrows
  public void transfer_la_required() {
    Application application = ApplicationDetailsTestData.getPersonChildvehicleApp();
    String applicationId = UUID.randomUUID().toString();
    application.setApplicationId(applicationId);
    when(applicationServiceMock.retrieve(applicationId)).thenReturn(application);

    mockMvc
        .perform(post("/applications/" + applicationId + "/transfers"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/applications/" + applicationId + "#error"))
        .andExpect(formRequestFlashAttributeCount(1, "transferApplicationFormRequest"))
        .andExpect(
            formRequestFlashAttributeHasFieldErrorCode(
                "transferToLaShortCode", "NotBlank", "transferApplicationFormRequest"));

    verify(applicationServiceMock, never()).transfer(eq(applicationId), any());
  }

  @Test
  public void applicationStatusOptions_shouldReturnApplicationStatusOptions() {
    List<ReferenceData> options = controller.applicationStatusOptions();
    assertThat(options).isEqualTo(applicationStatusOptions);
  }
}
