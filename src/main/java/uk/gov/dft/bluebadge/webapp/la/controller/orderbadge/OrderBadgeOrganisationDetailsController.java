package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeOrganisationDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;

@Slf4j
@Controller
@RequestMapping(OrderBadgeOrganisationDetailsController.ORDER_A_BADGE_ORGANISATION_DETAILS_URL)
public class OrderBadgeOrganisationDetailsController extends OrderBadgeBaseController {
  static final String ORDER_A_BADGE_ORGANISATION_DETAILS_URL =
      "/order-a-badge/organisation/details";
  private static final String TEMPLATE = "order-a-badge/organisation/details";

  private static final String REDIRECT_ORDER_BADGE_PROCESSING =
      "redirect:" + OrderBadgeProcessingController.ORDER_A_BADGE_PROCESSING_URL;

  @GetMapping
  public String showOrganisationDetails(
      @ModelAttribute("formRequest") OrderBadgeOrganisationDetailsFormRequest formRequest,
      HttpSession session,
      @RequestParam(name = "fid") String flowId) {
    super.setupPageModel(session, DETAILS_SESSION_ATTR, formRequest, flowId);
    return TEMPLATE;
  }

  @PostMapping
  public String submitOrganisationDetails(
      @Valid @ModelAttribute("formRequest")
          final OrderBadgeOrganisationDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    checkFlow(session, formRequest);
    session.setAttribute(DETAILS_SESSION_ATTR, formRequest);
    if (bindingResult.hasErrors()) {
      model.addAttribute("errorSummary", new ErrorViewModel());
      return TEMPLATE;
    }
    return REDIRECT_ORDER_BADGE_PROCESSING + "?fid=" + formRequest.getFlowId();
  }
}
