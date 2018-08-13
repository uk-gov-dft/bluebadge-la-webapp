package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgeBaseFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeBaseDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@SuppressWarnings("squid:S00119")
abstract class OrderBadgeBaseCheckOrderController<
        DetailsFormRequest extends OrderBadgeBaseDetailsFormRequest,
        ConverterToServiceModel extends OrderBadgeBaseFormsToBadgeOrderRequest,
        ConverterToViewModel extends OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModel>
    extends OrderBadgeBaseController {

  private static final String TEMPLATE = "order-a-badge/check-order";

  private static final String REDIRECT_BADGE_ORDERED =
      "redirect:" + OrderBadgeBadgeOrderedController.URL;

  private BadgeService badgeService;
  private ConverterToServiceModel converterToServiceModel;
  private ConverterToViewModel converterToViewModel;

  OrderBadgeBaseCheckOrderController(
      BadgeService badgeService,
      ConverterToServiceModel converterToServiceModel,
      ConverterToViewModel converterToViewModel) {
    this.badgeService = badgeService;
    this.converterToServiceModel = converterToServiceModel;
    this.converterToViewModel = converterToViewModel;
  }

  public String show(Model model, HttpSession session, String applicantType) {
    DetailsFormRequest detailsForm =
        (DetailsFormRequest)
            session.getAttribute(OrderBadgeBaseDetailsController.SESSION_FORM_REQUEST);
    OrderBadgeProcessingFormRequest processingForm =
        (OrderBadgeProcessingFormRequest)
            session.getAttribute(OrderBadgeProcessingController.SESSION_FORM_REQUEST);
    OrderBadgeCheckOrderViewModel data = converterToViewModel.convert(detailsForm, processingForm);

    model.addAttribute("applicantType", applicantType);
    model.addAttribute("data", data);
    return TEMPLATE;
  }

  public String submit(
      HttpSession session, RedirectAttributes redirectAttributes) {
    DetailsFormRequest detailsForm =
        (DetailsFormRequest)
            session.getAttribute(OrderBadgeBaseDetailsController.SESSION_FORM_REQUEST);
    OrderBadgeProcessingFormRequest processingForm =
        (OrderBadgeProcessingFormRequest)
            session.getAttribute(OrderBadgeProcessingController.SESSION_FORM_REQUEST);
    BadgeOrderRequest badgeOrderRequest =
        converterToServiceModel.convert(detailsForm, processingForm);

    List<String> badgeNumbers = badgeService.orderABadge(badgeOrderRequest);
    redirectAttributes.addFlashAttribute("badgeNumbers", badgeNumbers);

    super.finishSession(session);
    return REDIRECT_BADGE_ORDERED;
  }
}
