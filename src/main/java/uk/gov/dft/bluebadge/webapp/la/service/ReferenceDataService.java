package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.service.model.referencedata.ReferenceData;

@Service
public class ReferenceDataService {
  public List<ReferenceData> retrieveEligilities() {
    return Arrays.asList(
        new ReferenceData("PIP", "PIP", 0),
        new ReferenceData("DLA", "DLA", 0),
        new ReferenceData("AFRFCS", "Armed Forces Compensation scheme", 0),
        new ReferenceData("WPMS", "War Pensioners'' Mobility Supplement\n", 0),
        new ReferenceData("BLIND", "Registered blind", 0),
        new ReferenceData("WALKD", "Walking ability", 0),
        new ReferenceData("ARMS", "Disability in both arms", 0),
        new ReferenceData("CHILDBULK", "Child under 3 with bulky medical equipment", 0),
        new ReferenceData("CHILDVEHIC", "Child under 3 who needs to be near a vehicle", 0),
        new ReferenceData("TERMILL", "Terminal illness", 0),
        new ReferenceData("ORG", "Organisation", 0));
  }
}
