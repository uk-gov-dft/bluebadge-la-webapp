package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationToOrderBadgeTestData;

public class ApplicationToOrderBadgeProcessingFormRequestTest
    extends ApplicationToOrderBadgeTestData {

  private ApplicationToOrderBadgeProcessingFormRequest converter;

  @Before
  public void setUp() {
    converter = new ApplicationToOrderBadgeProcessingFormRequest();
  }

  @Test
  public void convert_shouldWork() {
    OrderBadgeProcessingFormRequest formRequest = converter.convert(getApplication());
    assertThat(formRequest).isEqualTo(APPLICATION_TO_ORDER_BADGE_PROCESSING_FORM_REQUEST);
  }
}
