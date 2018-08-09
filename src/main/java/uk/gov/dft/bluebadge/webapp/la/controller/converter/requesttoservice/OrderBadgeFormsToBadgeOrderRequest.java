package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;

@Component
public class OrderBadgeFormsToBadgeOrderRequest {

  private static final String PARTY_TYPE_PERSON = "PERSON";

  private final SecurityUtils securityUtils;

  @Autowired
  public OrderBadgeFormsToBadgeOrderRequest(SecurityUtils securityUtils) {
    this.securityUtils = securityUtils;
  }

  public BadgeOrderRequest convert(
      OrderBadgePersonDetailsFormRequest details, OrderBadgeProcessingFormRequest processing) {
    Assert.notNull(details, "details cannot be null");
    Assert.notNull(processing, "processing cannot be null");

    LocalDate dob = LocalDate.of(details.getDobYear(), details.getDobMonth(), details.getDobDay());

    LocalAuthority localAuthority = securityUtils.getCurrentLocalAuthority();

    Person person =
        new Person()
            .badgeHolderName(details.getName())
            .dob(dob)
            .nino(details.getNino())
            .genderCode(details.getGender());

    Contact contact =
        new Contact()
            .buildingStreet(details.getBuildingAndStreet())
            .line2(details.getOptionalAddressField())
            .townCity(details.getTownOrCity())
            .postCode(details.getPostcode())
            .fullName(details.getContactDetailsName())
            .primaryPhoneNumber(details.getContactDetailsContactNumber())
            .secondaryPhoneNumber(details.getContactDetailsSecondaryContactNumber())
            .emailAddress(details.getContactDetailsEmailAddress());

    Party party = new Party().typeCode(PARTY_TYPE_PERSON).person(person).contact(contact);

    LocalDate applicationDate =
        LocalDate.of(
            processing.getApplicationDateYear(),
            processing.getApplicationDateMonth(),
            processing.getApplicationDateDay());
    LocalDate startDate =
        LocalDate.of(
            processing.getBadgeStartDateYear(),
            processing.getBadgeStartDateMonth(),
            processing.getBadgeStartDateDay());
    LocalDate expiryDate =
        LocalDate.of(
            processing.getBadgeExpiryDateYear(),
            processing.getBadgeExpiryDateMonth(),
            processing.getBadgeExpiryDateDay());

    return new BadgeOrderRequest()
        .party(party)
        .eligibilityCode(details.getEligibility())
        .applicationDate(applicationDate)
        .applicationChannelCode(processing.getApplicationChannel())
        .localAuthorityRef(processing.getLocalAuthorityReferenceNumber())
        .startDate(startDate)
        .expiryDate(expiryDate)
        .deliverToCode(processing.getDeliverTo())
        .deliveryOptionCode(processing.getDeliveryOptions())
        .localAuthorityShortCode(localAuthority.getShortCode());
  }
}
