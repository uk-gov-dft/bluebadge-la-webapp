package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadgeDetailsViewModel {
  private String nino;
  private String fullName;
  private String dob;
  private String address;
  private String contactNumber;
  private String secondaryContactNumber;
  private String gender;
  private String eligibility;
  private String photoUrl;

  private String status;
  private String badgeNumber;
  private String issuedBy;
  private String badgeStartDate;
  private String badgeExpiryDate;
  private String localAuthorityReference;
  private String applicationDate;
  private String applicationChannel;
}
