package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.util.StringUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeOrganisationDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel
    extends OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModel<
        OrderBadgeOrganisationDetailsFormRequest> {

  @Autowired
  public OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel(
      ReferenceDataService referenceDataService) {
    super(referenceDataService);
  }

  @Override
  public OrderBadgeCheckOrderViewModel convert(
      OrderBadgeOrganisationDetailsFormRequest details,
      OrderBadgeProcessingFormRequest processing) {
    Assert.notNull(details, "details cannot be null");
    Assert.notNull(processing, "processing cannot be null");

    OrderBadgeCheckOrderViewModel.OrderBadgeCheckOrderViewModelBuilder builder =
        OrderBadgeCheckOrderViewModel.builder();
    buildDetails(builder, details);
    super.buildProcessing(builder, processing);
    return builder.build();
  }

  private OrderBadgeCheckOrderViewModel.OrderBadgeCheckOrderViewModelBuilder buildDetails(
      OrderBadgeCheckOrderViewModel.OrderBadgeCheckOrderViewModelBuilder builder,
      OrderBadgeOrganisationDetailsFormRequest details) {
    return builder
        .fullName(details.getName())
        .address(buildAddress(details))
        .contactFullName(details.getContactDetailsName())
        .contactNumber(details.getContactDetailsContactNumber())
        .secondaryContactNumber(details.getContactDetailsSecondaryContactNumber())
        .emailAddress(details.getContactDetailsEmailAddress());
  }

  private String buildAddress(OrderBadgeOrganisationDetailsFormRequest details) {
    StringBuilder address = new StringBuilder(details.getBuildingAndStreet());
    String line2 = details.getOptionalAddressField();
    if (!StringUtils.isEmpty(line2)) {
      address.append(", ").append(line2);
    }
    address.append(", ").append(details.getTownOrCity());
    address.append(", ").append(details.getPostcode());
    return address.toString();
  }
}
