package uk.gov.dft.bluebadge.webapp.la.controller;

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
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Slf4j
@Controller
public class OrderBadgeProcessingController {
  public static final String URL = "/order-a-badge/processing";

  public static final String FORM_REQUEST_SESSION = "formRequest-order-a-badge-processing";

  private static final String TEMPLATE = "order-a-badge/processing";

  private static final String REDIRECT_ORDER_A_BADGE_CHECK_ORDER =
      "redirect:" + OrderBadgeCheckOrderController.URL;
  public static final String FORM_REQUEST = "formRequest";

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgeProcessingController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(
      @ModelAttribute(FORM_REQUEST) OrderBadgeProcessingFormRequest formRequest,
      HttpSession session) {
    Object sessionFormRequest = session.getAttribute(FORM_REQUEST_SESSION);
    if (sessionFormRequest != null) {
      BeanUtils.copyProperties(sessionFormRequest, formRequest);
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @Valid @ModelAttribute(FORM_REQUEST) OrderBadgeProcessingFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel());
    session.setAttribute(FORM_REQUEST_SESSION, formRequest);
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
