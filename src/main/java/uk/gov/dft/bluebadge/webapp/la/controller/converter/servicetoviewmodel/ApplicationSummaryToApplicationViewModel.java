package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class ApplicationSummaryToApplicationViewModel
    implements Converter<ApplicationSummary, ApplicationViewModel> {

  private static final String VIEW_DATE_FORMAT = "dd/MM/yy hh:mm";

  private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(VIEW_DATE_FORMAT);

  private ReferenceDataService referenceDataService;

  @Autowired
  public ApplicationSummaryToApplicationViewModel(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @Override
  public ApplicationViewModel convert(ApplicationSummary source) {
    Assert.notNull(source, "Source cannot be null");

    String eligibilityViewModel =
        referenceDataService.retrieveEligibilityDisplayValue(
            source.getEligibilityCode().toString());
    String submittedDateViewModel = source.getSubmissionDate().format(dateFormatter);

    return ApplicationViewModel.builder()
        .name(source.getName())
        .nino(source.getNino())
        .eligibility(eligibilityViewModel)
        .submittedDate(submittedDateViewModel)
        .build();
  }
}
