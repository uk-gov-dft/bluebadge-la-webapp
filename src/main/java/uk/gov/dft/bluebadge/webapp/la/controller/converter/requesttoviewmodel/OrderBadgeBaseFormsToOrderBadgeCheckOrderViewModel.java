package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeBaseDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@SuppressWarnings("squid:S00119")
public abstract class OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModel<
    DetailsFormRequest extends OrderBadgeBaseDetailsFormRequest> {

  protected ReferenceDataService referenceDataService;

  public OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModel(
      ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  public abstract OrderBadgeCheckOrderViewModel convert(
    DetailsFormRequest details, OrderBadgeProcessingFormRequest processing);

  protected OrderBadgeCheckOrderViewModel.OrderBadgeCheckOrderViewModelBuilder buildProcessing(
      OrderBadgeCheckOrderViewModel.OrderBadgeCheckOrderViewModelBuilder builder,
      OrderBadgeProcessingFormRequest processing) {
    String badgeExpiryDate =
        DateValidationUtils.buildDateStringIfValidNullIfInvalid(
            processing.getBadgeExpiryDateDay(),
            processing.getBadgeExpiryDateMonth(),
            processing.getBadgeExpiryDateYear());
    String applicationChannelDisplayText =
        referenceDataService.retrieveApplicationChannelDisplayValue(
            processing.getApplicationChannel());
    String deliverToDisplayText =
        referenceDataService.retrieveDeliverToDisplayValue(processing.getDeliverTo());
    String deliveryOptionsDisplayText =
        referenceDataService.retrieveDeliveryOptionDisplayValue(processing.getDeliveryOptions());

    return builder
        .numberOfBadges(processing.getNumberOfBadges())
        .localAuthorityReference(processing.getLocalAuthorityReferenceNumber())
        .badgeStartDate(processing.getBadgeStartDate())
        .badgeExpiryDate(badgeExpiryDate)
        .applicationDate(processing.getApplicationDate())
        .applicationChannel(applicationChannelDisplayText)
        .deliverTo(deliverToDisplayText)
        .deliveryOptions(deliveryOptionsDisplayText);
  }
}
