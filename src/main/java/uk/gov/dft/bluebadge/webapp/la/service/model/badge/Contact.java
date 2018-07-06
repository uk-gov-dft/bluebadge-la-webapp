package uk.gov.dft.bluebadge.webapp.la.service.model.badge;

import lombok.Builder;
import lombok.Data;

/** Contact */
@Data
@Builder
public class Contact {
  private String fullName = null;

  private String buildingStreet = null;

  private String line2 = null;

  private String townCity = null;

  private String postCode = null;

  private String primaryPhoneNumber = null;

  private String secondaryPhoneNumber = null;

  private String emailAddress = null;
}
