package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest.ApplicationToOrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest.ApplicationToOrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest.ApplicationToOrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
public class OrderBadgeApplicationController extends OrderBadgeBaseController {
  public static final String ORDER_A_BADGE_APPLICATION_URL =
      "/order-a-badge/application/{applicationId}";

  private static final String REDIRECT_ORDER_A_BADGE_PERSON_DETAILS =
      "redirect:" + OrderBadgePersonDetailsController.ORDER_A_BADGE_PERSON_DETAILS_URL;

  private final ApplicationService applicationService;
  private final ApplicationToOrderBadgeIndexFormRequest applicationToOrderBadgeIndexFormRequest;
  private final ApplicationToOrderBadgePersonDetailsFormRequest
      applicationToOrderBadgePersonDetailsFormRequest;
  private final ApplicationToOrderBadgeProcessingFormRequest
      applicationToOrderBadgeProcessingFormRequest;

  public OrderBadgeApplicationController(
      ApplicationService applicationService,
      ApplicationToOrderBadgeIndexFormRequest applicationToOrderBadgeIndexFormRequest,
      ApplicationToOrderBadgePersonDetailsFormRequest
          applicationToOrderBadgePersonDetailsFormRequest,
      ApplicationToOrderBadgeProcessingFormRequest applicationToOrderBadgeProcessingFormRequest) {
    this.applicationService = applicationService;
    this.applicationToOrderBadgeIndexFormRequest = applicationToOrderBadgeIndexFormRequest;
    this.applicationToOrderBadgePersonDetailsFormRequest =
        applicationToOrderBadgePersonDetailsFormRequest;
    this.applicationToOrderBadgeProcessingFormRequest =
        applicationToOrderBadgeProcessingFormRequest;
  }

  @GetMapping(ORDER_A_BADGE_APPLICATION_URL)
  public String startOrderBadgeForApplication(
      @PathVariable(name = "applicationId") String applicationId, HttpSession session) {
    Application application = applicationService.retrieve(applicationId);

    String flowId = UUID.randomUUID().toString();

    OrderBadgeIndexFormRequest orderBadgeIndexFormRequest =
        applicationToOrderBadgeIndexFormRequest.convert(application);
    session.setAttribute(APP_TYPE_FORM_SESSION_ATTR, orderBadgeIndexFormRequest);
    orderBadgeIndexFormRequest.setFlowId(flowId);

    OrderBadgePersonDetailsFormRequest detailsForm =
        applicationToOrderBadgePersonDetailsFormRequest.convert(application);
    detailsForm.setFlowId(flowId);
    session.setAttribute(DETAILS_SESSION_ATTR, detailsForm);

    OrderBadgeProcessingFormRequest orderBadgeProcessingFormRequest =
        applicationToOrderBadgeProcessingFormRequest.convert(application);
    orderBadgeProcessingFormRequest.setApplicationChannel("ONLINE");
    orderBadgeProcessingFormRequest.setFlowId(flowId);
    session.setAttribute(PROCESSING_SESSION_ATTR, orderBadgeProcessingFormRequest);

    return REDIRECT_ORDER_A_BADGE_PERSON_DETAILS + "?fid=" + flowId;
  }
}
