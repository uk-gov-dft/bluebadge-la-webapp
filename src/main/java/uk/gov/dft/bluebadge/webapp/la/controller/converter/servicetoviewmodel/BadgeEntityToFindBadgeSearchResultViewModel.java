package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;

@Component
public class BadgeEntityToFindBadgeSearchResultViewModel
    implements Converter<String, FindBadgeSearchResultViewModel> {

  @Override
  public FindBadgeSearchResultViewModel convert(String source) {
    Assert.notNull(source, "Source cannot be null");
    FindBadgeSearchResultViewModel result =
        FindBadgeSearchResultViewModel.builder()
            .badgeNumber("HAS67SDDS3")
            .name("Joe BLoggs")
            .postCode("M12 8N")
            .localAuthority("Manchester city council")
            .expiryDate("20/12/22")
            .status("Active")
            .build();

    return result;
  }
}
