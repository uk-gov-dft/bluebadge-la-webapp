package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.dft.bluebadge.webapp.la.BaseSpringBootTest;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationToOrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationToOrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationToOrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationDetailsTestData;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationToOrderBadgeTestData;

import javax.servlet.http.HttpSession;

@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationDetailsControllerTest extends BaseSpringBootTest {
    @Mock private ApplicationService applicationServiceMock;
    @Mock private ApplicationToOrderBadgeIndexFormRequest applicationToOrderBadgeIndexFormRequestMock;
    @Mock private ApplicationToOrderBadgePersonDetailsFormRequest applicationToOrderBadgePersonDetailsFormRequestMock;
    @Mock private ApplicationToOrderBadgeProcessingFormRequest applicationToOrderBadgeProcessingFormRequestMock;

    private static final String SESSION_INDEX_FORM_REQUEST = "formRequest-order-a-badge-index";
    private static final String SESSION_PERSON_DETAILS_FORM_REQUEST = "formRequest-order-a-badge-details";
    private static final String SESSION_PROCESSING_FORM_REQUEST = "formRequest-order-a-badge-processing";

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

    @Test
    public void orderABadgeForApplication_shouldCreateFormRequestsOnSessionAndRedirectToOrderABadgeStep2() throws Exception {
        when(applicationServiceMock.retrieve(ApplicationToOrderBadgeTestData.APPLICATION.getApplicationId()))
                .thenReturn(ApplicationToOrderBadgeTestData.APPLICATION);

        when(applicationToOrderBadgeIndexFormRequestMock.convert(ApplicationToOrderBadgeTestData.APPLICATION))
                .thenReturn(ApplicationToOrderBadgeTestData.APPLICATION_TO_ORDER_BADGE_INDEX_FORM_REQUEST);

        when(applicationToOrderBadgePersonDetailsFormRequestMock.convert(ApplicationToOrderBadgeTestData.APPLICATION))
                .thenReturn(ApplicationToOrderBadgeTestData.APPLICATION_TO_ORDER_BADGE_PERSON_DETAILS_FORM_REQUEST);

        when(applicationToOrderBadgeProcessingFormRequestMock.convert(ApplicationToOrderBadgeTestData.APPLICATION))
                .thenReturn(ApplicationToOrderBadgeTestData.APPLICATION_TO_ORDER_BADGE_PROCESSING_FORM_REQUEST);

        HttpSession session = mockMvc
                .perform(
                        post("/new-applications/" + ApplicationToOrderBadgeTestData.APPLICATION.getApplicationId()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/order-a-badge/person/details"))
                .andReturn().getRequest().getSession();

        assertThat(session.getAttribute(SESSION_INDEX_FORM_REQUEST)).isEqualTo(ApplicationToOrderBadgeTestData.APPLICATION_TO_ORDER_BADGE_INDEX_FORM_REQUEST);
        assertThat(session.getAttribute(SESSION_PERSON_DETAILS_FORM_REQUEST)).isEqualTo(ApplicationToOrderBadgeTestData.APPLICATION_TO_ORDER_BADGE_PERSON_DETAILS_FORM_REQUEST);
        assertThat(session.getAttribute(SESSION_PROCESSING_FORM_REQUEST)).isEqualTo(ApplicationToOrderBadgeTestData.APPLICATION_TO_ORDER_BADGE_PROCESSING_FORM_REQUEST);
    }
}
