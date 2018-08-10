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
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;

public class BadgeServiceTest {
  private static final String BADGE_NUMBER = "123";
  private static final List<String> BADGE_NUMBERS_FOR_PERSON = Lists.newArrayList(BADGE_NUMBER);
  private static final Badge BADGE = new Badge().badgeNumber(BADGE_NUMBER);
  public static final String NAME = "jason";

  @Mock private BadgeManagementApiClient badgeManagementApiClientMock;

  private BadgeService badgeService;
  private static final String POST_CODE = "L131PA";

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    badgeService = new BadgeService(badgeManagementApiClientMock);
  }

  @Test
  public void orderBadge_shouldOrderBadgesAndReturnBadgeNumber() {
    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest().numberOfBadges(1);
    when(badgeManagementApiClientMock.orderBlueBadges(badgeOrderRequest))
        .thenReturn(BADGE_NUMBERS_FOR_PERSON);
    String badgeNumber = badgeService.orderABadge(badgeOrderRequest);
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

    when(badgeManagementApiClientMock.findBadgeByPostCode(POST_CODE)).thenReturn(badgesList);
    List<BadgeSummary> returnedBadges = badgeService.findBadgeByPostcode(POST_CODE);
    assertThat(returnedBadges).isEqualTo(badgesList);
  }

  @Test
  public void findABadge_ShouldRetrieveAListOfBadges_WhenNoPostcodeIsProvided() {
    List<BadgeSummary> badgesList = Lists.newArrayList();

    when(badgeManagementApiClientMock.findBadgeByPostCode(null)).thenReturn(badgesList);
    List<BadgeSummary> returnedBadges = badgeService.findBadgeByPostcode(null);
    assertThat(returnedBadges).isEqualTo(badgesList);
  }

  @Test
  public void findABadge_ShouldRetrieveAnEmptyList_WhenPostCodeProvidedDoesNotExist() {
    List<BadgeSummary> emptyList = Lists.newArrayList();
    when(badgeManagementApiClientMock.findBadgeByPostCode(POST_CODE)).thenReturn(emptyList);
    List<BadgeSummary> badges = badgeService.findBadgeByPostcode(POST_CODE);
    assertThat(badges).isEqualTo(emptyList);
  }

  @Test
  public void findABadge_ShouldRetrieveAListOfBadges_ForAParticularUser() {
    BadgeSummary b1 = new BadgeSummary();
    BadgeSummary b2 = new BadgeSummary();
    List<BadgeSummary> badgesList = Lists.newArrayList(b1, b2);

    when(badgeManagementApiClientMock.findBadgeByName(NAME)).thenReturn(badgesList);
    List<BadgeSummary> returnedBadges = badgeService.findBadgeByName(NAME);
    assertThat(returnedBadges).isEqualTo(badgesList);
  }

  @Test
  public void findABadge_ShouldRetrieveAnEmptyList_WhenUserNameDoesNotExists() {
    List<BadgeSummary> emptyList = Lists.newArrayList();
    when(badgeManagementApiClientMock.findBadgeByName(NAME)).thenReturn(emptyList);
    List<BadgeSummary> badges = badgeService.findBadgeByName(NAME);
    assertThat(badges).isEqualTo(emptyList);
  }

  @Test
  public void findABadge_ShouldRetrieveAListOfBadges_WhenNoUserNameIsProvided() {
    List<BadgeSummary> badgesList = Lists.newArrayList();

    when(badgeManagementApiClientMock.findBadgeByName(null)).thenReturn(badgesList);
    List<BadgeSummary> returnedBadges = badgeService.findBadgeByName(null);
    assertThat(returnedBadges).isEqualTo(badgesList);
  }
}
