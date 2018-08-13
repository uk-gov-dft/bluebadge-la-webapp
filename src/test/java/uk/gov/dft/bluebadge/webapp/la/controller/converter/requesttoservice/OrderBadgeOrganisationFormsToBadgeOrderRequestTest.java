package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData;

public class OrderBadgeOrganisationFormsToBadgeOrderRequestTest extends OrderBadgeTestData {

  @Mock SecurityUtils securityUtilsMock;

  private OrderBadgeOrganisationFormsToBadgeOrderRequest converter;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    LocalAuthority localAuthority =
        LocalAuthority.builder().id(-1).shortCode(LOCAL_AUTHORITY_SHORT_CODE).build();
    when(securityUtilsMock.getCurrentLocalAuthority()).thenReturn(localAuthority);
    converter = new OrderBadgeOrganisationFormsToBadgeOrderRequest(securityUtilsMock);
  }

  @Test
  public void convert_shouldWork() {
    BadgeOrderRequest badgeOrderRequest =
        converter.convert(FORM_REQUEST_ORGANISATION_DETAILS, FORM_REQUEST_ORGANISATION_PROCESSING);
    assertThat(badgeOrderRequest).isEqualTo(BADGE_ORDER_REQUEST_ORGANISATION);
  }
}
