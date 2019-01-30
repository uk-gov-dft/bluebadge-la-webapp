package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationToOrderBadgeTestData;

public class ApplicationToOrderBadgeIndexFormRequestTest extends ApplicationToOrderBadgeTestData {

  private ApplicationToOrderBadgeIndexFormRequest converter;

  @Before
  public void setUp() {
    converter = new ApplicationToOrderBadgeIndexFormRequest();
  }

  @Test
  public void convert_shouldWork() {
    OrderBadgeIndexFormRequest formRequest = converter.convert(APPLICATION);
    assertThat(formRequest).isEqualTo(APPLICATION_TO_ORDER_BADGE_INDEX_FORM_REQUEST);
  }
}
