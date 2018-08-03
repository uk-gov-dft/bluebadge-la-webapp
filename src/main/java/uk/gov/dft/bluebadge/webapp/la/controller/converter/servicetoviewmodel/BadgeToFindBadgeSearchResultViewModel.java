package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class BadgeToFindBadgeSearchResultViewModel
    implements Converter<Badge, FindBadgeSearchResultViewModel> {

  private static final String VIEW_DATE_FORMAT = "dd/MM/yy";

  private ReferenceDataService referenceDataService;

  @Autowired
  public BadgeToFindBadgeSearchResultViewModel(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @Override
  public FindBadgeSearchResultViewModel convert(Badge source) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(VIEW_DATE_FORMAT);
    Assert.notNull(source, "Source cannot be null");
    String statusDisplayText =
        referenceDataService.retrieveStatusDisplayValue(source.getStatusCode());
    String localAuthorityDisplayText =
        referenceDataService.retrieveLocalAuthorityDisplayValue(
            source.getLocalAuthorityShortCode());
    return FindBadgeSearchResultViewModel.builder()
        .badgeNumber(source.getBadgeNumber())
        .name(source.getParty().getPerson().getBadgeHolderName())
        .postCode(source.getParty().getContact().getPostCode())
        .localAuthority(localAuthorityDisplayText)
        .expiryDate(source.getExpiryDate().format(formatter))
        .status(statusDisplayText)
        .build();
  }
}
