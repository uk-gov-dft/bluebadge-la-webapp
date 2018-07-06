package uk.gov.dft.bluebadge.webapp.la.service.model.badge;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Party {
  private String typeCode = null;

  private Contact contact = null;

  private Person person = null;

  private Organisation organisation = null;
}
