package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData;

public class OrderBadgePersonFormsToBadgeOrderRequestTest extends OrderBadgeTestData {

  @Mock SecurityUtils securityUtilsMock;

  private OrderBadgePersonFormsToBadgeOrderRequest converter;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(securityUtilsMock.getCurrentLocalAuthorityShortCode())
        .thenReturn(LOCAL_AUTHORITY_SHORT_CODE);
    converter = new OrderBadgePersonFormsToBadgeOrderRequest(securityUtilsMock);
  }

  @Test
  public void convert_shouldWork() {
    BadgeOrderRequest badgeOrderRequest =
        converter.convert(FORM_REQUEST_PERSON_DETAILS, FORM_REQUEST_PERSON_PROCESSING);
    assertThat(badgeOrderRequest).isEqualTo(BADGE_ORDER_REQUEST_PERSON);
  }
}
