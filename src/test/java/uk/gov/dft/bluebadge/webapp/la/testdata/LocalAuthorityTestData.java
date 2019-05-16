package uk.gov.dft.bluebadge.webapp.la.testdata;

import java.math.BigDecimal;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import uk.gov.dft.bluebadge.common.service.enums.Nation;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;

public final class LocalAuthorityTestData {

  // Dft admin user signed-in
  public static final String EMAIL_ADDRESS = "joeblogs@joe.com";
  public static final String NAME = "joeblogs";
  public static final String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  public static UUID USER_ID = UUID.randomUUID();
  public static final String OVER_FORTY_LONG_STRING =
      "This is definitely longer than forty characters...";

  // Fields
  public static final String SHORT_CODE_PARAM = "shortCode";
  public static final String SHORT_CODE = "ABERD";
  public static final String DESCRIPTION_PARAM = "description";
  public static final String DESCRIPTION = "local authority description";
  public static final String WELSH_DESCRIPTION_PARAM = "welshDescription";
  public static final String WELSH_DESCRIPTION = "Cymraeg";
  public static final String NAME_LINE2_PARAM = "nameLine2";
  public static final String NAME_LINE2 = "name line 2";
  public static final String NAME_LINE2_INVALID = "";
  public static final String ADDRESS_LINE1_PARAM = "addressLine1";
  public static final String ADDRESS_LINE1 = "address 1";
  public static final String ADDRESS_LINE1_INVALID = OVER_FORTY_LONG_STRING;
  public static final String ADDRESS_LINE2_PARAM = "addressLine2";
  public static final String ADDRESS_LINE2 = "address 2";
  public static final String ADDRESS_LINE2_INVALID = OVER_FORTY_LONG_STRING;
  public static final String ADDRESS_LINE3_PARAM = "addressLine3";
  public static final String ADDRESS_LINE3 = "address 3";
  public static final String ADDRESS_LINE3_INVALID = OVER_FORTY_LONG_STRING;
  public static final String ADDRESS_LINE4_PARAM = "addressLine4";
  public static final String ADDRESS_LINE4 = "address 4";
  public static final String ADDRESS_LINE4_INVALID = OVER_FORTY_LONG_STRING;
  public static final String EMAIL_ADDRESS_PARAM = "emailAddress";
  public static final String EMAIL_ADDRESS_INVALID = "invalid";
  public static final String POSTCODE_PARAM = "postcode";
  public static final String POSTCODE = "M4 1FS";
  public static final String POSTCODE_INVALID = "ABC 123";
  public static final String COUNTRY_PARAM = "country";
  public static final String COUNTRY = "United Kingdom";
  public static final String COUNTRY_INVALID = OVER_FORTY_LONG_STRING;
  public static final String NATION_PARAM = "nation";
  public static Nation NATION = Nation.ENG;
  public static final String TOWN_PARAM = "town";
  public static final String TOWN = "London";
  public static final String TOWN_INVALID = OVER_FORTY_LONG_STRING;
  public static final String COUNTY_PARAM = "county";
  public static final String COUNTY = "westminster";
  public static final String COUNTY_INVALID = OVER_FORTY_LONG_STRING;
  public static final String CONTACT_NUMBER_PARAM = "contactNumber";
  public static final String CONTACT_NUMBER = "0 7812 345 6 78";
  public static final String CONTACT_NUMBER_TRIMMED = "07812345678";
  public static final String CONTACT_NUMBER_10_CHARACTERS = "078 12 345 68";
  public static final String CONTACT_NUMBER_44_PREFIX = "+44 78 12 345 678";
  public static final String CONTACT_NUMBER_INVALID = "01010101010101010101";
  public static final String BADGE_PACK_TYPE_PARAM = "badgePackType";
  public static final String BADGE_PACK_TYPE = "Standard";
  public static final String BADGE_PACK_TYPE_INVALID = "invalid";
  public static final String WEB_SITE_URL_PARAM = "websiteUrl";
  public static final String WEB_SITE_URL = "http://localhost";
  public static final String WEB_SITE_URL_INVALID = "invalid";
  public static final String PAYMENTS_ENABLED_PARAM = "paymentsEnabled";
  public static final boolean PAYMENTS_ENABLED = false;
  public static final String BADGE_COST_PARAM = "badgeCost";
  public static final String BADGE_COST = "10.10";
  public static final String BADGE_COST_INVALID = "1000";
  public static final String DIFFERENT_SERVICE_SIGNPOST_URL_PARAM = "differentServiceSignpostUrl";
  public static final String DIFFERENT_SERVICE_SIGNPOST_URL = "http://localhost:1111";
  public static final String DIFFERENT_SERVICE_SIGNPOST_URL_INVALID = "invalid";
  public static final String GOV_UK_PAY_API_KEY_PARAM = "govUkPayApiKey";
  public static final String GOV_UK_PAY_API_KEY = "govUkPayApiKeyValue1";
  public static final String GOV_UK_PAY_API_KEY_INVALID = StringUtils.leftPad("a", 201);
  public static final String GOV_UK_NOTIFY_API_KEY_PARAM = "govUkNotifyApiKey";
  public static final String GOV_UK_NOTIFY_API_KEY = "govUkNotifyApiKeyValue1";
  public static final String GOV_UK_NOTIFY_API_KEY_INVALID = StringUtils.leftPad("b", 201);
  public static final String GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_PARAM =
      "govUkNotifyApplicationSubmittedTemplateId";
  public static final String GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID =
      "govUkNotifyApplicationSubmittedTemplateIdValue1";
  public static final String GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_INVALID =
      StringUtils.leftPad("b", 201);
  public static final String STREAMLINED_CITIZEN_REAPPLICATION_JOURNEY_ENABLED_PARAM =
      "streamlinedCitizenReapplicationJourneyEnabled";
  public static final Boolean STREAMLINED_CITIZEN_REAPPLICATION_JOURNEY_ENABLED = Boolean.TRUE;

  // LocalAuthority
  public static final LocalAuthority LOCAL_AUTHORITY =
      new LocalAuthority()
          .description(DESCRIPTION)
          .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);
  public static final LocalAuthority LOCAL_AUTHORITY_EMPTY = new LocalAuthority();
  public static final LocalAuthority LOCAL_AUTHORITY_MANDATORY_FIELDS =
      new LocalAuthority()
          .description(DESCRIPTION)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);
  public static final LocalAuthority LOCAL_AUTHORITY_MANDATORY_FIELDS_WITH_PAYMENT =
      new LocalAuthority()
          .description(DESCRIPTION)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL)
          .paymentsEnabled(!PAYMENTS_ENABLED)
          .badgeCost(new BigDecimal(BADGE_COST));
  public static final LocalAuthority LOCAL_AUTHORITY_ALL_FIELDS =
      new LocalAuthority()
          .description(DESCRIPTION)
          .welshDescription(WELSH_DESCRIPTION)
          .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL)
          .addressLine1(ADDRESS_LINE1)
          .addressLine2(ADDRESS_LINE2)
          .addressLine3(ADDRESS_LINE3)
          .addressLine4(ADDRESS_LINE4)
          .nameLine2(NAME_LINE2)
          .contactUrl(WEB_SITE_URL)
          .county(COUNTY)
          .paymentsEnabled(PAYMENTS_ENABLED)
          .badgeCost(new BigDecimal(BADGE_COST))
          .badgePackType(BADGE_PACK_TYPE)
          .contactNumber(CONTACT_NUMBER_TRIMMED)
          .emailAddress(EMAIL_ADDRESS)
          .streamlinedCitizenReapplicationJourneyEnabled(
              STREAMLINED_CITIZEN_REAPPLICATION_JOURNEY_ENABLED)
          .town(TOWN);
  public static final LocalAuthority LOCAL_AUTHORITY_INVALID_VALUE =
      new LocalAuthority()
          .description(DESCRIPTION)
          .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL_INVALID)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);

  public static final LocalAuthorityDetailsFormRequest
      LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_EMPTY_FIELDS =
          LocalAuthorityDetailsFormRequest.builder().build();

  public static final LocalAuthorityDetailsFormRequest
      getLocalAuthorityDetailsFormRequestAllFields() {
    return LocalAuthorityDetailsFormRequest.builder()
        .description(DESCRIPTION)
        .welshDescription(WELSH_DESCRIPTION)
        .nameLine2(NAME_LINE2)
        .addressLine1(ADDRESS_LINE1)
        .addressLine2(ADDRESS_LINE2)
        .addressLine3(ADDRESS_LINE3)
        .addressLine4(ADDRESS_LINE4)
        .town(TOWN)
        .emailAddress(EMAIL_ADDRESS)
        .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
        .county(COUNTY)
        .contactNumber(CONTACT_NUMBER)
        .badgePackType(BADGE_PACK_TYPE)
        .paymentsEnabled(PAYMENTS_ENABLED)
        .badgeCost(BADGE_COST)
        .postcode(POSTCODE)
        .country(COUNTRY)
        .nation(NATION)
        .websiteUrl(WEB_SITE_URL)
        .govUkPayApiKey(GOV_UK_PAY_API_KEY)
        .govUkNotifyApiKey(GOV_UK_NOTIFY_API_KEY)
        .govUkNotifyApplicationSubmittedTemplateId(GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID)
        .streamlinedCitizenReapplicationJourneyEnabled(
            STREAMLINED_CITIZEN_REAPPLICATION_JOURNEY_ENABLED)
        .build();
  }

  public static final LocalAuthorityDetailsFormRequest
      getLocalAuthorityDetailsFormRequestAllFieldsButApiKeys() {
    LocalAuthorityDetailsFormRequest formRequest = getLocalAuthorityDetailsFormRequestAllFields();
    formRequest.setGovUkPayApiKey(null);
    formRequest.setGovUkNotifyApiKey(null);
    formRequest.setGovUkNotifyApplicationSubmittedTemplateId(null);
    return formRequest;
  }

  public static final LocalAuthorityDetailsFormRequest
      LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_MANDATORY_FIELDS =
          LocalAuthorityDetailsFormRequest.builder()
              .description(DESCRIPTION)
              .postcode(POSTCODE)
              .country(COUNTRY)
              .nation(NATION)
              .websiteUrl(WEB_SITE_URL)
              .build();

  public static final LocalAuthorityRefData.LocalAuthorityMetaData
      LOCAL_AUTHORITY_META_DATA_ALL_FIELDS =
          LocalAuthorityRefData.LocalAuthorityMetaData.builder()
              .welshDescription(WELSH_DESCRIPTION)
              .nameLine2(NAME_LINE2)
              .addressLine1(ADDRESS_LINE1)
              .addressLine2(ADDRESS_LINE2)
              .addressLine3(ADDRESS_LINE3)
              .addressLine4(ADDRESS_LINE4)
              .town(TOWN)
              .emailAddress(EMAIL_ADDRESS)
              .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
              .county(COUNTY)
              .contactNumber(CONTACT_NUMBER)
              .clockType(BADGE_PACK_TYPE)
              .paymentsEnabled(PAYMENTS_ENABLED)
              .badgeCost(new BigDecimal(BADGE_COST))
              .postcode(POSTCODE)
              .country(COUNTRY)
              .nation(NATION)
              .contactUrl(WEB_SITE_URL)
              .streamlinedCitizenReapplicationJourneyEnabled(
                  STREAMLINED_CITIZEN_REAPPLICATION_JOURNEY_ENABLED)
              .build();
}
