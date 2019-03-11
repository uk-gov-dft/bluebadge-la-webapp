package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;

@Component
public class LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest
    implements Converter<
        LocalAuthorityRefData.LocalAuthorityMetaData, LocalAuthorityDetailsFormRequest> {

  @Override
  public LocalAuthorityDetailsFormRequest convert(
      LocalAuthorityRefData.LocalAuthorityMetaData metadata) {
    Assert.notNull(metadata, "localAuthority metadata cannot be null");

    return LocalAuthorityDetailsFormRequest.builder()
        .welshDescription(metadata.getWelshDescription())
        .addressLine1(metadata.getAddressLine1())
        .addressLine2(metadata.getAddressLine2())
        .addressLine3(metadata.getAddressLine3())
        .addressLine4(metadata.getAddressLine4())
        .paymentsEnabled(metadata.getPaymentsEnabled())
        .badgeCost(metadata.getBadgeCost() != null ? metadata.getBadgeCost().toString() : null)
        .badgePackType(metadata.getClockType())
        .contactNumber(metadata.getContactNumber())
        .country(metadata.getCountry())
        .county(metadata.getCounty())
        .differentServiceSignpostUrl(metadata.getDifferentServiceSignpostUrl())
        .emailAddress(metadata.getEmailAddress())
        .nameLine2(metadata.getNameLine2())
        .nation((metadata.getNation()))
        .postcode(metadata.getPostcode())
        .town(metadata.getTown())
        .websiteUrl(metadata.getContactUrl())
        .build();
  }
}
