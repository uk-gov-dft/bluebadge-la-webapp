package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.service.model.referencedata.ReferenceData;

@Slf4j
@Controller
public class OrderBadgePersonDetailsController {
  public static final String URL = "/order-a-badge/details";

  private static final String TEMPLATE = "order-a-badge/details";

  private static final String REDIRECT_ORDER_A_BADGE_PROCESSING =
      "redirect:" + OrderBadgeProcessingController.URL;

  public static final String FORM_REQUEST_ORDER_A_BADGE_DETAILS =
      "formRequest-order-a-badge-details";

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgePersonDetailsController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(
      @RequestParam(name = "action", required = false) String action,
      @ModelAttribute("formRequest") OrderBadgePersonDetailsFormRequest formRequest,
      HttpSession session) {
    if ("reset".equalsIgnoreCase(StringUtils.trimToEmpty(action))) {
      session.removeAttribute("formRequest-order-a-badge-index");
      session.removeAttribute(FORM_REQUEST_ORDER_A_BADGE_DETAILS);
      session.removeAttribute("formRequest-order-a-badge-processing");
    } else {
      Object sessionFormRequest = session.getAttribute(FORM_REQUEST_ORDER_A_BADGE_DETAILS);
      if (sessionFormRequest != null) {
        BeanUtils.copyProperties(
            (OrderBadgePersonDetailsFormRequest) sessionFormRequest, formRequest);
      }
    }
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @Valid @ModelAttribute("formRequest") final OrderBadgePersonDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    model.addAttribute("errorSummary", new ErrorViewModel());
    if (bindingResult.hasErrors()) {
      return TEMPLATE;
    }
    session.setAttribute(FORM_REQUEST_ORDER_A_BADGE_DETAILS, formRequest);
    return REDIRECT_ORDER_A_BADGE_PROCESSING;
  }

  @ModelAttribute("eligibilities")
  public List<ReferenceData> eligibilities() {
    return referenceDataService.retrieveEligilities();
  }
}
