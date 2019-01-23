package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppContact;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppPerson;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;

@Component
public class ApplicationToOrderBadgePersonDetailsFormRequest implements Converter<Application, OrderBadgePersonDetailsFormRequest> {

  @Override
  public OrderBadgePersonDetailsFormRequest convert(Application source) {
    Assert.notNull(source, "Source cannot be null");

    AppPerson appPerson = source.getParty().getPerson();
    AppContact appContact = source.getParty().getContact();

    String[] dobArray = appPerson.getDob().toString().split("-");
    Integer dobYear = Integer.parseInt(dobArray[0]);
    Integer dobMonth = Integer.parseInt(dobArray[1]);
    Integer dobDay =  Integer.parseInt(dobArray[2]);

    return OrderBadgePersonDetailsFormRequest.builder()
          // Personal data
            .name(appPerson.getBadgeHolderName())
            .nino(appPerson.getNino())
            .gender(appPerson.getGenderCode().toString())
            .eligibility(source.getEligibility().getTypeCode().toString())
            .dobDay(dobDay)
            .dobMonth(dobMonth)
            .dobYear(dobYear)
            // Address
            .buildingAndStreet(appContact.getBuildingStreet())
            .optionalAddressField(appContact.getLine2())
            .townOrCity(appContact.getTownCity())
            .postcode(appContact.getPostCode())
            // Contact details
            .contactDetailsName(appContact.getFullName())
            .contactDetailsContactNumber(appContact.getPrimaryPhoneNumber())
            .contactDetailsSecondaryContactNumber(appContact.getSecondaryPhoneNumber())
            .contactDetailsEmailAddress(appContact.getEmailAddress())
          .build();
  }
}
