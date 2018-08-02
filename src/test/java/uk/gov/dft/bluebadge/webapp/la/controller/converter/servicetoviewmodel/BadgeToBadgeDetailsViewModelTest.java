package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

  private static final String APPLICATION_CHANNEL = "CHANNEL";
  private static final LocalDate APPLICATION_DATE = LocalDate.of(2017, 1, 1);
  private static final String BADGE_NUMBER = "AAAAA1";
  private static final String BADGE_HOLDER_NAME = "MyName";
  private static final String BUILDING_AND_STREET = "building and street";

  private static final String CONTACT_NUMBER = "0777777777";
  private static final LocalDate DOB = LocalDate.of(1930, 1, 1);
  private static final String ELIGIBILITY = "PIP";
  private static final LocalDate EXPIRY_DATE = LocalDate.of(2099, 7, 9);
  private static final String GENDER = "MALE";
  private static final String IMAGE_LINK = "http://localhost/image";
  private static final String LINE2 = "address line 2";
  private static final int LOCAL_AUTHORITY_ID = 2;
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
  private static final String APPLICATION_DATE_VIEW_MODEL = "01/01/17";
  private static final String DOB_VIEW_MODEL = "01/01/30";
  private static final String ELIGIBILITY_VIEW_MODEL = "pip";
  private static final String EXPIRY_DATE_VIEW_MODEL = "09/07/99";
  private static final String GENDER_VIEW_MODEL = "male";
  private static final String LOCAL_AUTHORITY_VIEW_MODEL = "2";
  private static final String START_DATE_VIEW_MODEL = "09/07/96";
  private static final String STATUS_VIEW_MODEL = "New";

  private static final Badge BADGE =
      new Badge()
          .applicationChannelCode(APPLICATION_CHANNEL)
          .imageLink(IMAGE_LINK)
          .applicationDate(APPLICATION_DATE)
          .badgeNumber(BADGE_NUMBER)
          .eligibilityCode(ELIGIBILITY)
          .expiryDate(EXPIRY_DATE)
          .localAuthorityId(LOCAL_AUTHORITY_ID)
          .localAuthorityRef(LOCAL_AUTHORITY_REF)
          .party(
              new Party()
                  .person(
                      new Person()
                          .badgeHolderName(BADGE_HOLDER_NAME)
                          .nino(NINO)
                          .dob(DOB)
                          .genderCode(GENDER))
                  .contact(
                      new Contact()
                          .buildingStreet(BUILDING_AND_STREET)
                          .townCity(TOWN_CITY)
                          .postCode(POSTCODE)
                          .line2(LINE2)
                          .primaryPhoneNumber(CONTACT_NUMBER)
                          .secondaryPhoneNumber(SECONDARY_CONTACT_NUMBER)))
          .startDate(START_DATE)
          .statusCode(STATUS);

  private static final BadgeDetailsViewModel BADGE_DETAILS_VIEW_MODEL =
      BadgeDetailsViewModel.builder()
          .applicationChannel(APPLICATION_CHANNEL_VIEW_MODEL)
          .applicationDate(APPLICATION_DATE_VIEW_MODEL)
          .badgeNumber(BADGE_NUMBER)
          .badgeExpiryDate(EXPIRY_DATE_VIEW_MODEL)
          .badgeStartDate(START_DATE_VIEW_MODEL)
          .contactNumber(CONTACT_NUMBER)
          .dob(DOB_VIEW_MODEL)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .fullName(BADGE_HOLDER_NAME)
          .gender(GENDER_VIEW_MODEL)
          .issuedBy(LOCAL_AUTHORITY_VIEW_MODEL)
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
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(referenceDataServiceMock.retrieveApplicationChannelDisplayValue(APPLICATION_CHANNEL))
        .thenReturn(APPLICATION_CHANNEL_VIEW_MODEL);
    when(referenceDataServiceMock.retrieveGenderDisplayValue(GENDER)).thenReturn(GENDER_VIEW_MODEL);
    when(referenceDataServiceMock.retrieveEligibilityDisplayValue(ELIGIBILITY))
        .thenReturn(ELIGIBILITY_VIEW_MODEL);
    when(referenceDataServiceMock.retrieveStatusDisplayValue(STATUS)).thenReturn(STATUS_VIEW_MODEL);
    converter = new BadgeToBadgeDetailsViewModel(referenceDataServiceMock);
  }

  @Test
  public void convert_shouldWork() {
    assertThat(converter.convert(BADGE)).isEqualTo(BADGE_DETAILS_VIEW_MODEL);
  }
}
