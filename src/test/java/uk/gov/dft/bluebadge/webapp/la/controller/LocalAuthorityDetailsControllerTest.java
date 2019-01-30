package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.LocalAuthorityDetailsFormRequestToLocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData;

public class LocalAuthorityDetailsControllerTest extends BaseControllerTest
    implements LocalAuthorityTestData {

  @Mock private ReferenceDataService referenceDataServiceMock;
  @Mock private LocalAuthorityDetailsFormRequestToLocalAuthority toLocalAuthorityMock;
  @Mock private LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest toFormRequestMock;

  private User dftAdminUserSignedIn;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    LocalAuthorityDetailsController controller =
        new LocalAuthorityDetailsController(
            referenceDataServiceMock, toLocalAuthorityMock, toFormRequestMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    dftAdminUserSignedIn =
        User.builder()
            .uuid(USER_ID)
            .emailAddress(EMAIL_ADDRESS)
            .name(NAME)
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .roleId(ROLE_DFT_ID)
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
  public void submit_shouldRedirectToManageLocalAuthorities_WhenAllMandatoryFieldsAreValid()
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
                .param(NATION_PARAM, NATION)
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_LOCAL_AUTHORITIES))
        .andExpect(
            model()
                .attribute(
                    MODEL_FORM_REQUEST, LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_MANDATORY_FIELDS));

    verify(referenceDataServiceMock)
        .updateLocalAuthority(SHORT_CODE, LOCAL_AUTHORITY_MANDATORY_FIELDS);
  }

  @Test
  public void submit_shouldRedirectToManageLocalAuthorities_WhenAllFieldsAreValid()
      throws Exception {
    when(toLocalAuthorityMock.convert(LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_ALL_FIELDS))
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
                .param(BADGE_COST_PARAM, BADGE_COST.toString())
                .param(PAYMENTS_ENABLED_PARAM, String.valueOf(PAYMENTS_ENABLED))
                .param(POSTCODE_PARAM, POSTCODE)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(NATION_PARAM, NATION)
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_LOCAL_AUTHORITIES))
        .andExpect(
            model().attribute(MODEL_FORM_REQUEST, LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_ALL_FIELDS));

    verify(referenceDataServiceMock).updateLocalAuthority(SHORT_CODE, LOCAL_AUTHORITY_ALL_FIELDS);
  }

  @Test
  public void
      submit_shouldDisplayLocalAuthorityTemplateWithErrors_WhenThereAreInvalidValues_InTheClient()
          throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest =
        LocalAuthorityDetailsFormRequest.builder()
            .description(DESCRIPTION)
            .country(COUNTRY)
            .postcode(POSTCODE)
            .nation(NATION)
            .differentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL_INVALID)
            .websiteUrl(WEB_SITE_URL_INVALID)
            .badgeCost(BADGE_COST_INVALID)
            .build();

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DESCRIPTION_PARAM, DESCRIPTION)
                .param(COUNTRY_PARAM, COUNTRY)
                .param(POSTCODE_PARAM, POSTCODE)
                .param(NATION_PARAM, NATION)
                .param(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL_INVALID)
                .param(WEB_SITE_URL_PARAM, WEB_SITE_URL_INVALID)
                .param(BADGE_COST_PARAM, BADGE_COST_INVALID.toString()))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().errorCount(3))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, "URL"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, WEB_SITE_URL_PARAM, "URL"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, BADGE_COST_PARAM, "DecimalMax"));
    verify(referenceDataServiceMock, times(0)).updateLocalAuthority(any(), any());
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
            .nation("")
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
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, NATION_PARAM, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, WEB_SITE_URL_PARAM, "NotBlank"));
    verify(referenceDataServiceMock, times(0)).updateLocalAuthority(any(), any());
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
                .param(NATION_PARAM, NATION)
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
  }
}
