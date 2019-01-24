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
  static final String EMAIL_ADDRESS = "joeblogs@joe.com";
  static final String NAME = "joeblogs";
  static final int ROLE_DFT_ID = Role.DFT_ADMIN.getRoleId();
  static final String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  static final UUID USER_ID = UUID.randomUUID();

  // Fields
  static final String SHORT_CODE_PARAM = "shortCode";
  static final String SHORT_CODE = "ABERD";
  static final String DESCRIPTION_PARAM = "description";
  static final String DESCRIPTION = "local authority description";
  static final String WELSH_DESCRIPTION_PARAM = "welshDescription";
  static final String WELSH_DESCRIPTION = "Cymraeg";
  static final String NAME_LINE2_PARAM = "nameLine2";
  static final String NAME_LINE2 = "name line 2";
  static final String ADDRESS_LINE1_PARAM = "addressLine1";
  static final String ADDRESS_LINE1 = "address 1";
  static final String ADDRESS_LINE2_PARAM = "addressLine2";
  static final String ADDRESS_LINE2 = "address 2";
  static final String ADDRESS_LINE3_PARAM = "addressLine3";
  static final String ADDRESS_LINE3 = "address 3";
  static final String ADDRESS_LINE4_PARAM = "addressLine4";
  static final String ADDRESS_LINE4 = "address 4";
  static final String EMAIL_ADDRESS_PARAM = "emailAddress";
  static final String POSTCODE_PARAM = "postcode";
  static final String POSTCODE = "ABC123";
  static final String COUNTRY_PARAM = "country";
  static final String COUNTRY = "United Kingdom";
  static final String NATION_PARAM = "nation";
  static final String NATION = "ENG";
  static final String TOWN_PARAM = "town";
  static final String TOWN = "London";
  static final String COUNTY_PARAM = "county";
  static final String COUNTY = "westminster";
  static final String CONTACT_NUMBER_PARAM = "contactNumber";
  static final String CONTACT_NUMBER = "010101010101101";
  static final String BADGE_PACK_TYPE_PARAM = "badgePackType";
  static final String BADGE_PACK_TYPE = "STANDARD";
  static final String WEB_SITE_URL_PARAM = "websiteUrl";
  static final String WEB_SITE_URL = "http://localhost";
  static final String WEB_SITE_URL_INVALID = "invalid";
  static final String PAYMENTS_ENABLED_PARAM = "paymentsEnabled";
  static final boolean PAYMENTS_ENABLED = false;
  static final String BADGE_COST_PARAM = "badgeCost";
  static final BigDecimal BADGE_COST = new BigDecimal(10.10);
  static final BigDecimal BADGE_COST_INVALID = new BigDecimal(1000);
  static final String DIFFERENT_SERVICE_SIGNPOST_URL_PARAM = "differentServiceSignpostUrl";
  static final String DIFFERENT_SERVICE_SIGNPOST_URL = "http://localhost:1111";
  static final String DIFFERENT_SERVICE_SIGNPOST_URL_INVALID = "invalid";

  // LocalAuthority
  static final LocalAuthority LOCAL_AUTHORITY =
      new LocalAuthority()
          .description(DESCRIPTION)
          .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);
  static final LocalAuthority LOCAL_AUTHORITY_EMPTY = new LocalAuthority();
  static final LocalAuthority LOCAL_AUTHORITY_MANDATORY_FIELDS =
      new LocalAuthority()
          .description(DESCRIPTION)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);
  static final LocalAuthority LOCAL_AUTHORITY_ALL_FIELDS =
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
  static final LocalAuthority LOCAL_AUTHORITY_INVALID_VALUE =
      new LocalAuthority()
          .description(DESCRIPTION)
          .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL_INVALID)
          .country(COUNTRY)
          .postcode(POSTCODE)
          .nation(NATION)
          .contactUrl(WEB_SITE_URL);

  // LocalAuthorityDetailsFormRequest
  static final LocalAuthorityDetailsFormRequest LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_EMPTY_FIELDS =
      LocalAuthorityDetailsFormRequest.builder().build();

  static final LocalAuthorityDetailsFormRequest LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_ALL_FIELDS =
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

  static final LocalAuthorityDetailsFormRequest
      LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_MANDATORY_FIELDS =
          LocalAuthorityDetailsFormRequest.builder()
              .description(DESCRIPTION)
              .postcode(POSTCODE)
              .country(COUNTRY)
              .nation(NATION)
              .websiteUrl(WEB_SITE_URL)
              .build();

  // LocalAuthorityRefData.LocalAuthorityMetaData
  static final LocalAuthorityRefData.LocalAuthorityMetaData LOCAL_AUTHORITY_META_DATA_ALL_FIELDS =
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
          .badgePackType(BADGE_PACK_TYPE)
          .paymentsEnabled(PAYMENTS_ENABLED)
          .badgeCost(BADGE_COST)
          .postcode(POSTCODE)
          .country(COUNTRY)
          .nation(Nation.forValue(NATION))
          .contactUrl(WEB_SITE_URL)
          .build();
  static final LocalAuthorityRefData.LocalAuthorityMetaData LOCAL_AUTHORITY_META_DATA_EMPTY_FIELDS =
      LocalAuthorityRefData.LocalAuthorityMetaData.builder().build();
}
