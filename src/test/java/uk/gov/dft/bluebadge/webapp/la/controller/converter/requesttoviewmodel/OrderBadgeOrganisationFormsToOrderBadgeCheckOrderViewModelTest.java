package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModelTest
    extends OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModelTest {

  private OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel converter;

  @Before
  public void setUp() {
    super.setUp();
    converter =
        new OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel(referenceDataServiceMock);
  }

  @Test
  public void convert_ShouldConvert() {
    assertThat(
            converter.convert(
                FORM_REQUEST_ORGANISATION_DETAILS, FORM_REQUEST_ORGANISATION_PROCESSING))
        .isEqualTo(CHECK_ORDER_ORGANISATION_VIEW_MODEL);
  }
}
