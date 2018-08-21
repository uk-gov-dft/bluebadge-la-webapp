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
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeOrganisationDetailsFormRequest;

@Slf4j
@Controller
public class OrderBadgeOrganisationDetailsController
    extends OrderBadgeBaseDetailsController<OrderBadgeOrganisationDetailsFormRequest> {
  public static final String URL = "/order-a-badge/organisation/details";

  private static final String TEMPLATE = "order-a-badge/organisation/details";

  private static final String REDIRECT_ORDER_BADGE_PROCESSING =
      "redirect:" + OrderBadgeProcessingController.URL_ORGANISATION_PROCESSING;

  @GetMapping(URL)
  public String showOrganisationDetails(
      @ModelAttribute("formRequest") OrderBadgeOrganisationDetailsFormRequest formRequest,
      HttpSession session,
      Model model) {
    return super.show(formRequest, session, model);
  }

  @PostMapping(URL)
  public String submitOrganisationDetails(
      @Valid @ModelAttribute("formRequest")
          final OrderBadgeOrganisationDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model,
      HttpSession session) {
    return super.submit(formRequest, bindingResult, model, session);
  }

  @Override
  protected String getTemplate() {
    return TEMPLATE;
  }

  @Override
  protected String getProcessingRedirectUrl() {
    return REDIRECT_ORDER_BADGE_PROCESSING;
  }
}
