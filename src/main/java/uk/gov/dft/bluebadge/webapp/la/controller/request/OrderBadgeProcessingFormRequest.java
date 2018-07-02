package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

@Data
public class OrderBadgeProcessingFormRequest {
  private Integer applicationDateDay;
  private Integer applicationDateMonth;
  private Integer applicationDataYear;
  private String applicationChannel;
  private String localAuthorityReferenceNumber;
  private Integer badgeStartDateDay;
  private Integer badgeStartDateMonth;
  private Integer badgeStartDateYear;
  private Integer badgeExpiryDateDay;
  private Integer badgeExpiryDateMonth;
  private Integer badgeExpiryDateYear;
  private String deliverTo;
  private String deliveryOptions;
}
