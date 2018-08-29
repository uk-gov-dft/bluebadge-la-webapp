package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ModelViewFormats.viewModelDateFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.BadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.enums.BadgePartyTypeEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class BadgeToBadgeDetailsViewModel implements Converter<Badge, BadgeDetailsViewModel> {

  private ReferenceDataService referenceDataService;

  @Autowired
  public BadgeToBadgeDetailsViewModel(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @Override
  public BadgeDetailsViewModel convert(Badge source) {
    Assert.notNull(source, "Source cannot be null");

    String address = toAddress(source);
    String applicationDate = source.getApplicationDate().format(viewModelDateFormatter);
    String expiryDate = source.getExpiryDate().format(viewModelDateFormatter);
    String startDate = source.getStartDate().format(viewModelDateFormatter);

    String applicationChannelDisplayText =
        referenceDataService.retrieveApplicationChannelDisplayValue(
            source.getApplicationChannelCode());
    String eligibilityDisplayText =
        referenceDataService.retrieveEligibilityDisplayValue(source.getEligibilityCode());
    String localAuthorityDisplayText =
        referenceDataService.retrieveLocalAuthorityDisplayValue(
            source.getLocalAuthorityShortCode());
    String statusDisplayText =
        referenceDataService.retrieveStatusDisplayValue(source.getStatusCode());

    Contact contact = source.getParty().getContact();

    BadgeDetailsViewModel.BadgeDetailsViewModelBuilder badgeView = BadgeDetailsViewModel.builder();
    String partyTypeCode = source.getParty().getTypeCode();

    if (partyTypeCode.equals(BadgePartyTypeEnum.PERSON.getCode())) {
      Person person = source.getParty().getPerson();
      String dob = source.getParty().getPerson().getDob().format(viewModelDateFormatter);
      String genderDisplayText =
          referenceDataService.retrieveGenderDisplayValue(
              source.getParty().getPerson().getGenderCode());
      badgeView
          .dob(dob)
          .fullName(StringUtils.trimToNull(person.getBadgeHolderName()))
          .gender(StringUtils.trimToNull(genderDisplayText))
          .nino(StringUtils.trimToNull(person.getNino()))
          .photoUrl(StringUtils.trimToNull(source.getImageLink()))
          .eligibility(StringUtils.trimToNull(eligibilityDisplayText));
    }

    if (partyTypeCode.equals(BadgePartyTypeEnum.ORGANISATION.getCode())) {
      badgeView.fullName(source.getParty().getOrganisation().getBadgeHolderName());
    }

    return badgeView
        .badgeNumber(StringUtils.trimToNull(source.getBadgeNumber()))
        .address(StringUtils.trimToNull(address))
        .applicationChannel(StringUtils.trimToNull(applicationChannelDisplayText))
        .applicationDate(StringUtils.trimToNull(applicationDate))
        .badgeStartDate(StringUtils.trimToNull(startDate))
        .badgeExpiryDate(expiryDate)
        .contactFullName(StringUtils.trimToNull(contact.getFullName()))
        .contactNumber(StringUtils.trimToNull(contact.getPrimaryPhoneNumber()))
        .emailAddress(StringUtils.trimToNull(contact.getEmailAddress()))
        .secondaryContactNumber(StringUtils.trimToNull(contact.getSecondaryPhoneNumber()))
        .issuedBy(StringUtils.trimToNull(localAuthorityDisplayText))
        .localAuthorityReference(StringUtils.trimToNull(source.getLocalAuthorityRef()))
        .status(StringUtils.trimToNull(statusDisplayText))
        .build();
  }

  private String toAddress(Badge badge) {
    Contact contact = badge.getParty().getContact();
    StringBuilder address = new StringBuilder(contact.getBuildingStreet());
    String line2 = contact.getLine2();
    if (!StringUtils.isEmpty(line2)) {
      address.append(", ").append(line2);
    }
    address.append(", ").append(contact.getTownCity());
    address.append(", ").append(contact.getPostCode());
    return address.toString();
  }
}
