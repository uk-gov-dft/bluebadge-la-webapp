package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgeOrganisationFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgePersonFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeOrganisationDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Slf4j
@Controller
@RequestMapping(OrderBadgeCheckOrderController.ORDER_A_BADGE_CHECK_ORDER_URL)
public class OrderBadgeCheckOrderController extends OrderBadgeBaseController {
  static final String ORDER_A_BADGE_CHECK_ORDER_URL = "/order-a-badge/check-order";

  private static final String TEMPLATE = "order-a-badge/check-order";

  protected static final String REDIRECT_BADGE_ORDERED =
      "redirect:" + OrderBadgeBadgeOrderedController.URL;

  protected final BadgeService badgeService;
  protected final ReferenceDataService referenceDataService;
  private final OrderBadgeOrganisationFormsToBadgeOrderRequest orgConverter;
  private final OrderBadgePersonFormsToBadgeOrderRequest personConverter;

  OrderBadgeCheckOrderController(
      BadgeService badgeService,
      ReferenceDataService referenceDataService,
      OrderBadgeOrganisationFormsToBadgeOrderRequest orgConverter,
      OrderBadgePersonFormsToBadgeOrderRequest personConverter) {
    this.badgeService = badgeService;
    this.referenceDataService = referenceDataService;
    this.orgConverter = orgConverter;
    this.personConverter = personConverter;
  }

  @GetMapping
  public String show(Model model, HttpSession session, @RequestParam(name = "fid") String flowId) {
    checkFlow(session, flowId);
    model.addAttribute("applicantType", getApplicantType(session));
    model.addAttribute("details", session.getAttribute(DETAILS_SESSION_ATTR));
    model.addAttribute("processing", session.getAttribute(PROCESSING_SESSION_ATTR));
    model.addAttribute("flowId", flowId);
    return TEMPLATE;
  }

  @PostMapping
  public String submit(
      HttpSession session,
      RedirectAttributes redirectAttributes,
      @RequestParam("flowId") String flowId) {
    checkFlow(session, flowId);
    String applicantType = getApplicantType(session);

    Object detailsForm = session.getAttribute(DETAILS_SESSION_ATTR);
    OrderBadgeProcessingFormRequest processingForm =
        (OrderBadgeProcessingFormRequest) session.getAttribute(PROCESSING_SESSION_ATTR);

    List<String> badgeNumbers;
    if ("person".equals(applicantType)) {
      badgeNumbers = personSubmit((OrderBadgePersonDetailsFormRequest) detailsForm, processingForm);
    } else {
      badgeNumbers =
          orgSubmit((OrderBadgeOrganisationDetailsFormRequest) detailsForm, processingForm);
    }

    redirectAttributes.addFlashAttribute("badgeNumbers", badgeNumbers);

    super.finishSession(session);
    return REDIRECT_BADGE_ORDERED;
  }

  public List<String> personSubmit(
      OrderBadgePersonDetailsFormRequest detailsForm,
      OrderBadgeProcessingFormRequest processingForm) {
    BadgeOrderRequest badgeOrderRequest = personConverter.convert(detailsForm, processingForm);

    List<String> badgeNumbers;

    if (detailsForm.getByteImage() == null) {
      badgeNumbers = badgeService.orderABadge(badgeOrderRequest);
    } else {
      badgeNumbers =
          badgeService.orderABadgeForAPerson(badgeOrderRequest, detailsForm.getByteImage());
    }
    return badgeNumbers;
  }

  public List<String> orgSubmit(
      OrderBadgeOrganisationDetailsFormRequest detailsForm,
      OrderBadgeProcessingFormRequest processingForm) {
    BadgeOrderRequest badgeOrderRequest = orgConverter.convert(detailsForm, processingForm);

    return badgeService.orderABadge(badgeOrderRequest);
  }

  @ModelAttribute("eligibilityMap")
  public Map<String, String> eligibilityLookupMap() {
    return referenceDataService.retrieveBadgeEligibilityMap();
  }

  @ModelAttribute("genderMap")
  public Map<String, String> genderLookupMap() {
    return referenceDataService.retrieveBadgeGenderMap();
  }

  @ModelAttribute("applicationChannelMap")
  public Map<String, String> applicationChannelLookupMap() {
    return referenceDataService.retrieveBadgeApplicationChannelMap();
  }

  @ModelAttribute("deliverToMap")
  public Map<String, String> deliverToLookupMap() {
    return referenceDataService.retrieveBadgeDeliverToMap();
  }

  @ModelAttribute("deliveryOptionMap")
  public Map<String, String> deliveryOptionLookupMap() {
    return referenceDataService.retrieveBadgeDeliveryOptionMap();
  }
}
