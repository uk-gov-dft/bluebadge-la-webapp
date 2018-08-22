package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public abstract class OrderBadgeBaseControllerTest extends OrderBadgeTestData {

  protected static final OrderBadgeIndexFormRequest FORM_REQUEST_INDEX_PERSON =
      OrderBadgeIndexFormRequest.builder().applicantType("person").build();

  protected static final OrderBadgeIndexFormRequest FORM_REQUEST_INDEX_ORGANISATION =
      OrderBadgeIndexFormRequest.builder().applicantType("organisation").build();

  protected static final OrderBadgePersonDetailsFormRequest FORM_REQUEST_PERSON_DETAILS_WITH_IMAGE =
      OrderBadgePersonDetailsFormRequest.builder()
          .buildingAndStreet(BUILDING_AND_STREET)
          .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
          .contactDetailsSecondaryContactNumber(CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
          .contactDetailsName(CONTACT_DETAILS_NAME)
          .dobDay(Integer.valueOf(DOB_DAY))
          .dobMonth(Integer.valueOf(DOB_MONTH))
          .dobYear(Integer.valueOf(DOB_YEAR))
          .eligibility(ELIGIBILITY)
          .name(NAME)
          .nino(NINO)
          .optionalAddressField(OPTIONAL_ADDRESS_FIELD)
          .postcode(POSTCODE)
          .townOrCity(TOWN_OR_CITY)
          .photo(PHOTO())
          .thumbBase64("thumbnail")
          .byteImage("thumbnail".getBytes())
          .build();

  protected static final OrderBadgePersonDetailsFormRequest
      FORM_REQUEST_PERSON_DETAILS_WITHOUT_IMAGE =
          OrderBadgePersonDetailsFormRequest.builder()
              .buildingAndStreet(BUILDING_AND_STREET)
              .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
              .contactDetailsSecondaryContactNumber(CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
              .contactDetailsName(CONTACT_DETAILS_NAME)
              .dobDay(Integer.valueOf(DOB_DAY))
              .dobMonth(Integer.valueOf(DOB_MONTH))
              .dobYear(Integer.valueOf(DOB_YEAR))
              .eligibility(ELIGIBILITY)
              .name(NAME)
              .nino(NINO)
              .optionalAddressField(OPTIONAL_ADDRESS_FIELD)
              .postcode(POSTCODE)
              .townOrCity(TOWN_OR_CITY)
              .photo(EMPTY_PHOTO)
              .build();

  protected static final OrderBadgeProcessingFormRequest FORM_REQUEST_PERSON_PROCESSING =
      OrderBadgeProcessingFormRequest.builder()
          .applicationChannel(APPLICATION_CHANNEL)
          .applicationDateDay(Integer.valueOf(APPLICATION_DATE_DAY))
          .applicationDateMonth(Integer.valueOf(APPLICATION_DATE_MONTH))
          .applicationDateYear(Integer.valueOf(APPLICATION_DATE_YEAR))
          .localAuthorityReferenceNumber(LOCAL_AUTHORITY_REFERENCE_NUMBER)
          .badgeStartDateDay(Integer.valueOf(BADGE_START_DATE_DAY))
          .badgeStartDateMonth(Integer.valueOf(BADGE_START_DATE_MONTH))
          .badgeStartDateYear(Integer.valueOf(BADGE_START_DATE_YEAR))
          .badgeExpiryDateDay(Integer.valueOf(BADGE_EXPIRY_DATE_DAY))
          .badgeExpiryDateMonth(Integer.valueOf(BADGE_EXPIRY_DATE_MONTH))
          .badgeExpiryDateYear(Integer.valueOf(BADGE_EXPIRY_DATE_YEAR))
          .deliverTo(DELIVER_TO)
          .deliveryOptions(DELIVERY_OPTIONS)
          .numberOfBadges(NUMBER_OF_BADGES_PERSON)
          .build();

  protected static final OrderBadgeProcessingFormRequest FORM_REQUEST_ORGANISATION_PROCESSING =
      OrderBadgeProcessingFormRequest.builder()
          .applicationChannel(APPLICATION_CHANNEL)
          .applicationDateDay(Integer.valueOf(APPLICATION_DATE_DAY))
          .applicationDateMonth(Integer.valueOf(APPLICATION_DATE_MONTH))
          .applicationDateYear(Integer.valueOf(APPLICATION_DATE_YEAR))
          .localAuthorityReferenceNumber(LOCAL_AUTHORITY_REFERENCE_NUMBER)
          .badgeStartDateDay(Integer.valueOf(BADGE_START_DATE_DAY))
          .badgeStartDateMonth(Integer.valueOf(BADGE_START_DATE_MONTH))
          .badgeStartDateYear(Integer.valueOf(BADGE_START_DATE_YEAR))
          .badgeExpiryDateDay(Integer.valueOf(BADGE_EXPIRY_DATE_DAY))
          .badgeExpiryDateMonth(Integer.valueOf(BADGE_EXPIRY_DATE_MONTH))
          .badgeExpiryDateYear(Integer.valueOf(BADGE_EXPIRY_DATE_YEAR))
          .deliverTo(DELIVER_TO)
          .deliveryOptions(DELIVERY_OPTIONS)
          .numberOfBadges(NUMBER_OF_BADGES_ORGANISATION)
          .build();

  protected MockMvc mockMvc;

  @Mock protected SecurityUtils securityUtilsMock;
  @Mock protected ReferenceDataService referenceDataServiceMock;
  @Mock protected BadgeService badgeServiceMock;
}
