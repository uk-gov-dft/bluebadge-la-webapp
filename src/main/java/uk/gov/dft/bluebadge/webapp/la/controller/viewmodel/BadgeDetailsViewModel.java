package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.common.security.model.LocalAuthorityControlled;

@Data
@Builder
public class BadgeDetailsViewModel implements LocalAuthorityControlled, Serializable {
  private String partyTypeCode;
  private String nino;
  private String fullName;
  private String dob;
  private String age;
  private String address;
  private String contactFullName;
  private String contactNumber;
  private String secondaryContactNumber;
  private String gender;
  private String eligibility;
  private String photoUrl;
  private String emailAddress;

  private String status;
  private String badgeNumber;
  private String issuedBy;
  private String localAuthorityShortCode;
  private String badgeStartDate;
  private String badgeExpiryDate;
  private String localAuthorityReference;
  private String applicationDate;
  private String applicationChannel;
  private String orderDate;
  private String issuedDate;
  private String rejectedReason;

  public boolean isRejected() {
    return "Rejected".equals(status);
  }
}
