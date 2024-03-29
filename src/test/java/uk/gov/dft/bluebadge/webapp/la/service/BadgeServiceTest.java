package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData.PHOTO;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeReplaceRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliverToCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliveryOptionCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.service.enums.CancelReason;
import uk.gov.dft.bluebadge.webapp.la.service.enums.ReplaceReason;

public class BadgeServiceTest {
  private static final String BADGE_NUMBER = "123";
  private static final List<String> BADGE_NUMBERS_FOR_PERSON = Lists.newArrayList(BADGE_NUMBER);
  private static final Badge BADGE = new Badge().badgeNumber(BADGE_NUMBER);
  public static final String NAME = "jason";
  private static final PagingInfo PAGING_INFO = new PagingInfo();

  static {
    PAGING_INFO.setPageNum(1);
    PAGING_INFO.setPageSize(50);
  }

  @Mock private BadgeManagementApiClient badgeManagementApiClientMock;

  private BadgeService badgeService;
  private static final String POST_CODE = "L131PA";

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    badgeService = new BadgeService(badgeManagementApiClientMock);
  }

  @Test
  public void orderBadge_shouldOrderBadgesAndReturnBadgeNumbers() {
    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest().numberOfBadges(1);
    when(badgeManagementApiClientMock.orderBlueBadges(badgeOrderRequest))
        .thenReturn(BADGE_NUMBERS_FOR_PERSON);
    List<String> badgeNumbers = badgeService.orderABadge(badgeOrderRequest);
    assertThat(badgeNumbers).isEqualTo(BADGE_NUMBERS_FOR_PERSON);
  }

  @Test
  public void orderBadgeForAPersonWithImageUploaded_shouldOrderOneBadgeAndReturnBadgeNumber()
      throws IOException {
    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest();

    MultipartFile photo = PHOTO();
    BufferedImage bufferedImage = ImageIO.read(photo.getInputStream());

    byte[] imageInByte;
    ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "JPG", byteArrayStream);
    byteArrayStream.flush();
    imageInByte = byteArrayStream.toByteArray();
    byteArrayStream.close();

    BadgeOrderRequest expectedBadgeOrderRequest = badgeOrderRequest.numberOfBadges(1);
    expectedBadgeOrderRequest.imageFile("base64");

    when(badgeManagementApiClientMock.orderBlueBadges(expectedBadgeOrderRequest))
        .thenReturn(BADGE_NUMBERS_FOR_PERSON);

    List<String> badgeNumbers = badgeService.orderABadgeForAPerson(badgeOrderRequest, imageInByte);

    assertThat(badgeNumbers).isEqualTo(BADGE_NUMBERS_FOR_PERSON);
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
    BadgesResponse response = new BadgesResponse().data(badgesList);

    when(badgeManagementApiClientMock.findBadgeByPostCode(POST_CODE, PAGING_INFO))
        .thenReturn(response);
    BadgesResponse returnedBadgesResponse =
        badgeService.findBadgeByPostcode(POST_CODE, PAGING_INFO);
    assertThat(returnedBadgesResponse.getData()).isEqualTo(badgesList);
  }

  @Test
  public void findABadge_ShouldRetrieveAListOfBadges_WhenNoPostcodeIsProvided() {
    List<BadgeSummary> badgesList = Lists.newArrayList();
    BadgesResponse response = new BadgesResponse().data(badgesList);

    when(badgeManagementApiClientMock.findBadgeByPostCode(null, PAGING_INFO)).thenReturn(response);
    BadgesResponse returnedBadgesResponse = badgeService.findBadgeByPostcode(null, PAGING_INFO);
    assertThat(returnedBadgesResponse).isEqualTo(response);
  }

  @Test
  public void findABadge_ShouldRetrieveAnEmptyList_WhenPostCodeProvidedDoesNotExist() {
    List<BadgeSummary> emptyList = Lists.newArrayList();
    BadgesResponse response = new BadgesResponse().data(emptyList);

    when(badgeManagementApiClientMock.findBadgeByPostCode(POST_CODE, PAGING_INFO))
        .thenReturn(response);
    BadgesResponse badgesResponse = badgeService.findBadgeByPostcode(POST_CODE, PAGING_INFO);
    assertThat(badgesResponse).isEqualTo(response);
  }

  @Test
  public void findABadge_ShouldRetrieveAListOfBadges_ForAParticularUser() {
    BadgeSummary b1 = new BadgeSummary();
    BadgeSummary b2 = new BadgeSummary();
    List<BadgeSummary> badgesList = Lists.newArrayList(b1, b2);
    BadgesResponse response = new BadgesResponse().data(badgesList);

    when(badgeManagementApiClientMock.findBadgeByName(NAME, PAGING_INFO)).thenReturn(response);
    BadgesResponse returnedBadgesResponse = badgeService.findBadgeByName(NAME, PAGING_INFO);
    assertThat(returnedBadgesResponse).isEqualTo(response);
  }

  @Test
  public void findABadge_ShouldRetrieveAnEmptyList_WhenUserNameDoesNotExists() {
    List<BadgeSummary> emptyList = Lists.newArrayList();
    BadgesResponse response = new BadgesResponse().data(emptyList);

    when(badgeManagementApiClientMock.findBadgeByName(NAME, PAGING_INFO)).thenReturn(response);
    BadgesResponse badgesResponse = badgeService.findBadgeByName(NAME, PAGING_INFO);
    assertThat(badgesResponse).isEqualTo(response);
  }

  @Test
  public void findABadge_ShouldRetrieveAListOfBadges_WhenNoUserNameIsProvided() {
    List<BadgeSummary> badgesList = Lists.newArrayList();
    BadgesResponse response = new BadgesResponse().data(badgesList);

    when(badgeManagementApiClientMock.findBadgeByName(null, PAGING_INFO)).thenReturn(response);
    BadgesResponse returnedBadgesResponse = badgeService.findBadgeByName(null, PAGING_INFO);
    assertThat(returnedBadgesResponse).isEqualTo(response);
  }

  @Test
  public void exportAllBadgesByLa_shouldWork() {
    ResponseEntity<byte[]> expectedResponse =
        new ResponseEntity<>("response".getBytes(), HttpStatus.OK);
    when(badgeManagementApiClientMock.exportBadgesByLa("ABERD")).thenReturn(expectedResponse);
    ResponseEntity<byte[]> response = badgeService.exportBadgesByLa("ABERD");
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  public void cancelABadge_shouldNotThrowException() {
    doNothing().when(badgeManagementApiClientMock).cancelBadge(BADGE_NUMBER, CancelReason.REVOKE);
    badgeService.cancelBadge(BADGE_NUMBER, CancelReason.REVOKE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cancelABadge_byPassingNullAsReason_shouldThrowIllegalArgumentException() {
    doThrow(HttpClientErrorException.class)
        .when(badgeManagementApiClientMock)
        .cancelBadge(any(), any());
    badgeService.cancelBadge(BADGE_NUMBER, null);
  }

  @Test(expected = HttpClientErrorException.class)
  public void cancelABadge_byPassingNullAsReason_shouldThrowHttpClientErrorException() {
    doThrow(HttpClientErrorException.class)
        .when(badgeManagementApiClientMock)
        .cancelBadge(any(), any());
    badgeService.cancelBadge(BADGE_NUMBER, CancelReason.REVOKE);
  }

  @Test
  public void deleteBadge_shouldCallApiClient() {
    badgeService.deleteBadge(BADGE_NUMBER);
    verify(badgeManagementApiClientMock).deleteBadge(BADGE_NUMBER);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteBadge_exceptionWhenBadgeNumberNotSet() {
    badgeService.deleteBadge(null);
  }

  @Test
  public void replaceBadge_success() {
    String NEW_BADGE_NUMBER = "abc";
    BadgeReplaceRequest request =
        BadgeReplaceRequest.builder()
            .badgeNumber(BADGE_NUMBER)
            .replaceReasonCode(ReplaceReason.LOST)
            .deliveryOptionCode(DeliveryOptionCodeField.STAND)
            .deliverToCode(DeliverToCodeField.HOME)
            .build();

    when(badgeManagementApiClientMock.replaceBadge(request)).thenReturn(NEW_BADGE_NUMBER);
    String newBadgeNumber = badgeService.replaceBadge(request);
    assertEquals(NEW_BADGE_NUMBER, newBadgeNumber);
  }

  @Test(expected = IllegalArgumentException.class)
  public void replaceBadge_byPassingNullParameters_shouldThrowIllegalArgumentException() {
    badgeService.replaceBadge(BadgeReplaceRequest.builder().build());
  }
}
