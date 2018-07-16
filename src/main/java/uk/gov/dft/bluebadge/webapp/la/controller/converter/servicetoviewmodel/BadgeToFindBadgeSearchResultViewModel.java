package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;

@Component
public class BadgeToFindBadgeSearchResultViewModel
    implements Converter<Badge, FindBadgeSearchResultViewModel> {

  @Override
  public FindBadgeSearchResultViewModel convert(Badge source) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    Assert.notNull(source, "Source cannot be null");
    return
        FindBadgeSearchResultViewModel.builder()
            .badgeNumber(source.getBadgeNumber())
            .name(source.getParty().getPerson().getBadgeHolderName())
            .postCode(source.getParty().getContact().getPostCode())
            .localAuthority(source.getLocalAuthorityId().toString())
            .expiryDate(source.getExpiryDate().format(formatter))
            .status(source.getStatusCode())
            .build();
  }
}
