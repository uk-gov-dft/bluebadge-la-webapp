package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData.LOCAL_AUTHORITY_SHORT_CODE;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE1;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE1_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE1_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE2;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE2_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE2_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE3;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE3_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE3_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE4;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE4_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.ADDRESS_LINE4_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.BADGE_COST;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.BADGE_COST_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.BADGE_COST_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.BADGE_PACK_TYPE;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.BADGE_PACK_TYPE_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.BADGE_PACK_TYPE_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.CONTACT_NUMBER;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.CONTACT_NUMBER_10_CHARACTERS;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.CONTACT_NUMBER_44_PREFIX;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.CONTACT_NUMBER_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.CONTACT_NUMBER_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.COUNTRY;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.COUNTRY_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.COUNTRY_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.COUNTY;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.COUNTY_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.COUNTY_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.DESCRIPTION;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.DESCRIPTION_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.DIFFERENT_SERVICE_SIGNPOST_URL;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.DIFFERENT_SERVICE_SIGNPOST_URL_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.DIFFERENT_SERVICE_SIGNPOST_URL_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.EMAIL_ADDRESS;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.EMAIL_ADDRESS_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.EMAIL_ADDRESS_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.GOV_UK_NOTIFY_API_KEY;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.GOV_UK_NOTIFY_API_KEY_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.GOV_UK_NOTIFY_API_KEY_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.GOV_UK_PAY_API_KEY;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.GOV_UK_PAY_API_KEY_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.GOV_UK_PAY_API_KEY_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.LOCAL_AUTHORITY;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.LOCAL_AUTHORITY_ALL_FIELDS;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_MANDATORY_FIELDS;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.LOCAL_AUTHORITY_MANDATORY_FIELDS;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.LOCAL_AUTHORITY_MANDATORY_FIELDS_WITH_PAYMENT;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.NAME_LINE2;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.NAME_LINE2_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.NAME_LINE2_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.NATION;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.NATION_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.PAYMENTS_ENABLED;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.PAYMENTS_ENABLED_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.POSTCODE;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.POSTCODE_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.POSTCODE_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.SHORT_CODE;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.SHORT_CODE_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.TOWN;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.TOWN_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.TOWN_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.WEB_SITE_URL;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.WEB_SITE_URL_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.WEB_SITE_URL_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.WELSH_DESCRIPTION;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.WELSH_DESCRIPTION_PARAM;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.TemplateName;
import uk.gov.dft.bluebadge.webapp.la.client.payment.model.GovPayProfile;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.LocalAuthorityDetailsFormRequestToLocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.MessageService;
import uk.gov.dft.bluebadge.webapp.la.service.PaymentService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData;

public class LocalAuthorityDetailsControllerTest extends BaseControllerTest {

  @Mock private ReferenceDataService referenceDataServiceMock;
  @Mock private PaymentService paymentServiceMock;
  @Mock private MessageService messageServiceMock;
  @Mock private LocalAuthorityDetailsFormRequestToLocalAuthority toLocalAuthorityMock;
  @Mock private LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest toFormRequestMock;

  private User dftAdminUserSignedIn;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    LocalAuthorityDetailsController controller =
        new LocalAuthorityDetailsController(
            referenceDataServiceMock,
            paymentServiceMock,
            messageServiceMock,
            toLocalAuthorityMock,
            toFormRequestMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    dftAdminUserSignedIn =
        User.builder()
            .uuid(LocalAuthorityTestData.USER_ID)
            .emailAddress(EMAIL_ADDRESS)
            .name(LocalAuthorityTestData.NAME)
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .roleId(Role.DFT_ADMIN.getRoleId())
            .build();
  }

  @Test
  public void show_shouldDisplayLocalAuthorityTemplateWithLocalAuthorityDetails() throws Exception {
    LocalAuthorityRefData referenceData = new LocalAuthorityRefData();
    referenceData.setShortCode(SHORT_CODE);
    LocalAuthorityRefData.LocalAuthorityMetaData metadata =
        LocalAuthorityRefData.LocalAuthorityMetaData.builder()
            .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
            .build();
    referenceData.setLocalAuthorityMetaData(metadata);
    when(referenceDataServiceMock.retrieveBadgeReferenceDataItem(RefDataGroupEnum.LA, SHORT_CODE))
        .thenReturn(Optional.of(referenceData));

    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder()
            .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
            .build();

    when(toFormRequestMock.convert(metadata)).thenReturn(expectedFormRequest);

    mockMvc
        .perform(
            get(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE).sessionAttr("user", dftAdminUserSignedIn))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().attribute(SHORT_CODE_PARAM, SHORT_CODE));
  }

  @Test
  public void submit_shouldRedirectToManageLocalAuthorities_WhenMandatoryFieldsAreValid()
      throws Exception {
    when(toLocalAuthorityMock.convert(LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_MANDATORY_FIELDS))
        .thenReturn(LOCAL_AUTHORITY_MANDATORY_FIELDS);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(POSTCODE_PARAM, POSTCODE)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(NATION_PARAM, NATION.name())
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_LOCAL_AUTHORITIES))
        .andExpect(
            model()
                .attribute(
                    MODEL_FORM_REQUEST, LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_MANDATORY_FIELDS));

    verify(referenceDataServiceMock)
        .updateLocalAuthority(SHORT_CODE, LOCAL_AUTHORITY_MANDATORY_FIELDS);
    verifyZeroInteractions(paymentServiceMock);
    verifyZeroInteractions(messageServiceMock);
  }

  @Test
  public void submit_shouldRedirectToManageLocalAuthorities_WhenAllFieldsAreValid()
      throws Exception {
    when(toLocalAuthorityMock.convert(
            LocalAuthorityTestData.getLocalAuthorityDetailsFormRequestAllFields()))
        .thenReturn(LOCAL_AUTHORITY_ALL_FIELDS);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(WELSH_DESCRIPTION_PARAM, WELSH_DESCRIPTION)
                .param(NAME_LINE2_PARAM, NAME_LINE2)
                .param(ADDRESS_LINE1_PARAM, ADDRESS_LINE1)
                .param(ADDRESS_LINE2_PARAM, ADDRESS_LINE2)
                .param(ADDRESS_LINE3_PARAM, ADDRESS_LINE3)
                .param(ADDRESS_LINE4_PARAM, ADDRESS_LINE4)
                .param(TOWN_PARAM, TOWN)
                .param(EMAIL_ADDRESS_PARAM, EMAIL_ADDRESS)
                .param(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL)
                .param(COUNTY_PARAM, COUNTY)
                .param(CONTACT_NUMBER_PARAM, CONTACT_NUMBER)
                .param(BADGE_PACK_TYPE_PARAM, BADGE_PACK_TYPE)
                .param(BADGE_COST_PARAM, BADGE_COST)
                .param(PAYMENTS_ENABLED_PARAM, String.valueOf(PAYMENTS_ENABLED))
                .param(POSTCODE_PARAM, POSTCODE)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(NATION_PARAM, NATION.name())
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL)
                .param(GOV_UK_PAY_API_KEY_PARAM, GOV_UK_PAY_API_KEY)
                .param(GOV_UK_NOTIFY_API_KEY_PARAM, GOV_UK_NOTIFY_API_KEY)
                .param(
                    GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_PARAM,
                    GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_LOCAL_AUTHORITIES))
        .andExpect(
            model()
                .attribute(
                    MODEL_FORM_REQUEST,
                    LocalAuthorityTestData.getLocalAuthorityDetailsFormRequestAllFields()));

    verify(referenceDataServiceMock).updateLocalAuthority(SHORT_CODE, LOCAL_AUTHORITY_ALL_FIELDS);
    GovPayProfile payProfile = GovPayProfile.builder().apiKey(GOV_UK_PAY_API_KEY).build();
    verify(paymentServiceMock).updateLocalAuthoritySecret(SHORT_CODE, payProfile);
    NotifyProfile notifyProfile =
        NotifyProfile.builder()
            .apiKey(GOV_UK_NOTIFY_API_KEY)
            .templates(
                ImmutableMap.of(
                    TemplateName.APPLICATION_SUBMITTED, GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID))
            .build();
    verify(messageServiceMock).updateLocalNotifySecret(SHORT_CODE, notifyProfile);
  }

  @Test
  @SneakyThrows
  public void
      submit_shouldRedirectToManageLocalAuthorities_WhenAllFieldsAreValidAndContactNumberIs10Characters() {
    LocalAuthority expectedLocalAuthority = new LocalAuthority();
    BeanUtils.copyProperties(expectedLocalAuthority, LOCAL_AUTHORITY_ALL_FIELDS);
    expectedLocalAuthority.setContactNumber(CONTACT_NUMBER_10_CHARACTERS);

    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder().build();
    BeanUtils.copyProperties(
        expectedFormRequest, LocalAuthorityTestData.getLocalAuthorityDetailsFormRequestAllFields());
    expectedFormRequest.setContactNumber(CONTACT_NUMBER_10_CHARACTERS);

    when(toLocalAuthorityMock.convert(expectedFormRequest)).thenReturn(expectedLocalAuthority);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(WELSH_DESCRIPTION_PARAM, WELSH_DESCRIPTION)
                .param(NAME_LINE2_PARAM, NAME_LINE2)
                .param(ADDRESS_LINE1_PARAM, ADDRESS_LINE1)
                .param(ADDRESS_LINE2_PARAM, ADDRESS_LINE2)
                .param(ADDRESS_LINE3_PARAM, ADDRESS_LINE3)
                .param(ADDRESS_LINE4_PARAM, ADDRESS_LINE4)
                .param(TOWN_PARAM, TOWN)
                .param(EMAIL_ADDRESS_PARAM, EMAIL_ADDRESS)
                .param(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL)
                .param(COUNTY_PARAM, COUNTY)
                .param(CONTACT_NUMBER_PARAM, CONTACT_NUMBER_10_CHARACTERS)
                .param(BADGE_PACK_TYPE_PARAM, BADGE_PACK_TYPE)
                .param(BADGE_COST_PARAM, BADGE_COST)
                .param(PAYMENTS_ENABLED_PARAM, String.valueOf(PAYMENTS_ENABLED))
                .param(POSTCODE_PARAM, POSTCODE)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(NATION_PARAM, NATION.name())
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL)
                .param(GOV_UK_PAY_API_KEY_PARAM, GOV_UK_PAY_API_KEY)
                .param(GOV_UK_NOTIFY_API_KEY_PARAM, GOV_UK_NOTIFY_API_KEY)
                .param(
                    GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_PARAM,
                    GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_LOCAL_AUTHORITIES))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest));

    verify(referenceDataServiceMock).updateLocalAuthority(SHORT_CODE, expectedLocalAuthority);
    GovPayProfile payProfile = GovPayProfile.builder().apiKey(GOV_UK_PAY_API_KEY).build();
    verify(paymentServiceMock).updateLocalAuthoritySecret(SHORT_CODE, payProfile);
    NotifyProfile notifyProfile =
        NotifyProfile.builder()
            .apiKey(GOV_UK_NOTIFY_API_KEY)
            .templates(
                ImmutableMap.of(
                    TemplateName.APPLICATION_SUBMITTED, GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID))
            .build();
    verify(messageServiceMock).updateLocalNotifySecret(SHORT_CODE, notifyProfile);
  }

  @Test
  @SneakyThrows
  public void
      submit_shouldRedirectToManageLocalAuthorities_WhenAllFieldsAreValidAndContactNumberHas44Prefix() {
    LocalAuthority expectedLocalAuthority = new LocalAuthority();
    BeanUtils.copyProperties(expectedLocalAuthority, LOCAL_AUTHORITY_ALL_FIELDS);
    expectedLocalAuthority.setContactNumber(CONTACT_NUMBER_44_PREFIX);

    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder().build();
    BeanUtils.copyProperties(
        expectedFormRequest, LocalAuthorityTestData.getLocalAuthorityDetailsFormRequestAllFields());
    expectedFormRequest.setContactNumber(CONTACT_NUMBER_44_PREFIX);

    when(toLocalAuthorityMock.convert(expectedFormRequest)).thenReturn(expectedLocalAuthority);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(WELSH_DESCRIPTION_PARAM, WELSH_DESCRIPTION)
                .param(NAME_LINE2_PARAM, NAME_LINE2)
                .param(ADDRESS_LINE1_PARAM, ADDRESS_LINE1)
                .param(ADDRESS_LINE2_PARAM, ADDRESS_LINE2)
                .param(ADDRESS_LINE3_PARAM, ADDRESS_LINE3)
                .param(ADDRESS_LINE4_PARAM, ADDRESS_LINE4)
                .param(TOWN_PARAM, TOWN)
                .param(EMAIL_ADDRESS_PARAM, EMAIL_ADDRESS)
                .param(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL)
                .param(COUNTY_PARAM, COUNTY)
                .param(CONTACT_NUMBER_PARAM, CONTACT_NUMBER_44_PREFIX)
                .param(BADGE_PACK_TYPE_PARAM, BADGE_PACK_TYPE)
                .param(BADGE_COST_PARAM, BADGE_COST)
                .param(PAYMENTS_ENABLED_PARAM, String.valueOf(PAYMENTS_ENABLED))
                .param(POSTCODE_PARAM, POSTCODE)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(NATION_PARAM, NATION.name())
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL)
                .param(GOV_UK_PAY_API_KEY_PARAM, GOV_UK_PAY_API_KEY)
                .param(GOV_UK_NOTIFY_API_KEY_PARAM, GOV_UK_NOTIFY_API_KEY)
                .param(
                    GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_PARAM,
                    GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_LOCAL_AUTHORITIES))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest));

    verify(referenceDataServiceMock).updateLocalAuthority(SHORT_CODE, expectedLocalAuthority);
    GovPayProfile payProfile = GovPayProfile.builder().apiKey(GOV_UK_PAY_API_KEY).build();
    verify(paymentServiceMock).updateLocalAuthoritySecret(SHORT_CODE, payProfile);
    NotifyProfile notifyProfile =
        NotifyProfile.builder()
            .apiKey(GOV_UK_NOTIFY_API_KEY)
            .templates(
                ImmutableMap.of(
                    TemplateName.APPLICATION_SUBMITTED, GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID))
            .build();
    verify(messageServiceMock).updateLocalNotifySecret(SHORT_CODE, notifyProfile);
  }

  @Test
  public void
      submit_shouldDisplayLocalAuthorityTemplateWithErrors_WhenThereAreInvalidValues_InTheClient()
          throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder()
            .description(DESCRIPTION)
            .badgePackType(BADGE_PACK_TYPE_INVALID)
            .nameLine2(NAME_LINE2_INVALID)
            .addressLine1(ADDRESS_LINE1_INVALID)
            .addressLine2(ADDRESS_LINE2_INVALID)
            .addressLine3(ADDRESS_LINE3_INVALID)
            .addressLine4(ADDRESS_LINE4_INVALID)
            .postcode(POSTCODE_INVALID)
            .town(TOWN_INVALID)
            .county(COUNTY_INVALID)
            .country(COUNTRY_INVALID)
            .nation(NATION)
            .contactNumber(CONTACT_NUMBER_INVALID)
            .emailAddress(EMAIL_ADDRESS_INVALID)
            .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL_INVALID)
            .websiteUrl(WEB_SITE_URL_INVALID)
            .govUkPayApiKey(GOV_UK_PAY_API_KEY_INVALID)
            .govUkNotifyApiKey(GOV_UK_NOTIFY_API_KEY_INVALID)
            .govUkNotifyApplicationSubmittedTemplateId(
                GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_INVALID)
            .build();

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(BADGE_PACK_TYPE_PARAM, BADGE_PACK_TYPE_INVALID)
                .param(NAME_LINE2_PARAM, NAME_LINE2_INVALID)
                .param(ADDRESS_LINE1_PARAM, ADDRESS_LINE1_INVALID)
                .param(ADDRESS_LINE2_PARAM, ADDRESS_LINE2_INVALID)
                .param(ADDRESS_LINE3_PARAM, ADDRESS_LINE3_INVALID)
                .param(ADDRESS_LINE4_PARAM, ADDRESS_LINE4_INVALID)
                .param(TOWN_PARAM, TOWN_INVALID)
                .param(COUNTY_PARAM, COUNTY_INVALID)
                .param(POSTCODE_PARAM, POSTCODE_INVALID)
                .param(COUNTRY_PARAM, COUNTRY_INVALID)
                .param(CONTACT_NUMBER_PARAM, CONTACT_NUMBER_INVALID)
                .param(EMAIL_ADDRESS_PARAM, EMAIL_ADDRESS_INVALID)
                .param(NATION_PARAM, NATION.name())
                .param(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL_INVALID)
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL_INVALID)
                .param(GOV_UK_PAY_API_KEY_PARAM, GOV_UK_PAY_API_KEY_INVALID)
                .param(GOV_UK_NOTIFY_API_KEY_PARAM, GOV_UK_NOTIFY_API_KEY_INVALID)
                .param(
                    GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_PARAM,
                    GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_INVALID))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().errorCount(17))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(MODEL_FORM_REQUEST, BADGE_PACK_TYPE_PARAM, "Pattern"))
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, NAME_LINE2_PARAM, "Size"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, ADDRESS_LINE1_PARAM, "Size"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, ADDRESS_LINE2_PARAM, "Size"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, ADDRESS_LINE3_PARAM, "Size"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, ADDRESS_LINE4_PARAM, "Size"))
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, TOWN_PARAM, "Size"))
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, COUNTY_PARAM, "Size"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, POSTCODE_PARAM, "Pattern"))
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, COUNTRY_PARAM, "Size"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, CONTACT_NUMBER_PARAM, "Pattern"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, EMAIL_ADDRESS_PARAM, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, "URL"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, WEB_SITE_URL_PARAM, "URL"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(MODEL_FORM_REQUEST, GOV_UK_PAY_API_KEY_PARAM, "Size"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, GOV_UK_NOTIFY_API_KEY_PARAM, "Size"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, GOV_UK_APPLICATION_SUBMITTED_TEMPLATE_ID_PARAM, "Size"));

    verifyZeroInteractions(referenceDataServiceMock);
    verifyZeroInteractions(paymentServiceMock);
    verifyZeroInteractions(messageServiceMock);
  }

  @Test
  public void
      submit_shouldDisplayLocalAuthorityTemplateWithErrors_WhenThereAreEmptyValues_InTheClient()
          throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder()
            .description("")
            .country("")
            .postcode("")
            .nation(null)
            .websiteUrl("")
            .build();

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, "")
                .param(COUNTRY_PARAM, "")
                .param(POSTCODE_PARAM, "")
                .param(NATION_PARAM, "")
                .param(WEB_SITE_URL_PARAM, ""))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().errorCount(5))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, DESCRIPTION_PARAM, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, COUNTRY_PARAM, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, POSTCODE_PARAM, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, WEB_SITE_URL_PARAM, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, NATION_PARAM, "NotNull"));

    verifyZeroInteractions(referenceDataServiceMock);
    verifyZeroInteractions(paymentServiceMock);
    verifyZeroInteractions(messageServiceMock);
  }

  @Test
  public void
      submit_shouldDisplayLocalAuthorityTemplateWithErrors_WhenThereAreValidationErrorsInTheServer()
          throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder()
            .description(DESCRIPTION)
            .country(COUNTRY)
            .postcode(POSTCODE)
            .nation(NATION)
            .websiteUrl(WEB_SITE_URL)
            .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL)
            .build();
    when(toLocalAuthorityMock.convert(expectedFormRequest)).thenReturn(LOCAL_AUTHORITY);

    CommonResponse commonResponse = new CommonResponse();
    uk.gov.dft.bluebadge.common.api.model.Error error = new Error();
    List<ErrorErrors> errorErrors =
        Lists.newArrayList(
            new ErrorErrors()
                .field(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM)
                .message("Enter a valid URL"));
    error.setErrors(errorErrors);
    commonResponse.setError(error);

    doThrow(new BadRequestException(commonResponse))
        .when(referenceDataServiceMock)
        .updateLocalAuthority(SHORT_CODE, LOCAL_AUTHORITY);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(POSTCODE_PARAM, POSTCODE)
                .param(NATION_PARAM, NATION.name())
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL)
                .param(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().errorCount(1))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, "Enter a valid URL"));

    verify(referenceDataServiceMock, times(1)).updateLocalAuthority(SHORT_CODE, LOCAL_AUTHORITY);
    verifyZeroInteractions(paymentServiceMock);
    verifyZeroInteractions(messageServiceMock);
  }

  @Test
  public void
      submit_shouldRedirectToManageLocalAuthorities_WhenMandatoryFieldsAreValidAndPaymentIsEnabledWithBadgeCost()
          throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder()
            .description(DESCRIPTION)
            .postcode(POSTCODE)
            .country(COUNTRY)
            .nation(NATION)
            .websiteUrl(WEB_SITE_URL)
            .paymentsEnabled(!PAYMENTS_ENABLED)
            .badgeCost(BADGE_COST)
            .build();
    when(toLocalAuthorityMock.convert(expectedFormRequest))
        .thenReturn(LOCAL_AUTHORITY_MANDATORY_FIELDS_WITH_PAYMENT);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(POSTCODE_PARAM, POSTCODE)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(NATION_PARAM, NATION.name())
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL)
                .param(PAYMENTS_ENABLED_PARAM, String.valueOf(!PAYMENTS_ENABLED))
                .param(BADGE_COST_PARAM, BADGE_COST))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_LOCAL_AUTHORITIES))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest));

    verify(referenceDataServiceMock)
        .updateLocalAuthority(SHORT_CODE, LOCAL_AUTHORITY_MANDATORY_FIELDS_WITH_PAYMENT);
    verifyZeroInteractions(paymentServiceMock);
    verifyZeroInteractions(messageServiceMock);
  }

  @Test
  public void
      submit_shouldDisplayLocalAuthorityTemplateWithErrors_WhenMandatoryFieldsAreValidAndPaymentIsEnabledWithoutBadgeCost()
          throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder()
            .description(DESCRIPTION)
            .postcode(POSTCODE)
            .country(COUNTRY)
            .nation(NATION)
            .websiteUrl(WEB_SITE_URL)
            .paymentsEnabled(!PAYMENTS_ENABLED)
            .build();

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(POSTCODE_PARAM, POSTCODE)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(NATION_PARAM, NATION.name())
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL)
                .param(PAYMENTS_ENABLED_PARAM, String.valueOf(!PAYMENTS_ENABLED)))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().errorCount(1))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST,
                    BADGE_COST_PARAM,
                    "NotNull.localAuthorityDetailPage.badgeCost"));
    verifyZeroInteractions(referenceDataServiceMock);
    verifyZeroInteractions(paymentServiceMock);
    verifyZeroInteractions(messageServiceMock);
  }

  @Test
  public void
      submit_shouldDisplayLocalAuthorityTemplateWithErrors_WhenMandatoryFieldsAreValidAndPaymentIsEnabledWithOutOfRangeBadgeCost()
          throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder()
            .description(DESCRIPTION)
            .postcode(POSTCODE)
            .country(COUNTRY)
            .nation(NATION)
            .websiteUrl(WEB_SITE_URL)
            .paymentsEnabled(!PAYMENTS_ENABLED)
            .badgeCost(BADGE_COST_INVALID)
            .build();

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(POSTCODE_PARAM, POSTCODE)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(NATION_PARAM, NATION.name())
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL)
                .param(PAYMENTS_ENABLED_PARAM, String.valueOf(!PAYMENTS_ENABLED))
                .param(BADGE_COST_PARAM, BADGE_COST_INVALID))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().errorCount(1))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST,
                    BADGE_COST_PARAM,
                    "Range.localAuthorityDetailPage.badgeCost"));
    verifyZeroInteractions(referenceDataServiceMock);
    verifyZeroInteractions(paymentServiceMock);
    verifyZeroInteractions(messageServiceMock);
  }
}
