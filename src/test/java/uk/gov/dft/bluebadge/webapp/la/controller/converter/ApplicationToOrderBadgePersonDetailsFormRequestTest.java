package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationToOrderBadgeTestData;

public class ApplicationToOrderBadgePersonDetailsFormRequestTest
    extends ApplicationToOrderBadgeTestData {

  private ApplicationToOrderBadgePersonDetailsFormRequest converter;

  @Before
  public void setUp() {
    converter = new ApplicationToOrderBadgePersonDetailsFormRequest();
  }

  @Test
  public void convert_shouldWork() {
    OrderBadgePersonDetailsFormRequest formRequest = converter.convert(APPLICATION);
    assertThat(formRequest).isEqualTo(APPLICATION_TO_ORDER_BADGE_PERSON_DETAILS_FORM_REQUEST);
  }
}
