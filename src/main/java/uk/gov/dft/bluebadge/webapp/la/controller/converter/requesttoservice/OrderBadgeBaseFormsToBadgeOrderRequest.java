package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import java.time.LocalDate;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeBaseDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;

@SuppressWarnings("squid:S00119")
public abstract class OrderBadgeBaseFormsToBadgeOrderRequest<
    DetailsFormRequest extends OrderBadgeBaseDetailsFormRequest> {

  private final SecurityUtils securityUtils;

  public OrderBadgeBaseFormsToBadgeOrderRequest(SecurityUtils securityUtils) {
    this.securityUtils = securityUtils;
  }

  public abstract BadgeOrderRequest convert(
      DetailsFormRequest details, OrderBadgeProcessingFormRequest processing);

  protected BadgeOrderRequest buildProcessing(
      BadgeOrderRequest badgeOrderRequest, OrderBadgeProcessingFormRequest processing) {

    LocalDate applicationDate =
        LocalDate.of(
            processing.getApplicationDateYear(),
            processing.getApplicationDateMonth(),
            processing.getApplicationDateDay());
    LocalDate startDate =
        LocalDate.of(
            processing.getBadgeStartDateYear(),
            processing.getBadgeStartDateMonth(),
            processing.getBadgeStartDateDay());
    LocalDate expiryDate =
        LocalDate.of(
            processing.getBadgeExpiryDateYear(),
            processing.getBadgeExpiryDateMonth(),
            processing.getBadgeExpiryDateDay());

    return badgeOrderRequest
        .applicationDate(applicationDate)
        .applicationChannelCode(processing.getApplicationChannel())
        .localAuthorityRef(processing.getLocalAuthorityReferenceNumber())
        .startDate(startDate)
        .expiryDate(expiryDate)
        .deliverToCode(processing.getDeliverTo())
        .deliveryOptionCode(processing.getDeliveryOptions())
        .localAuthorityShortCode(securityUtils.getCurrentLocalAuthorityShortCode())
        .numberOfBadges(Integer.valueOf(processing.getNumberOfBadges()));
  }
}
