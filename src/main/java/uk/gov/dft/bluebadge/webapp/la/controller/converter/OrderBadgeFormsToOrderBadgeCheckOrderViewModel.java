package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.util.StringUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;

@Component
public class OrderBadgeFormsToOrderBadgeCheckOrderViewModel {

  public OrderBadgeCheckOrderViewModel convert(
      OrderBadgePersonDetailsFormRequest details, OrderBadgeProcessingFormRequest processing) {
    Assert.notNull(details, "details cannot be null");
    Assert.notNull(processing, "processing cannot be null");
    StringBuilder address = new StringBuilder(details.getBuildingAndStreet());
    String line2 = details.getOptionalAddressField();
    if (!StringUtils.isEmpty(line2)) {
      address.append(",").append(line2);
    }
    address.append(",").append(details.getTownOrCity());
    address.append(",").append(details.getPostcode());

    String badgeExpiryDate =
        DateValidationUtils.buildDateStringIfValidNullIfInvalid(
            processing.getBadgeExpiryDateDay(),
            processing.getBadgeExpiryDateMonth(),
            processing.getBadgeExpiryDateYear());

    return OrderBadgeCheckOrderViewModel.builder()
        .fullName(details.getName())
        .dob(details.getDob())
        .gender(details.getGender())
        .nino(details.getNino())
        .address(address.toString())
        .contactNumber(details.getContactDetailsContactNumber())
        .emailAddress(details.getContactDetailsEmailAddress())
        .eligibility(details.getEligibility())
        .localAuthorityReference(processing.getLocalAuthorityReferenceNumber())
        .badgeStartDate(processing.getBadgeStartDate())
        .badgeExpiryDate(badgeExpiryDate)
        .applicationDate(processing.getApplicationDate())
        .applicationChannel(processing.getApplicationChannel())
        .deliverTo(processing.getDeliverTo())
        .deliveryOptions(processing.getDeliveryOptions())
        .build();
  }
}
