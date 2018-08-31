package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationSummaryViewModel {
  private String applicationId;
  private String name;
  private String nino;
  private String eligibility;
  private String submittedDate;
}
