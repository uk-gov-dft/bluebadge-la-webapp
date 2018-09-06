package uk.gov.dft.bluebadge.webapp.la.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.applications.ApplicationsApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.comparator.ApplicationSummaryComparatorBySubmittedDateDescendingOrder;

@Service
@Slf4j
public class ApplicationService {

    private ApplicationsApiClient applicationsApiClient;

    @Autowired
    public ApplicationService(ApplicationsApiClient applicationsApiClient) {
        this.applicationsApiClient = applicationsApiClient;
    }

    public List<ApplicationSummary> find(
            Optional<String> name,
            Optional<String> postcode,
            Optional<LocalDateTime> from,
            Optional<LocalDateTime> to,
            Optional<ApplicationTypeCodeField> applicationTypeCode) {
        log.debug(
                "find applications with name=[{}], postcode=[{}], from=[{}], to=[{}], applicationTypeCode=[{}]",
                name,
                postcode,
                from,
                to,
                applicationTypeCode);
        Assert.isTrue(
                name.isPresent()
                        || postcode.isPresent()
                        || from.isPresent()
                        || to.isPresent()
                        || applicationTypeCode.isPresent(),
                "Either name or postcode or from or to or applicationTypeCode should be non empty");

        List<ApplicationSummary> applicationSummariesResponse =
                this.applicationsApiClient.find(name, postcode, from, to, applicationTypeCode);
        if (!applicationSummariesResponse.isEmpty()) {
            applicationSummariesResponse.sort(
                    new ApplicationSummaryComparatorBySubmittedDateDescendingOrder());
        }
        return applicationSummariesResponse;
    }

    public List<ApplicationSummary> findApplicationByName(String name) {
        return find(Optional.of(name), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public List<ApplicationSummary> findApplicationByPostCode(String postcode) {
        return find(Optional.empty(), Optional.of(postcode), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public List<ApplicationSummary> retrieve() {
        return find(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(ApplicationTypeCodeField.NEW));
    }
}
