package uk.gov.dft.bluebadge.webapp.la.comparator;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Comparator;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;

/** To be used to sort <code>ApplicationSummary</code> by submitted date in descending order. */
public class ApplicationSummaryComparatorBySubmittedDateDescendingOrder
    implements Comparator<ApplicationSummary>, Serializable {
  public int compare(ApplicationSummary a1, ApplicationSummary a2) {
    OffsetDateTime date1 = a1.getSubmissionDate();
    OffsetDateTime date2 = a2.getSubmissionDate();
    return date2.compareTo(date1);
  }
}
