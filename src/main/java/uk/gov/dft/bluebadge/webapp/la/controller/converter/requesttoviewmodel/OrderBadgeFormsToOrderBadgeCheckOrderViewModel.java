package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.util.StringUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class OrderBadgeFormsToOrderBadgeCheckOrderViewModel {

  private ReferenceDataService referenceDataService;

  @Autowired
  public OrderBadgeFormsToOrderBadgeCheckOrderViewModel(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  public OrderBadgeCheckOrderViewModel convert(
      OrderBadgePersonDetailsFormRequest details, OrderBadgeProcessingFormRequest processing) {
    Assert.notNull(details, "details cannot be null");
    Assert.notNull(processing, "processing cannot be null");
    StringBuilder address = new StringBuilder(details.getBuildingAndStreet());
    String line2 = details.getOptionalAddressField();
    if (!StringUtils.isEmpty(line2)) {
      address.append(", ").append(line2);
    }
    address.append(", ").append(details.getTownOrCity());
    address.append(", ").append(details.getPostcode());

    String badgeExpiryDate =
        DateValidationUtils.buildDateStringIfValidNullIfInvalid(
            processing.getBadgeExpiryDateDay(),
            processing.getBadgeExpiryDateMonth(),
            processing.getBadgeExpiryDateYear());

    String eligibilityDisplayText =
        referenceDataService.retrieveEligibilityDisplayValue(details.getEligibility());
    String genderDisplayText = referenceDataService.retrieveGenderDisplayValue(details.getGender());
    String applicationChannelDisplayText =
        referenceDataService.retrieveApplicationChannelDisplayValue(
            processing.getApplicationChannel());
    String deliverToDisplayText =
        referenceDataService.retrieveDeliverToDisplayValue(processing.getDeliverTo());
    String deliveryOptionsDisplayText =
        referenceDataService.retrieveDeliveryOptionDisplayValue(processing.getDeliveryOptions());

    return OrderBadgeCheckOrderViewModel.builder()
        .fullName(details.getName())
        .dob(details.getDob())
        .gender(genderDisplayText)
        .nino(details.getNino())
        .address(address.toString())
        .contactFullName(details.getContactDetailsName())
        .contactNumber(details.getContactDetailsContactNumber())
        .secondaryContactNumber(details.getContactDetailsSecondaryContactNumber())
        .emailAddress(details.getContactDetailsEmailAddress())
        .eligibility(eligibilityDisplayText)
        .localAuthorityReference(processing.getLocalAuthorityReferenceNumber())
        .badgeStartDate(processing.getBadgeStartDate())
        .badgeExpiryDate(badgeExpiryDate)
        .applicationDate(processing.getApplicationDate())
        .applicationChannel(applicationChannelDisplayText)
        .deliverTo(deliverToDisplayText)
        .deliveryOptions(deliveryOptionsDisplayText)
        .build();
  }
}
