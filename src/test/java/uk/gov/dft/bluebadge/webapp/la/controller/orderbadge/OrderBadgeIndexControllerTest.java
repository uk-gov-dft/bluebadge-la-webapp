package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeIndexFormRequest;

public class OrderBadgeIndexControllerTest extends OrderBadgeBaseControllerTest {
  private static final String URL_ORDER_A_BADGE_PERSON_DETAILS = "/order-a-badge/person/details";
  private static final String URL_ORDER_A_BADGE_ORGANISATION_DETAILS =
      "/order-a-badge/organisation/details";

  private static final String SESSION_FORM_REQUEST = "formRequest-order-a-badge-index";

  private static final String APPLICANT_TYPE_PERSON = "person";
  private static final String APPLICANT_TYPE_ORGANISATION = "organisation";

  private MockMvc mockMvc;

  private OrderBadgeIndexController controller;

  @Before
  public void setup() {
    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new OrderBadgeIndexController();

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_ShouldDisplayTemplateWithEmptyValues_WhenIsAccessedFirstTime() throws Exception {
    OrderBadgeIndexFormRequest formRequest = OrderBadgeIndexFormRequest.builder().build();
    mockMvc
        .perform(get("/order-a-badge/"))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/index"))
        .andExpect(model().attribute("formRequest", formRequest));
  }

  @Test
  public void show_ShouldDisplayTemplateWithValuesFromSession_WhenThereIsASession()
      throws Exception {
    OrderBadgeIndexFormRequest formRequest =
        OrderBadgeIndexFormRequest.builder().applicantType(APPLICANT_TYPE_ORGANISATION).build();
    mockMvc
        .perform(get("/order-a-badge/").sessionAttr(SESSION_FORM_REQUEST, formRequest))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/index"))
        .andExpect(model().attribute("formRequest", formRequest));
  }

  @Test
  public void show_ShouldDisplayTemplateWithEmptyValues_WhenActionIsResetAndThereIsASession()
      throws Exception {
    OrderBadgeIndexFormRequest sessionFormRequest =
        OrderBadgeIndexFormRequest.builder().applicantType(APPLICANT_TYPE_ORGANISATION).build();
    OrderBadgeIndexFormRequest expectedModelFormRequest =
        OrderBadgeIndexFormRequest.builder().build();
    mockMvc
        .perform(
            get("/order-a-badge/?action=reset")
                .sessionAttr(SESSION_FORM_REQUEST, sessionFormRequest))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/index"))
        .andExpect(model().attribute("formRequest", expectedModelFormRequest));
  }

  @Test
  public void submit_ShouldRediretToPersonDetailsTemplate_WhenApplicationTypeIsPerson()
      throws Exception {
    mockMvc
        .perform(post("/order-a-badge/").param("applicantType", APPLICANT_TYPE_PERSON))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(URL_ORDER_A_BADGE_PERSON_DETAILS));
  }

  @Test
  public void submit_ShouldRediretToOrganisationDetailsTemplate_WhenApplicationTypeIsOrganisation()
      throws Exception {
    mockMvc
        .perform(post("/order-a-badge/").param("applicantType", APPLICANT_TYPE_ORGANISATION))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(URL_ORDER_A_BADGE_ORGANISATION_DETAILS));
  }
}
