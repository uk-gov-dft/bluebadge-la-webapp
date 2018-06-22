package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.service.model.referencedata.Eligibility;

@Service
public class ReferenceDataService {
  public List<Eligibility> retrieveEligilities() {
    List<Eligibility> eligibilities =
        Arrays.asList(
            new Eligibility("PIP", "PIP"),
            new Eligibility("DLA", "DLA"),
            new Eligibility("AFRFCS", "Armed Forces Compensation scheme"),
            new Eligibility("WPMS", "War Pensioners' Mobility Supplement\n"),
            new Eligibility("BLIND", "Registered blind"),
            new Eligibility("WALKD", "Walking ability"),
            new Eligibility("ARMS", "Disability in both arms"),
            new Eligibility("CHILDBULK", "Child under 3 with bulky medical equipment"),
            new Eligibility("CHILDVEHIC", "Chuld under 3 who needs to be near a vehicle"),
            new Eligibility("TERMILL", "Terminal illness"),
            new Eligibility("ORG", "Organisation"));
    return eligibilities;
  }
}
