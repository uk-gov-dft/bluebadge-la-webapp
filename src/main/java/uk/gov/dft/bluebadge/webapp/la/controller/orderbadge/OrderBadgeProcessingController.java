package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliverToCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliveryOptionCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Slf4j
@Controller
@RequestMapping(OrderBadgeProcessingController.ORDER_A_BADGE_PROCESSING_URL)
public class OrderBadgeProcessingController extends OrderBadgeBaseController {
  static final String ORDER_A_BADGE_PROCESSING_URL = "/order-a-badge/processing";

  private static final String TEMPLATE = "order-a-badge/processing";

  private static final String FORM_REQUEST = "formRequest";

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgeProcessingController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping
  public String show(
      @ModelAttribute(FORM_REQUEST) OrderBadgeProcessingFormRequest formRequest,
      HttpSession session,
      Model model,
      @RequestParam(name = "fid") String flowId) {
    log.debug("Processing, begin get/show.");
    super.setupPageModel(session, model, PROCESSING_SESSION_ATTR, formRequest, flowId);
    addApplicantTypeToModel(session, model);
    log.debug("Processing, show template.");
    return TEMPLATE;
  }

  @PostMapping
  public String submit(
      @Valid @ModelAttribute(FORM_REQUEST) OrderBadgeProcessingFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    checkFlow(session, formRequest);
    session.setAttribute(PROCESSING_SESSION_ATTR, formRequest);

    // Must have delivery option if sent to badge holder.
    // Is always standard if sent to council.
    if (DeliverToCodeField.HOME == formRequest.getDeliverTo()
        && null == formRequest.getDeliveryOptions()) {
      bindingResult.rejectValue("deliveryOptions", "NotNull.badge");
    } else if (DeliverToCodeField.COUNCIL == formRequest.getDeliverTo()) {
      formRequest.setDeliveryOptions(DeliveryOptionCodeField.STAND);
    }

    if (bindingResult.hasErrors()) {
      addApplicantTypeToModel(session, model);
      model.addAttribute("errorSummary", new ErrorViewModel());
      return TEMPLATE;
    }
    return "redirect:"
        + OrderBadgeCheckOrderController.ORDER_A_BADGE_CHECK_ORDER_URL
        + "?fid="
        + formRequest.getFlowId();
  }

  @ModelAttribute("localAuthorityName")
  public String localAuthorityDisplayValue() {
    log.debug("Processing, adding la display value.");
    return referenceDataService.retrieveBadgeLocalAuthorityDisplayValue();
  }

  @ModelAttribute("appSourceOptions")
  public List<ReferenceData> appSourceOptions() {
    log.debug("Processing, adding source options.");
    return referenceDataService.retrieveBadgeApplicationChannels();
  }

  @ModelAttribute("deliveryOptions")
  public List<ReferenceData> deliveryOptions() {
    log.debug("Processing, adding delivery options.");
    return referenceDataService.retrieveBadgeDeliveryOptions();
  }
}
