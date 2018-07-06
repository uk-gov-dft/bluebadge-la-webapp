package uk.gov.dft.bluebadge.webapp.la.service.model.badge;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadgeOrder {
  private Party party = null;
  private Integer localAuthorityId = null;
  private String localAuthorityRef = null;
  private LocalDate applicationDate = null;
  private String applicationChannelCode = null;
  private LocalDate startDate = null;
  private LocalDate expiryDate = null;
  private String eligibilityCode = null;
  private String imageFile = null;
  private String deliverToCode = null;
  private String deliveryOptionCode = null;
  private Integer numberOfBadges = null;
}
