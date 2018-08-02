package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.util.StringUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.BadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class BadgeToBadgeDetailsViewModel implements Converter<Badge, BadgeDetailsViewModel> {

  private static final String VIEW_DATE_FORMAT = "dd/MM/yy";

  private ReferenceDataService referenceDataService;

  @Autowired
  public BadgeToBadgeDetailsViewModel(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @Override
  public BadgeDetailsViewModel convert(Badge source) {
    Assert.notNull(source, "Source cannot be null");

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(VIEW_DATE_FORMAT);

    String address = toAddress(source);
    String applicationDate = source.getApplicationDate().format(dateFormatter);
    String expiryDate = source.getExpiryDate().format(dateFormatter);
    String startDate = source.getStartDate().format(dateFormatter);
    String dob = source.getParty().getPerson().getDob().format(dateFormatter);
    String localAuthority = source.getLocalAuthorityId().toString();

    String applicationChannelDisplayText =
        referenceDataService.retrieveApplicationChannelDisplayValue(
            source.getApplicationChannelCode());
    String eligibilityDisplayText =
        referenceDataService.retrieveEligibilityDisplayValue(source.getEligibilityCode());
    String statusDisplayText =
        referenceDataService.retrieveStatusDisplayValue(source.getStatusCode());
    String genderDisplayText =
        referenceDataService.retrieveGenderDisplayValue(
            source.getParty().getPerson().getGenderCode());

    return BadgeDetailsViewModel.builder()
        .badgeNumber(source.getBadgeNumber())
        .address(address)
        .applicationChannel(applicationChannelDisplayText)
        .applicationDate(applicationDate)
        .badgeStartDate(startDate)
        .badgeExpiryDate(expiryDate)
        .contactNumber(source.getParty().getContact().getPrimaryPhoneNumber())
        .secondaryContactNumber(source.getParty().getContact().getSecondaryPhoneNumber())
        .dob(dob)
        .eligibility(eligibilityDisplayText)
        .gender(genderDisplayText)
        .fullName(source.getParty().getPerson().getBadgeHolderName())
        .issuedBy(localAuthority)
        .localAuthorityReference(source.getLocalAuthorityRef())
        .nino(source.getParty().getPerson().getNino())
        .photoUrl(source.getImageLink())
        .status(statusDisplayText)
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
