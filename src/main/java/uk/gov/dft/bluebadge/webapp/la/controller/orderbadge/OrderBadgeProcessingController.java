package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Slf4j
@Controller
public class OrderBadgeProcessingController {
  static final String URL_PERSON_PROCESSING = "/order-a-badge/person/processing";
  static final String URL_ORGANISATION_PROCESSING = "/order-a-badge/organisation/processing";

  static final String SESSION_FORM_REQUEST = "formRequest-order-a-badge-processing";

  private static final String TEMPLATE = "order-a-badge/processing";

  private static final String REDIRECT_ORDER_A_BADGE_CHECK_ORDER =
      "redirect:" + OrderBadgePersonCheckOrderController.URL;
  private static final String FORM_REQUEST = "formRequest";

  private static final String APPLICANT_TYPE_ORGANISATION = "organisation";

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgeProcessingController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(path = {URL_PERSON_PROCESSING, URL_ORGANISATION_PROCESSING})
  public String show(
      @ModelAttribute(FORM_REQUEST) OrderBadgeProcessingFormRequest formRequest,
      HttpSession session,
      Model model) {
    Object sessionFormRequest = session.getAttribute(SESSION_FORM_REQUEST);
    if (sessionFormRequest != null) {
      BeanUtils.copyProperties(sessionFormRequest, formRequest);
    }
    OrderBadgeIndexFormRequest sessionFormRequestOrderBadgeIndex =
        (OrderBadgeIndexFormRequest)
            session.getAttribute(OrderBadgeIndexController.SESSION_FORM_REQUEST);
    String applicantType = sessionFormRequestOrderBadgeIndex.getApplicantType();
    model.addAttribute("applicantType", applicantType);
    return TEMPLATE;
  }

  @PostMapping(path = {URL_PERSON_PROCESSING, URL_ORGANISATION_PROCESSING})
  public String submit(
      @Valid @ModelAttribute(FORM_REQUEST) OrderBadgeProcessingFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel());
    session.setAttribute(SESSION_FORM_REQUEST, formRequest);
    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }
    return REDIRECT_ORDER_A_BADGE_CHECK_ORDER;
  }

  @ModelAttribute("appSourceOptions")
  public List<ReferenceData> appSourceOptions() {
    return referenceDataService.retrieveApplicationChannels();
  }

  @ModelAttribute("deliverToOptions")
  public List<ReferenceData> deliverToOptions() {
    return referenceDataService.retrieveDeliverTos();
  }

  @ModelAttribute("deliveryOptions")
  public List<ReferenceData> deliveryOptions() {
    return referenceDataService.retrieveDeliveryOptions();
  }
}
