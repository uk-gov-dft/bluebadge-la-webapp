package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;

@Component
public class OrderBadgePersonFormsToBadgeOrderRequest
    extends OrderBadgeBaseFormsToBadgeOrderRequest<OrderBadgePersonDetailsFormRequest> {

  private static final String PARTY_TYPE_PERSON = "PERSON";

  @Autowired
  public OrderBadgePersonFormsToBadgeOrderRequest(SecurityUtils securityUtils) {
    super(securityUtils);
  }

  @Override
  public BadgeOrderRequest convert(
      OrderBadgePersonDetailsFormRequest details, OrderBadgeProcessingFormRequest processing) {
    Assert.notNull(details, "details cannot be null");
    Assert.notNull(processing, "processing cannot be null");

    LocalDate dob = LocalDate.of(details.getDobYear(), details.getDobMonth(), details.getDobDay());

    Person person =
        new Person()
            .badgeHolderName(details.getName())
            .dob(dob)
            .nino(details.getNino())
            .genderCode(details.getGender());
    Contact contact = getContact(details);
    Party party = new Party().typeCode(PARTY_TYPE_PERSON).person(person).contact(contact);

    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest();
    buildProcessing(badgeOrderRequest, processing);
    return badgeOrderRequest.party(party).eligibilityCode(details.getEligibility());
  }

  private Contact getContact(OrderBadgePersonDetailsFormRequest details) {
    return new Contact()
        .buildingStreet(details.getBuildingAndStreet())
        .line2(details.getOptionalAddressField())
        .townCity(details.getTownOrCity())
        .postCode(StringUtils.trimAllWhitespace(details.getPostcode()))
        .fullName(details.getContactDetailsName())
        .primaryPhoneNumber(StringUtils.trimAllWhitespace(details.getContactDetailsContactNumber()))
        .secondaryPhoneNumber(
            StringUtils.trimAllWhitespace(details.getContactDetailsSecondaryContactNumber()))
        .emailAddress(details.getContactDetailsEmailAddress());
  }
}
