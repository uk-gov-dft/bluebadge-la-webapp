package uk.gov.dft.bluebadge.webapp.la.service.model.badge;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
  private String badgeHolderName = null;

  private String nino = null;

  private LocalDate dob = null;

  private String genderCode = null;
}
