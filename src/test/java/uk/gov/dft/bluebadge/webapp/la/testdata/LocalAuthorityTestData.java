package uk.gov.dft.bluebadge.webapp.la.testdata;

import java.math.BigDecimal;
import java.util.UUID;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.Nation;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;

public interface LocalAuthorityTestData {

  // Dft admin user signed-in
  String EMAIL_ADDRESS = "joeblogs@joe.com";
  String NAME = "joeblogs";
  int ROLE_DFT_ID = Role.DFT_ADMIN.getRoleId();
  String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  UUID USER_ID = UUID.randomUUID();

  // Fields
  String SHORT_CODE_PARAM = "shortCode";
  String SHORT_CODE = "ABERD";
  String DESCRIPTION_PARAM = "description";
  String DESCRIPTION = "local authority description";
  String WELSH_DESCRIPTION_PARAM = "welshDescription";
  String WELSH_DESCRIPTION = "Cymraeg";
  String NAME_LINE2_PARAM = "nameLine2";
  String NAME_LINE2 = "name line 2";
  String ADDRESS_LINE1_PARAM = "addressLine1";
  String ADDRESS_LINE1 = "address 1";
  String ADDRESS_LINE2_PARAM = "addressLine2";
  String ADDRESS_LINE2 = "address 2";
  String ADDRESS_LINE3_PARAM = "addressLine3";
  String ADDRESS_LINE3 = "address 3";
  String ADDRESS_LINE4_PARAM = "addressLine4";
  String ADDRESS_LINE4 = "address 4";
  String EMAIL_ADDRESS_PARAM = "emailAddress";
  String POSTCODE_PARAM = "postcode";
  String POSTCODE = "ABC123";
  String COUNTRY_PARAM = "country";
  String COUNTRY = "United Kingdom";
  String NATION_PARAM = "nation";
  String NATION = "ENG";
  String NATION_INVALID = "invalid";
  String TOWN_PARAM = "town";
  String TOWN = "London";
  String COUNTY_PARAM = "county";
  String COUNTY = "westminster";
  String CONTACT_NUMBER_PARAM = "contactNumber";
  String CONTACT_NUMBER = "010101010101101";
  String BADGE_PACK_TYPE_PARAM = "badgePackType";
  String BADGE_PACK_TYPE = "STANDARD";
  String WEB_SITE_URL_PARAM = "websiteUrl";
  String WEB_SITE_URL = "http://localhost";
  String WEB_SITE_URL_INVALID = "invalid";
  String PAYMENTS_ENABLED_PARAM = "paymentsEnabled";
  boolean PAYMENTS_ENABLED = false;
  String BADGE_COST_PARAM = "badgeCost";
  BigDecimal BADGE_COST = new BigDecimal("10.10");
  BigDecimal BADGE_COST_INVALID = new BigDecimal("1000");
  String DIFFERENT_SERVICE_SIGNPOST_URL_PARAM = "differentServiceSignpostUrl";
  String DIFFERENT_SERVICE_SIGNPOST_URL = "http://localhost:1111";
  String DIFFERENT_SERVICE_SIGNPOST_URL_INVALID = "invalid";

  // LocalAuthority
  LocalAuthority LOCAL_AUTHORITY =
      new LocalAuthority()
          .description(DESCRIPTION)
          .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);
  LocalAuthority LOCAL_AUTHORITY_EMPTY = new LocalAuthority();
  LocalAuthority LOCAL_AUTHORITY_MANDATORY_FIELDS =
      new LocalAuthority()
          .description(DESCRIPTION)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);
  LocalAuthority LOCAL_AUTHORITY_ALL_FIELDS =
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
  LocalAuthority LOCAL_AUTHORITY_INVALID_VALUE =
      new LocalAuthority()
          .description(DESCRIPTION)
          .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL_INVALID)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);

  LocalAuthorityDetailsFormRequest LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_EMPTY_FIELDS =
      LocalAuthorityDetailsFormRequest.builder().build();

  LocalAuthorityDetailsFormRequest LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_ALL_FIELDS =
      LocalAuthorityDetailsFormRequest.builder()
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

  LocalAuthorityDetailsFormRequest LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_MANDATORY_FIELDS =
      LocalAuthorityDetailsFormRequest.builder()
          .description(DESCRIPTION)
          .postcode(POSTCODE)
          .country(COUNTRY)
          .nation(NATION)
          .websiteUrl(WEB_SITE_URL)
          .build();

  LocalAuthorityRefData.LocalAuthorityMetaData LOCAL_AUTHORITY_META_DATA_ALL_FIELDS =
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
  LocalAuthorityRefData.LocalAuthorityMetaData LOCAL_AUTHORITY_META_DATA_EMPTY_FIELDS =
      LocalAuthorityRefData.LocalAuthorityMetaData.builder().build();
}
