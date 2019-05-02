package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ModelViewFormats.viewModelFieldDateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.LookupBadgeViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class BadgeToLookupBadgeViewModel implements Converter<Badge, LookupBadgeViewModel> {

  private ReferenceDataService referenceDataService;

  @Autowired
  BadgeToLookupBadgeViewModel(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @Override
  public LookupBadgeViewModel convert(Badge source) {
    Assert.notNull(source, "Source cannot be null");
    String statusDisplayText =
        referenceDataService.retrieveBadgeStatusDisplayValue(source.getStatusCode());

    LookupBadgeViewModel.LookupBadgeViewModelBuilder viewModel = LookupBadgeViewModel.builder();

    return viewModel
        .badgeNumber(source.getBadgeNumber())
        .expiryDate(source.getExpiryDate().format(viewModelFieldDateFormatter))
        .status(statusDisplayText)
        .build();
  }
}
