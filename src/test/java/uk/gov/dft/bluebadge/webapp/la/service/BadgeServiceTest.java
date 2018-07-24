package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.CommonResponse;

public class BadgeServiceTest {
  private static final String BADGE_NUMBER = "123";
  private static final List<String> BADGE_NUMBERS_FOR_PERSON = Lists.newArrayList(BADGE_NUMBER);
  private static final Badge BADGE = new Badge().badgeNumber(BADGE_NUMBER);

  @Mock private BadgeManagementApiClient badgeManagementApiClientMock;

  private BadgeService badgeService;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    badgeService = new BadgeService(badgeManagementApiClientMock);
  }

  @Test
  public void orderBadgeForAPerson_shouldOrderOneBadgeAndReturnBadgeNumber() {
    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest();
    BadgeOrderRequest expectedBadgeOrderRequest = badgeOrderRequest.numberOfBadges(1);
    when(badgeManagementApiClientMock.orderBlueBadges(expectedBadgeOrderRequest))
        .thenReturn(BADGE_NUMBERS_FOR_PERSON);
    String badgeNumber = badgeService.orderABadgeForAPerson(badgeOrderRequest);
    assertThat(badgeNumber).isEqualTo(BADGE_NUMBER);
  }

  @Test
  public void retrieve_ShouldRetrieveANonEmptyBadge_WhenClientReturnsANonEmptyBadge() {
    when(badgeManagementApiClientMock.retrieveBadge(BADGE_NUMBER)).thenReturn(BADGE);
    Optional<Badge> badgeMaybe = badgeService.retrieve(BADGE_NUMBER);
    assertThat(badgeMaybe).isEqualTo(Optional.of(BADGE));
  }

  @Test
  public void retrieve_ShouldRetrieveAnEmptyBadge_WhenBadgeNumberIsNull() {
    Optional<Badge> badgeMaybe = badgeService.retrieve(null);
    assertThat(badgeMaybe).isEqualTo(Optional.empty());
  }

  @Test
  public void retrieve_ShouldRetrieveAnEmptyBadge_WhenBadgeNumberIsEmpty() {
    Optional<Badge> badgeMaybe = badgeService.retrieve("");
    assertThat(badgeMaybe).isEqualTo(Optional.empty());
  }

  @Test
  public void retrieve_ShouldRetrieveAnEmptyBadge_WhenThereIsANotFoundException() {
    when(badgeManagementApiClientMock.retrieveBadge(BADGE_NUMBER))
        .thenThrow(new NotFoundException(new CommonResponse()));
    Optional<Badge> badgeMaybe = badgeService.retrieve(BADGE_NUMBER);
    assertThat(badgeMaybe).isEqualTo(Optional.empty());
  }

  @Test
  public void findABadge_ShouldRetrieveAListOfBadges_WhenValidPostCodeIsProvided() {
    BadgeSummary b1 = new BadgeSummary();
    BadgeSummary b2 = new BadgeSummary();
    List<BadgeSummary> badgesList = Lists.newArrayList(b1, b2);

    when(badgeManagementApiClientMock.findBadgeBy("postCode", "L131PA")).thenReturn(badgesList);
    List<BadgeSummary> returnedBadges = badgeService.findBadgesByAttribute("postCode", "L131PA");
    assertThat(returnedBadges).isEqualTo(badgesList);
  }

  @Test
  public void findABadge_ShouldRetrieveAnEmptyList_WhenPostCodeProvidedDoesNotExist() {
    List<BadgeSummary> emptyList = Lists.newArrayList();
    when(badgeManagementApiClientMock.findBadgeBy("postCode", "L131PA")).thenReturn(emptyList);
    List<BadgeSummary> badges = badgeService.findBadgesByAttribute("postCode", "L131PA");
    assertThat(badges.equals(emptyList));
  }
}
