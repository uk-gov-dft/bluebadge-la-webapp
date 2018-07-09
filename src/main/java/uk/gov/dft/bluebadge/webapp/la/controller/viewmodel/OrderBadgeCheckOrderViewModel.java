package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderBadgeCheckOrderViewModel {
  private String fullName;
  private String dob;
  private String nino;
  private String address;
  private String contactNumber;
  private String emailAddress;
  private String eligibility;
  private String photo;
  private String localAuthorityReference;
  private String badgeStartDate;
  private String badgeExpiryDate;
  private String applicationDate;
  private String applicationChannel;
  private String deliverTo;
  private String deliveryOptions;
}
