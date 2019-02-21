package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationSummaryViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ModelViewFormats;
import uk.gov.dft.bluebadge.webapp.la.service.DateTimeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class ApplicationSummaryToApplicationViewModel
    implements Converter<ApplicationSummary, ApplicationSummaryViewModel> {

  private ReferenceDataService referenceDataService;
  private DateTimeService dateTimeService;

  @Autowired
  public ApplicationSummaryToApplicationViewModel(
      ReferenceDataService referenceDataService, DateTimeService dateTimeService) {
    this.referenceDataService = referenceDataService;
    this.dateTimeService = dateTimeService;
  }

  @Override
  public ApplicationSummaryViewModel convert(ApplicationSummary source) {
    Assert.notNull(source, "Source cannot be null");

    String eligibilityViewModel = "Organisation";
    EligibilityCodeField eligility = source.getEligibilityCode();
    if (eligility != null) {
      eligibilityViewModel =
          referenceDataService.retrieveApplicationEligibilityDisplayValue(eligility.toString());
    }

    String submittedDateViewModel =
        source
            .getSubmissionDate()
            .atZoneSameInstant(dateTimeService.clientZoneId())
            .format(ModelViewFormats.viewModelGridDateTimeFormatter);

    return ApplicationSummaryViewModel.builder()
        .applicationId(source.getApplicationId())
        .name(source.getName())
        .nino(source.getNino())
        .eligibility(eligibilityViewModel)
        .submittedDate(submittedDateViewModel)
        .status(source.getApplicationStatus().name())
        .build();
  }
}
