package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliverToCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliveryOptionCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public abstract class OrderBadgeControllerTestData extends OrderBadgeTestData {

  static final OrderBadgeIndexFormRequest FORM_REQUEST_INDEX_PERSON =
      OrderBadgeIndexFormRequest.builder().applicantType("person").build();
  static final OrderBadgeIndexFormRequest FORM_REQUEST_INDEX_ORG =
      OrderBadgeIndexFormRequest.builder().applicantType("organisation").build();

  static final OrderBadgePersonDetailsFormRequest FORM_REQUEST_PERSON_DETAILS_WITH_IMAGE =
      OrderBadgePersonDetailsFormRequest.builder()
          .flowId(FLOW_ID)
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

  static final OrderBadgePersonDetailsFormRequest FORM_REQUEST_PERSON_DETAILS_WITHOUT_IMAGE =
      OrderBadgePersonDetailsFormRequest.builder()
          .flowId(FLOW_ID)
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

  static final OrderBadgeProcessingFormRequest FORM_REQUEST_PERSON_PROCESSING =
      OrderBadgeProcessingFormRequest.builder()
          .flowId(FLOW_ID)
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
          .deliverTo(DeliverToCodeField.HOME)
          .deliveryOptions(DeliveryOptionCodeField.FAST)
          .numberOfBadges(NUMBER_OF_BADGES_PERSON)
          .build();

  static final OrderBadgeProcessingFormRequest FORM_REQUEST_ORGANISATION_PROCESSING =
      OrderBadgeProcessingFormRequest.builder()
          .flowId(FLOW_ID)
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
          .deliverTo(DeliverToCodeField.HOME)
          .deliveryOptions(DeliveryOptionCodeField.FAST)
          .numberOfBadges(NUMBER_OF_BADGES_ORGANISATION)
          .build();

  protected MockMvc mockMvc;

  @Mock protected SecurityUtils securityUtilsMock;
  @Mock protected ReferenceDataService referenceDataServiceMock;
  @Mock protected BadgeService badgeServiceMock;
}
