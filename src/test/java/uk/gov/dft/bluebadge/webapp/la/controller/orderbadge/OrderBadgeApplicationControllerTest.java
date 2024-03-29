package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.advice.ErrorControllerAdvice;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest.ApplicationToOrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest.ApplicationToOrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest.ApplicationToOrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationToOrderBadgeTestData;

public class OrderBadgeApplicationControllerTest extends OrderBadgeControllerTestData {
  @Mock private ApplicationService applicationServiceMock;
  @Mock private ApplicationToOrderBadgeIndexFormRequest applicationToOrderBadgeIndexFormRequestMock;
  @Mock MessageSource messageSource;

  @Mock
  private ApplicationToOrderBadgePersonDetailsFormRequest
      applicationToOrderBadgePersonDetailsFormRequestMock;

  @Mock
  private ApplicationToOrderBadgeProcessingFormRequest
      applicationToOrderBadgeProcessingFormRequestMock;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    OrderBadgeApplicationController controller =
        new OrderBadgeApplicationController(
            applicationServiceMock,
            applicationToOrderBadgeIndexFormRequestMock,
            applicationToOrderBadgePersonDetailsFormRequestMock,
            applicationToOrderBadgeProcessingFormRequestMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .setControllerAdvice(new ErrorControllerAdvice(new ObjectMapper(), messageSource))
            .build();
  }

  @Test
  @SneakyThrows
  public void show_shouldDisplayBadgeOrderedTemplateWithBadgeNumber() {
    String applicationId = ApplicationToOrderBadgeTestData.getApplication().getApplicationId();
    when(applicationServiceMock.retrieve(applicationId))
        .thenReturn(ApplicationToOrderBadgeTestData.getApplication());

    when(applicationToOrderBadgeIndexFormRequestMock.convert(
            ApplicationToOrderBadgeTestData.getApplication()))
        .thenReturn(ApplicationToOrderBadgeTestData.getApplicationToOrderBadgeIndexFormRequest());

    when(applicationToOrderBadgePersonDetailsFormRequestMock.convert(
            ApplicationToOrderBadgeTestData.getApplication()))
        .thenReturn(
            ApplicationToOrderBadgeTestData.getApplicationToOrderBadgePersonDetailsFormRequest());

    OrderBadgeProcessingFormRequest expectedProcessingForm =
        OrderBadgeProcessingFormRequest.builder().build();

    BeanUtils.copyProperties(
        ApplicationToOrderBadgeTestData.APPLICATION_TO_ORDER_BADGE_PROCESSING_FORM_REQUEST,
        expectedProcessingForm);
    when(applicationToOrderBadgeProcessingFormRequestMock.convert(
            ApplicationToOrderBadgeTestData.getApplication()))
        .thenReturn(expectedProcessingForm);

    HttpSession session =
        mockMvc
            .perform(get("/order-a-badge/application/" + applicationId))
            .andExpect(status().isFound())
            .andExpect(redirectedUrlPattern("/order-a-badge/person/details?fid=*"))
            .andReturn()
            .getRequest()
            .getSession();

    assertThat(session.getAttribute("formRequest-order-a-badge-index"))
        .isEqualToIgnoringGivenFields(
            ApplicationToOrderBadgeTestData.getApplicationToOrderBadgeIndexFormRequest(), "flowId");
    assertThat(session.getAttribute("formRequest-order-a-badge-details"))
        .isEqualToIgnoringGivenFields(
            ApplicationToOrderBadgeTestData.getApplicationToOrderBadgePersonDetailsFormRequest(),
            "flowId");
    assertThat(session.getAttribute("formRequest-order-a-badge-processing"))
        .isEqualToIgnoringGivenFields(
            ApplicationToOrderBadgeTestData
                .APPLICATION_TO_ORDER_BADGE_PROCESSING_WITH_APPLICATION_CHANNEL_FORM_REQUEST,
            "flowId");
  }
}
