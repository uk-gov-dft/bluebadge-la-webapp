package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;

@Component
public class LocalAuthorityDetailsFormRequestToLocalAuthority
    implements Converter<LocalAuthorityDetailsFormRequest, LocalAuthority> {

  @Override
  public LocalAuthority convert(LocalAuthorityDetailsFormRequest formRequest) {
    Assert.notNull(formRequest, "formRequest cannot be null");
    return new LocalAuthority()
        .description(formRequest.getDescription())
        .welshDescription(formRequest.getWelshDescription())
        .addressLine1(formRequest.getAddressLine1())
        .addressLine2(formRequest.getAddressLine2())
        .addressLine3(formRequest.getAddressLine3())
        .addressLine4(formRequest.getAddressLine4())
        .badgeCost(formRequest.getBadgeCost())
        .paymentsEnabled(formRequest.getPaymentsEnabled())
        .badgePackType(formRequest.getBadgePackType())
        .contactNumber(formRequest.getContactNumber())
        .contactUrl(formRequest.getWebsiteUrl())
        .country(formRequest.getCountry())
        .county(formRequest.getCounty())
        .emailAddress(formRequest.getEmailAddress())
        .nameLine2(formRequest.getNameLine2())
        .nation(formRequest.getNation())
        .postcode(formRequest.getPostcode())
        .town(formRequest.getTown())
        .differentServiceSignpostUrl(formRequest.getDifferentServiceSignpostUrl());
  }
}