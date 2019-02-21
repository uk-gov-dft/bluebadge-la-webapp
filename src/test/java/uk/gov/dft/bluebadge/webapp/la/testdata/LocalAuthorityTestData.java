package uk.gov.dft.bluebadge.webapp.la.testdata;

import java.util.UUID;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.Nation;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;

public final class LocalAuthorityTestData {

  // Dft admin user signed-in
  public static String EMAIL_ADDRESS = "joeblogs@joe.com";
  public static String NAME = "joeblogs";
  public static String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  public static UUID USER_ID = UUID.randomUUID();

  // Fields
  public static String SHORT_CODE_PARAM = "shortCode";
  public static String SHORT_CODE = "ABERD";
  public static String DESCRIPTION_PARAM = "description";
  public static String DESCRIPTION = "local authority description";
  public static String WELSH_DESCRIPTION_PARAM = "welshDescription";
  public static String WELSH_DESCRIPTION = "Cymraeg";
  public static String NAME_LINE2_PARAM = "nameLine2";
  public static String NAME_LINE2 = "name line 2";
  public static String ADDRESS_LINE1_PARAM = "addressLine1";
  public static String ADDRESS_LINE1 = "address 1";
  public static String ADDRESS_LINE2_PARAM = "addressLine2";
  public static String ADDRESS_LINE2 = "address 2";
  public static String ADDRESS_LINE3_PARAM = "addressLine3";
  public static String ADDRESS_LINE3 = "address 3";
  public static String ADDRESS_LINE4_PARAM = "addressLine4";
  public static String ADDRESS_LINE4 = "address 4";
  public static String EMAIL_ADDRESS_PARAM = "emailAddress";
  public static String POSTCODE_PARAM = "postcode";
  public static String POSTCODE = "ABC123";
  public static String COUNTRY_PARAM = "country";
  public static String COUNTRY = "United Kingdom";
  public static String NATION_PARAM = "nation";
  public static String NATION = "ENG";
  public static String NATION_INVALID = "invalid";
  public static String TOWN_PARAM = "town";
  public static String TOWN = "London";
  public static String COUNTY_PARAM = "county";
  public static String COUNTY = "westminster";
  public static String CONTACT_NUMBER_PARAM = "contactNumber";
  public static String CONTACT_NUMBER = "010101010101101";
  public static String BADGE_PACK_TYPE_PARAM = "badgePackType";
  public static String BADGE_PACK_TYPE = "STANDARD";
  public static String WEB_SITE_URL_PARAM = "websiteUrl";
  public static String WEB_SITE_URL = "http://localhost";
  public static String WEB_SITE_URL_INVALID = "invalid";
  public static String PAYMENTS_ENABLED_PARAM = "paymentsEnabled";
  public static boolean PAYMENTS_ENABLED = false;
  public static String BADGE_COST_PARAM = "badgeCost";
  public static String BADGE_COST = "10.10";
  public static String BADGE_COST_INVALID = "1000";
  public static String DIFFERENT_SERVICE_SIGNPOST_URL_PARAM = "differentServiceSignpostUrl";
  public static String DIFFERENT_SERVICE_SIGNPOST_URL = "http://localhost:1111";
  public static String DIFFERENT_SERVICE_SIGNPOST_URL_INVALID = "invalid";

  // LocalAuthority
  public static LocalAuthority LOCAL_AUTHORITY =
      new LocalAuthority()
          .description(DESCRIPTION)
          .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);
  public static LocalAuthority LOCAL_AUTHORITY_EMPTY = new LocalAuthority();
  public static LocalAuthority LOCAL_AUTHORITY_MANDATORY_FIELDS =
      new LocalAuthority()
          .description(DESCRIPTION)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);
  public static LocalAuthority LOCAL_AUTHORITY_ALL_FIELDS =
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
          .badgeCost(BADGE_COST)
          .badgePackType(BADGE_PACK_TYPE)
          .contactNumber(CONTACT_NUMBER)
          .emailAddress(EMAIL_ADDRESS)
          .town(TOWN);
  public static LocalAuthority LOCAL_AUTHORITY_INVALID_VALUE =
      new LocalAuthority()
          .description(DESCRIPTION)
          .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL_INVALID)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);

  public static LocalAuthorityDetailsFormRequest LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_EMPTY_FIELDS =
      LocalAuthorityDetailsFormRequest.builder().build();

  public static LocalAuthorityDetailsFormRequest getLocalAuthorityDetailsFormRequestAllFields() {
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
        .build();
  }

  public static LocalAuthorityDetailsFormRequest
      LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_MANDATORY_FIELDS =
          LocalAuthorityDetailsFormRequest.builder()
              .description(DESCRIPTION)
              .postcode(POSTCODE)
              .country(COUNTRY)
              .nation(NATION)
              .websiteUrl(WEB_SITE_URL)
              .build();

  public static LocalAuthorityRefData.LocalAuthorityMetaData LOCAL_AUTHORITY_META_DATA_ALL_FIELDS =
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
          .badgeCost(BADGE_COST)
          .postcode(POSTCODE)
          .country(COUNTRY)
          .nation(Nation.forValue(NATION))
          .contactUrl(WEB_SITE_URL)
          .build();
}
