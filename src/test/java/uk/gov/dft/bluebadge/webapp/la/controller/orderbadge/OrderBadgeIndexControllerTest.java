package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.advice.ErrorControllerAdvice;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;

public class OrderBadgeIndexControllerTest extends OrderBadgeBaseControllerTest {
  private static final String SESSION_FORM_REQUEST_INDEX = "formRequest-order-a-badge-index";
  private static final String SESSION_FORM_REQUEST_DETAILS = "formRequest-order-a-badge-details";
  private static final String SESSION_FORM_REQUEST_PROCESSING =
      "formRequest-order-a-badge-processing";

  private static final String APPLICANT_TYPE_PERSON = "person";
  private static final String APPLICANT_TYPE_ORGANISATION = "organisation";

  private static final OrderBadgeIndexFormRequest FORM_REQUEST_PERSON =
      OrderBadgeIndexFormRequest.builder().applicantType(APPLICANT_TYPE_PERSON).build();
  private static final OrderBadgeIndexFormRequest FORM_REQUEST_ORGANISATION =
      OrderBadgeIndexFormRequest.builder().applicantType(APPLICANT_TYPE_ORGANISATION).build();

  private MockMvc mockMvc;

  @Before
  public void setup() {
    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    OrderBadgeIndexController controller = new OrderBadgeIndexController();

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .setControllerAdvice(new ErrorControllerAdvice(new ObjectMapper()))
            .build();
  }

  @Test
  public void show_ShouldDisplayTemplateWithEmptyValues_WhenIsAccessedFirstTime() throws Exception {
    mockMvc
        .perform(get("/order-a-badge"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge?action=reset"));
  }

  @Test
  public void show_ShouldDisplayTemplateWithValuesFromSession_WhenThereIsASession()
      throws Exception {
    OrderBadgeIndexFormRequest formRequest =
        OrderBadgeIndexFormRequest.builder()
            .flowId(FLOW_ID)
            .applicantType(APPLICANT_TYPE_ORGANISATION)
            .build();
    mockMvc
        .perform(
            get("/order-a-badge")
                .param("fid", FLOW_ID)
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, formRequest))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/index"))
        .andExpect(model().attribute("formRequest", formRequest));
  }

  @Test
  public void show_ShouldDisplayTemplateWithEmptyValues_WhenActionIsResetAndThereIsASession()
      throws Exception {
    OrderBadgeIndexFormRequest sessionFormRequest =
        OrderBadgeIndexFormRequest.builder().applicantType(APPLICANT_TYPE_ORGANISATION).build();
    mockMvc
        .perform(
            get("/order-a-badge/?action=reset")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, sessionFormRequest))
        .andExpect(status().isFound())
        .andExpect(redirectedUrlPattern("/order-a-badge?fid=*"));
  }

  @Test
  public void submit_ShouldRediretToPersonDetailsTemplate_WhenApplicationTypeIsPerson()
      throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/")
                .param("flowId", FLOW_ID)
                .param("applicantType", APPLICANT_TYPE_PERSON))
        .andExpect(status().isFound())
        .andExpect(redirectedUrlPattern("/order-a-badge/person/details?fid=*"));
  }

  @Test
  public void submit_ShouldRediretToOrganisationDetailsTemplate_WhenApplicationTypeIsOrganisation()
      throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/")
                .param("flowId", FLOW_ID)
                .param("applicantType", APPLICANT_TYPE_ORGANISATION))
        .andExpect(status().isFound())
        .andExpect(redirectedUrlPattern("/order-a-badge/organisation/details?fid=*"));
  }

  @Test
  public void submit_ShouldReturnTemplateWithErrors_WhenApplicationTypeIsMissing()
      throws Exception {
    mockMvc
        .perform(post("/order-a-badge/"))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/index"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "applicantType", "NotBlank"))
        .andExpect(model().errorCount(1));
  }

  @Test
  public void
      submit_RedirectToOrganisationDetailsTemplateAndResetSession_WhenApplicationTypeIsOrganisationAndSessionExistsForOrderABadgeForPerson()
          throws Exception {
    HttpSession session =
        mockMvc
            .perform(
                post("/order-a-badge/")
                    .param("applicantType", APPLICANT_TYPE_ORGANISATION)
                    .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_PERSON))
            .andExpect(status().isFound())
            .andExpect(redirectedUrlPattern("/order-a-badge/organisation/details?fid=*"))
            .andReturn()
            .getRequest()
            .getSession();
    assertThat(session.getAttribute(SESSION_FORM_REQUEST_INDEX))
        .isEqualTo(FORM_REQUEST_ORGANISATION);
    assertThat(session.getAttribute(SESSION_FORM_REQUEST_DETAILS)).isNull();
    assertThat(session.getAttribute(SESSION_FORM_REQUEST_PROCESSING)).isNull();
  }
}
