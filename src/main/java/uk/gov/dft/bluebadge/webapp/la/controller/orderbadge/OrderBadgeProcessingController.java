package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
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

  private static final String FORM_REQUEST = "formRequest";

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgeProcessingController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(path = {"/order-a-badge/{applicantType:person|organisation}/processing"})
  public String show(
      @ModelAttribute(FORM_REQUEST) OrderBadgeProcessingFormRequest formRequest,
      HttpSession session,
      Model model,
      @PathVariable("applicantType") String applicantType) {
    Object sessionFormRequest = session.getAttribute(SESSION_FORM_REQUEST);
    if (sessionFormRequest != null) {
      BeanUtils.copyProperties(sessionFormRequest, formRequest);
    }
    model.addAttribute("applicantType", applicantType);
    return TEMPLATE;
  }

  @PostMapping(path = {"/order-a-badge/{applicantType:person|organisation}/processing"})
  public String submit(
      @Valid @ModelAttribute(FORM_REQUEST) OrderBadgeProcessingFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session,
      @PathVariable("applicantType") String applicantType) {
    model.addAttribute("errorSummary", new ErrorViewModel());
    session.setAttribute(SESSION_FORM_REQUEST, formRequest);
    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }
    return getRedirectUrlCheckOrder(applicantType);
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

  private String getRedirectUrlCheckOrder(String applicantType) {
    Assert.notNull(applicantType, "applicantType should not null");
    return "redirect:/order-a-badge/" + applicantType + "/check-order";
  }
}
