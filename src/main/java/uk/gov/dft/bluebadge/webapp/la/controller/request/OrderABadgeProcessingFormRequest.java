package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

@Data
public class OrderABadgeProcessingFormRequest {
  Integer applicationDateDay;
  Integer applicationDateMonth;
  Integer applicationDataYear;
  String applicationChannel;
  String localAuthorityReferenceNumber;
  Integer badgeStartDateDay;
  Integer badgeStartDateMonth;
  Integer badgeStartDateYear;
  Integer badgeExpiryDateDay;
  Integer badgeExpiryDateMonth;
  Integer badgeExpiryDateYear;
  String deliverTo;
  String deliveryOptions;
}
