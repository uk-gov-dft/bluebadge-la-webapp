package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.BadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class BadgeToBadgeDetailsViewModelTest {

  private static final String PARTY_TYPE_CODE_PERSON = "PERSON";
  private static final String APPLICATION_CHANNEL = "CHANNEL";
  private static final String APPLICANT_AGE = "89";
  private static final LocalDate APPLICATION_DATE = LocalDate.of(2017, 1, 1);
  private static final String BADGE_NUMBER = "AAAAA1";
  private static final String BADGE_HOLDER_NAME = "MyName";
  private static final String BUILDING_AND_STREET = "building and street";
  private static final String CONTACT_FULL_NAME = "Contact full name";
  private static final String CONTACT_NUMBER = "0777777777";
  private static final LocalDate DOB = LocalDate.of(1930, 1, 1);
  private static final String ELIGIBILITY = "PIP";
  private static final String EMAIL_ADDRESS = "aa@aa.com";
  private static final LocalDate EXPIRY_DATE = LocalDate.of(2099, 7, 9);
  private static final LocalDate ORDER_DATE = LocalDate.of(2019, 1, 1);
  private static final LocalDateTime ISSUED_DATE = LocalDateTime.of(2019, 1, 2, 0, 0, 0);
  private static final String REJECTED_REASON = "any reason";
  private static final String GENDER = "MALE";
  private static final String IMAGE_LINK = "http://localhost/image";
  private static final String LINE2 = "address line 2";
  private static final String LOCAL_AUTHORITY = "BARNS";
  private static final String LOCAL_AUTHORITY_REF = "localAuthorityRef";
  private static final String NINO = "AAAA1SJ";
  private static final String POSTCODE = "postcode";
  private static final String SECONDARY_CONTACT_NUMBER = "0777777771";
  private static final LocalDate START_DATE = LocalDate.of(2096, 7, 9);
  private static final String STATUS = "NEW";
  private static final String TOWN_CITY = "town or city";

  private static final String ADDRESS_VIEW_MODEL =
      BUILDING_AND_STREET + ", " + LINE2 + ", " + TOWN_CITY + ", " + POSTCODE;
  private static final String APPLICATION_CHANNEL_VIEW_MODEL = "channel";
  private static final String APPLICATION_DATE_VIEW_MODEL = "01 January 2017";
  private static final String DOB_VIEW_MODEL = "01 January 1930";
  private static final String ELIGIBILITY_VIEW_MODEL = "pip";
  private static final String EXPIRY_DATE_VIEW_MODEL = "09 July 2099";
  private static final String ORDER_DATE_VIEW_MODEL = "01 January 2019";
  private static final String ISSUED_DATE_VIEW_MODEL = "02 January 2019";
  private static final String GENDER_VIEW_MODEL = "male";
  private static final String LOCAL_AUTHORITY_VIEW_MODEL = "Barnsley MBC";
  private static final String START_DATE_VIEW_MODEL = "09 July 2096";
  private static final String STATUS_VIEW_MODEL = "New";

  private static final Badge BADGE =
      new Badge()
          .applicationChannelCode(APPLICATION_CHANNEL)
          .imageLink(IMAGE_LINK)
          .applicationDate(APPLICATION_DATE)
          .badgeNumber(BADGE_NUMBER)
          .eligibilityCode(ELIGIBILITY)
          .expiryDate(EXPIRY_DATE)
          .orderDate(ORDER_DATE)
          .issuedDate(ISSUED_DATE)
          .rejectedReason(REJECTED_REASON)
          .localAuthorityShortCode(LOCAL_AUTHORITY)
          .localAuthorityRef(LOCAL_AUTHORITY_REF)
          .party(
              new Party()
                  .typeCode("PERSON")
                  .person(
                      new Person()
                          .badgeHolderName(BADGE_HOLDER_NAME)
                          .nino(NINO)
                          .dob(DOB)
                          .genderCode(GENDER))
                  .contact(
                      new Contact()
                          .fullName(CONTACT_FULL_NAME)
                          .buildingStreet(BUILDING_AND_STREET)
                          .townCity(TOWN_CITY)
                          .postCode(POSTCODE)
                          .line2(LINE2)
                          .primaryPhoneNumber(CONTACT_NUMBER)
                          .secondaryPhoneNumber(SECONDARY_CONTACT_NUMBER)
                          .emailAddress(EMAIL_ADDRESS)))
          .startDate(START_DATE)
          .statusCode(STATUS);

  private static final BadgeDetailsViewModel BADGE_DETAILS_VIEW_MODEL =
      BadgeDetailsViewModel.builder()
          .partyTypeCode(PARTY_TYPE_CODE_PERSON)
          .applicationChannel(APPLICATION_CHANNEL_VIEW_MODEL)
          .applicationDate(APPLICATION_DATE_VIEW_MODEL)
          .age(APPLICANT_AGE)
          .badgeNumber(BADGE_NUMBER)
          .badgeExpiryDate(EXPIRY_DATE_VIEW_MODEL)
          .badgeStartDate(START_DATE_VIEW_MODEL)
          .orderDate(ORDER_DATE_VIEW_MODEL)
          .issuedDate(ISSUED_DATE_VIEW_MODEL)
          .rejectedReason(REJECTED_REASON)
          .contactFullName(CONTACT_FULL_NAME)
          .contactNumber(CONTACT_NUMBER)
          .dob(DOB_VIEW_MODEL)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .emailAddress(EMAIL_ADDRESS)
          .fullName(BADGE_HOLDER_NAME)
          .gender(GENDER_VIEW_MODEL)
          .issuedBy(LOCAL_AUTHORITY_VIEW_MODEL)
          .localAuthorityShortCode(LOCAL_AUTHORITY)
          .localAuthorityReference(LOCAL_AUTHORITY_REF)
          .nino(NINO)
          .photoUrl(IMAGE_LINK)
          .secondaryContactNumber(SECONDARY_CONTACT_NUMBER)
          .status(STATUS_VIEW_MODEL)
          .address(ADDRESS_VIEW_MODEL)
          .build();

  @Mock private ReferenceDataService referenceDataServiceMock;

  private BadgeToBadgeDetailsViewModel converter;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(referenceDataServiceMock.retrieveBadgeApplicationChannelDisplayValue(APPLICATION_CHANNEL))
        .thenReturn(APPLICATION_CHANNEL_VIEW_MODEL);
    when(referenceDataServiceMock.retrieveBadgeEligibilityDisplayValue(ELIGIBILITY))
        .thenReturn(ELIGIBILITY_VIEW_MODEL);
    when(referenceDataServiceMock.retrieveBadgeGenderDisplayValue(GENDER))
        .thenReturn(GENDER_VIEW_MODEL);
    when(referenceDataServiceMock.retrieveBadgeLocalAuthorityDisplayValue(LOCAL_AUTHORITY))
        .thenReturn(LOCAL_AUTHORITY_VIEW_MODEL);
    when(referenceDataServiceMock.retrieveBadgeStatusDisplayValue(any(Badge.class)))
        .thenReturn(STATUS_VIEW_MODEL);
    converter =
        new BadgeToBadgeDetailsViewModel(referenceDataServiceMock, new PartyToAddressViewModel());
  }

  @Test
  public void convert_shouldWork() {
    assertThat(converter.convert(BADGE)).isEqualTo(BADGE_DETAILS_VIEW_MODEL);
  }
}
