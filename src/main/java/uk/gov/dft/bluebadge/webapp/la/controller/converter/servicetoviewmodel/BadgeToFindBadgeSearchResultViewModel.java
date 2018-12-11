package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ModelViewFormats.viewModelFieldDateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.enums.BadgePartyTypeEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class BadgeToFindBadgeSearchResultViewModel
    implements Converter<Badge, FindBadgeSearchResultViewModel> {

  private ReferenceDataService referenceDataService;

  @Autowired
  BadgeToFindBadgeSearchResultViewModel(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @Override
  public FindBadgeSearchResultViewModel convert(Badge source) {
    Assert.notNull(source, "Source cannot be null");
    String statusDisplayText =
        referenceDataService.retrieveBadgeStatusDisplayValue(source.getStatusCode());
    String localAuthorityDisplayText =
        referenceDataService.retrieveBadgeLocalAuthorityDisplayValue(
            source.getLocalAuthorityShortCode());

    String partyTypeCode = source.getParty().getTypeCode();

    FindBadgeSearchResultViewModel.FindBadgeSearchResultViewModelBuilder viewModel =
        FindBadgeSearchResultViewModel.builder();

    if (partyTypeCode.equals(BadgePartyTypeEnum.PERSON.getCode())) {
      viewModel.name(source.getParty().getPerson().getBadgeHolderName());
    }

    if (partyTypeCode.equals(BadgePartyTypeEnum.ORGANISATION.getCode())) {
      viewModel.name(source.getParty().getOrganisation().getBadgeHolderName());
    }

    return viewModel
        .badgeNumber(source.getBadgeNumber())
        .postCode(source.getParty().getContact().getPostCode())
        .localAuthority(localAuthorityDisplayText)
        .expiryDate(source.getExpiryDate().format(viewModelFieldDateFormatter))
        .status(statusDisplayText)
        .build();
  }
}
