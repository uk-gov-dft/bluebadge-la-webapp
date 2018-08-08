package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class BadgeSummaryToFindBadgeSearchResultViewModel
    implements Converter<BadgeSummary, FindBadgeSearchResultViewModel> {

  private static final String VIEW_DATE_FORMAT = "dd/MM/yy";
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(VIEW_DATE_FORMAT);

  private ReferenceDataService referenceDataService;

  @Autowired
  public BadgeSummaryToFindBadgeSearchResultViewModel(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @Override
  public FindBadgeSearchResultViewModel convert(BadgeSummary source) {
    Assert.notNull(source, "Source cannot be null");

    String localAuthorityDisplayText =
        referenceDataService.retrieveLocalAuthorityDisplayValue(
            source.getLocalAuthorityShortCode());
    String statusDisplayText =
        referenceDataService.retrieveStatusDisplayValue(source.getStatusCode());

    return FindBadgeSearchResultViewModel.builder()
        .badgeNumber(source.getBadgeNumber())
        .name(source.getName())
        .postCode(source.getPostCode())
        .localAuthority(localAuthorityDisplayText)
        .expiryDate(source.getExpiryDate().format(formatter))
        .status(statusDisplayText)
        .build();
  }
}
