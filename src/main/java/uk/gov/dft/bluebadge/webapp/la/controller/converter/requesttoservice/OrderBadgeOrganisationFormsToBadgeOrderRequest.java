package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Organisation;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeOrganisationDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;

@Component
public class OrderBadgeOrganisationFormsToBadgeOrderRequest
    extends OrderBadgeBaseFormsToBadgeOrderRequest<OrderBadgeOrganisationDetailsFormRequest> {

  private static final String PARTY_TYPE_ORGANISATION = "ORG";

  @Autowired
  public OrderBadgeOrganisationFormsToBadgeOrderRequest(SecurityUtils securityUtils) {
    super(securityUtils);
  }

  @Override
  public BadgeOrderRequest convert(
      OrderBadgeOrganisationDetailsFormRequest details,
      OrderBadgeProcessingFormRequest processing) {
    Assert.notNull(details, "details cannot be null");
    Assert.notNull(processing, "processing cannot be null");

    Organisation organisation = new Organisation().badgeHolderName(details.getName());
    Contact contact = getContact(details);
    Party party =
        new Party().typeCode(PARTY_TYPE_ORGANISATION).organisation(organisation).contact(contact);

    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest();
    buildProcessing(badgeOrderRequest, processing);
    return badgeOrderRequest.party(party);
  }

  private Contact getContact(OrderBadgeOrganisationDetailsFormRequest details) {
    return new Contact()
        .buildingStreet(details.getBuildingAndStreet())
        .line2(details.getOptionalAddressField())
        .townCity(details.getTownOrCity())
        .postCode(details.getPostcode())
        .fullName(details.getContactDetailsName())
        .primaryPhoneNumber(details.getContactDetailsContactNumber())
        .secondaryPhoneNumber(details.getContactDetailsSecondaryContactNumber())
        .emailAddress(details.getContactDetailsEmailAddress());
  }
}
