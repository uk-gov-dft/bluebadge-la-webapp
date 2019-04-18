package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationSummaryViewModel implements Serializable {
  private String applicationId;
  private String name;
  private String dob;
  private String eligibility;
  private String submittedDate;
  private String applicationType;
  private String status;
}
