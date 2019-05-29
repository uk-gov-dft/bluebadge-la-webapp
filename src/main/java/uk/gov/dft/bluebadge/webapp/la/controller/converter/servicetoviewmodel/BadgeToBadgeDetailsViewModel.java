package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ModelViewFormats.viewModelFieldDateFormatter;

import java.time.LocalDate;
import java.time.Period;
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
  private PartyToAddressViewModel partyToAddressViewModel;

  @Autowired
  BadgeToBadgeDetailsViewModel(
      ReferenceDataService referenceDataService, PartyToAddressViewModel partyToAddressViewModel) {
    this.referenceDataService = referenceDataService;
    this.partyToAddressViewModel = partyToAddressViewModel;
  }

  @Override
  public BadgeDetailsViewModel convert(Badge source) {
    Assert.notNull(source, "Source cannot be null");

    String address = partyToAddressViewModel.convert(source.getParty().getContact());
    String applicationDate = source.getApplicationDate().format(viewModelFieldDateFormatter);
    String expiryDate = source.getExpiryDate().format(viewModelFieldDateFormatter);
    String startDate = source.getStartDate().format(viewModelFieldDateFormatter);
    String orderDate =
        source.getOrderDate() != null
            ? source.getOrderDate().format(viewModelFieldDateFormatter)
            : null;
    String issuedDate =
        source.getIssuedDate() != null
            ? source.getIssuedDate().format(viewModelFieldDateFormatter)
            : null;

    String applicationChannelDisplayText =
        referenceDataService.retrieveBadgeApplicationChannelDisplayValue(
            source.getApplicationChannelCode());
    String eligibilityDisplayText =
        referenceDataService.retrieveBadgeEligibilityDisplayValue(source.getEligibilityCode());
    String localAuthorityDisplayText =
        referenceDataService.retrieveBadgeLocalAuthorityDisplayValue(
            source.getLocalAuthorityShortCode());
    String statusDisplayText = referenceDataService.retrieveBadgeStatusDisplayValue(source);

    Contact contact = source.getParty().getContact();

    BadgeDetailsViewModel.BadgeDetailsViewModelBuilder badgeView = BadgeDetailsViewModel.builder();
    String partyTypeCode = source.getParty().getTypeCode();

    if (BadgePartyTypeEnum.PERSON.getCode().equals(partyTypeCode)) {
      Person person = source.getParty().getPerson();
      String dobStr = "";
      String age = "";
      if (null != person.getDob()) {
        LocalDate dob = person.getDob();
        dobStr = dob.format(viewModelFieldDateFormatter);
        age = Integer.toString(Period.between(dob, LocalDate.now()).getYears());
      }
      String genderDisplayText =
          referenceDataService.retrieveBadgeGenderDisplayValue(person.getGenderCode());
      badgeView
          .dob(dobStr)
          .age(age)
          .fullName(StringUtils.trimToNull(person.getBadgeHolderName()))
          .gender(StringUtils.trimToNull(genderDisplayText))
          .nino(StringUtils.trimToNull(person.getNino()))
          .photoUrl(StringUtils.trimToNull(source.getImageLink()))
          .eligibility(StringUtils.trimToNull(eligibilityDisplayText));
    }

    if (BadgePartyTypeEnum.ORGANISATION.getCode().equals(partyTypeCode)) {
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
        .localAuthorityShortCode(source.getLocalAuthorityShortCode())
        .localAuthorityReference(StringUtils.trimToNull(source.getLocalAuthorityRef()))
        .status(StringUtils.trimToNull(statusDisplayText))
        .orderDate(StringUtils.trimToNull(orderDate))
        .issuedDate(StringUtils.trimToNull(issuedDate))
        .rejectedReason(StringUtils.trimToNull(source.getRejectedReason()))
        .partyTypeCode(source.getParty().getTypeCode())
        .build();
  }
}
