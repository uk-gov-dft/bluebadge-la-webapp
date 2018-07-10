package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;

public class BadgeServiceTest {
  private static final String BADGE_NUMBER1 = "123";
  private static final List<String> BADGE_NUMBERS_FOR_PERSON = Lists.newArrayList(BADGE_NUMBER1);

  @Mock private BadgeManagementApiClient badgeManagementApiClientMock;

  private BadgeService badgeService;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    badgeService = new BadgeService(badgeManagementApiClientMock);
  }

  @Ignore
  @Test
  public void orderBadgeForAPerson_shouldOrderOneBadgeAndReturnBadgeNumber() {
    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest();
    BadgeOrderRequest expectedBadgeOrderRequest = badgeOrderRequest.numberOfBadges(1);
    when(badgeManagementApiClientMock.orderBlueBadges(expectedBadgeOrderRequest))
        .thenReturn(BADGE_NUMBERS_FOR_PERSON);
    String badgeNumber = badgeService.orderABadgeForAPerson(badgeOrderRequest);
    assertThat(badgeNumber).isEqualTo(BADGE_NUMBER1);
  }
}
