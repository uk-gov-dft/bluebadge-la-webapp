package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;

@Component
public class BadgeSummaryToFindBadgeSearchResultViewModel
    implements Converter<BadgeSummary, FindBadgeSearchResultViewModel> {

  private static final String VIEW_DATE_FORMAT = "dd/MM/yy";

  @Override
  public FindBadgeSearchResultViewModel convert(BadgeSummary source) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(VIEW_DATE_FORMAT);
    Assert.notNull(source, "Source cannot be null");
    return FindBadgeSearchResultViewModel.builder()
        .badgeNumber(source.getBadgeNumber())
        .name(source.getName())
        .postCode(source.getPostCode())
        .localAuthority(source.getLocalAuthorityShortCode())
        .expiryDate(source.getExpiryDate().format(formatter))
        .status(source.getStatusDescription())
        .build();
  }
}
