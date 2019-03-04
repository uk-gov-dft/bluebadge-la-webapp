package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeBaseController.APP_TYPE_FORM_SESSION_ATTR;
import static uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeBaseController.DETAILS_SESSION_ATTR;
import static uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeBaseController.PROCESSING_SESSION_ATTR;
import static uk.gov.dft.bluebadge.webapp.la.controller.orderbadge.OrderBadgeIndexController.ORDER_BADGE_RESET_URL;

import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.controller.exceptions.InvalidSessionException;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;

public class OrderBadgeBaseControllerTest {

  private static final String FLOW_ID = UUID.randomUUID().toString();

  private OrderBadgeBaseController controller;
  @Mock private HttpSession mockSession;

  private OrderBadgeIndexFormRequest indexForm;
  private OrderBadgePersonDetailsFormRequest detailsForm;
  private OrderBadgeProcessingFormRequest processingForm;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    controller = new OrderBadgeBaseController() {};

    indexForm = OrderBadgeIndexFormRequest.builder().flowId(FLOW_ID).build();
    detailsForm = OrderBadgePersonDetailsFormRequest.builder().flowId(FLOW_ID).build();
    processingForm = OrderBadgeProcessingFormRequest.builder().flowId(FLOW_ID).build();
  }

  @Test
  public void finishSession() {
    controller.finishSession(mockSession);

    verify(mockSession).removeAttribute(APP_TYPE_FORM_SESSION_ATTR);
    verify(mockSession).removeAttribute(DETAILS_SESSION_ATTR);
    verify(mockSession).removeAttribute(PROCESSING_SESSION_ATTR);
  }

  @Test
  public void getApplicantType_indexFormPresent() {
    indexForm.setApplicantType("test type");
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);

    String applicantType = controller.getApplicantType(mockSession);
    assertThat(applicantType).isEqualTo("test type");
  }

  @Test
  public void getApplicantType_indexFormPresent_appTypeNotSet() {
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);

    try {
      controller.getApplicantType(mockSession);
      fail("No exception thrown");
    } catch (InvalidSessionException e) {
      assertThat(e.getRedirectUrl()).isNotPresent();
    }
  }

  @Test
  public void getApplicantType_indexFormNotPresent() {
    try {
      controller.getApplicantType(mockSession);
      fail("No exception thrown");
    } catch (InvalidSessionException e) {
      assertThat(e.getRedirectUrl()).isNotPresent();
    }
  }

  @Test
  public void addApplicantTypeToModel() {}

  @Test
  public void setupPageModel() {}

  @Test
  public void redirectFlow() {
    String result = OrderBadgeBaseController.redirectFlow(indexForm, "/redirect/here");
    assertThat(result).isEqualTo("redirect:/redirect/here?fid=" + FLOW_ID);
  }

  @Test
  public void checkFlow_fullSessionAllSameFlow_allOk() {
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);
    when(mockSession.getAttribute(DETAILS_SESSION_ATTR)).thenReturn(detailsForm);
    when(mockSession.getAttribute(PROCESSING_SESSION_ATTR)).thenReturn(processingForm);

    controller.checkFlow(mockSession, FLOW_ID);
  }

  @Test
  public void checkFlow_emptySession_thenRedirectToOrderBadgeStart() {
    try {
      controller.checkFlow(mockSession, UUID.randomUUID().toString());
      fail("No exception thrown");
    } catch (InvalidSessionException e) {
      assertThat(e.getRedirectUrl()).isPresent();
      assertThat(e.getRedirectUrl().get()).isEqualTo(ORDER_BADGE_RESET_URL);
    }
  }

  @Test
  public void checkFlow_onlyIndexSessionForm_andSameFlow_thenOk() {
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);
    controller.checkFlow(mockSession, FLOW_ID);
  }

  @Test
  public void checkFlow_onlyIndexSessionForm_andDifferentFlow_thenException() {
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);

    try {
      controller.checkFlow(mockSession, UUID.randomUUID().toString());
      fail("No exception thrown");
    } catch (InvalidSessionException e) {
      assertThat(e.getRedirectUrl()).isNotPresent();
    }
  }

  @Test
  public void checkFlow_fullSessionAllDifferentFlow() {
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);
    when(mockSession.getAttribute(DETAILS_SESSION_ATTR)).thenReturn(detailsForm);
    when(mockSession.getAttribute(PROCESSING_SESSION_ATTR)).thenReturn(processingForm);

    try {
      controller.checkFlow(mockSession, UUID.randomUUID().toString());
      fail("No exception thrown");
    } catch (InvalidSessionException e) {
      assertThat(e.getRedirectUrl()).isNotPresent();
    }
  }

  @Test
  public void checkFlow_fullSessionIndexFormDifferentFlow() {
    indexForm.setFlowId(UUID.randomUUID().toString());
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);
    when(mockSession.getAttribute(DETAILS_SESSION_ATTR)).thenReturn(detailsForm);
    when(mockSession.getAttribute(PROCESSING_SESSION_ATTR)).thenReturn(processingForm);

    try {
      controller.checkFlow(mockSession, FLOW_ID);
      fail("No exception thrown");
    } catch (InvalidSessionException e) {
      assertThat(e.getRedirectUrl()).isNotPresent();
    }
  }

  @Test
  public void checkFlow_fullSessionDetailsFormDifferentFlow() {
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);
    detailsForm.setFlowId(UUID.randomUUID().toString());
    when(mockSession.getAttribute(DETAILS_SESSION_ATTR)).thenReturn(detailsForm);
    when(mockSession.getAttribute(PROCESSING_SESSION_ATTR)).thenReturn(processingForm);

    try {
      controller.checkFlow(mockSession, FLOW_ID);
      fail("No exception thrown");
    } catch (InvalidSessionException e) {
      assertThat(e.getRedirectUrl()).isNotPresent();
    }
  }

  @Test
  public void checkFlow_fullSessionProcessingFormDifferentFlow() {
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);
    when(mockSession.getAttribute(DETAILS_SESSION_ATTR)).thenReturn(detailsForm);
    processingForm.setFlowId(UUID.randomUUID().toString());
    when(mockSession.getAttribute(PROCESSING_SESSION_ATTR)).thenReturn(processingForm);

    try {
      controller.checkFlow(mockSession, FLOW_ID);
      fail("No exception thrown");
    } catch (InvalidSessionException e) {
      assertThat(e.getRedirectUrl()).isNotPresent();
    }
  }

  @Test
  public void checkFlow_sessionAllSameFlowAsForm() {
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);
    when(mockSession.getAttribute(DETAILS_SESSION_ATTR)).thenReturn(detailsForm);
    when(mockSession.getAttribute(PROCESSING_SESSION_ATTR)).thenReturn(processingForm);

    OrderBadgeIndexFormRequest newIndexForm =
        OrderBadgeIndexFormRequest.builder().flowId(FLOW_ID).build();
    controller.checkFlow(mockSession, newIndexForm);
  }

  @Test
  public void checkFlow_sessionDifferentFlowAsForm() {
    when(mockSession.getAttribute(APP_TYPE_FORM_SESSION_ATTR)).thenReturn(indexForm);
    when(mockSession.getAttribute(DETAILS_SESSION_ATTR)).thenReturn(detailsForm);
    when(mockSession.getAttribute(PROCESSING_SESSION_ATTR)).thenReturn(processingForm);

    OrderBadgeIndexFormRequest newIndexForm =
        OrderBadgeIndexFormRequest.builder().flowId("different").build();
    try {
      controller.checkFlow(mockSession, newIndexForm);
      fail("No exception thrown");
    } catch (InvalidSessionException e) {
      assertThat(e.getRedirectUrl()).isNotPresent();
    }
  }
}
