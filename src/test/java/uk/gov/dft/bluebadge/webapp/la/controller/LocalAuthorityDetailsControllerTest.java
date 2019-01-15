package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
import java.util.UUID;
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
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class LocalAuthorityDetailsControllerTest extends BaseControllerTest {

  // Dft admin user signed-in
  private static final String EMAIL_ADDRESS = "joeblogs@joe.com";
  private static final String NAME = "joeblogs";
  private static final int ROLE_DFT_ID = Role.DFT_ADMIN.getRoleId();
  private static final String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  private static final UUID USER_ID = UUID.randomUUID();

  // Local Authority test data
  private static final String MODEL_FORM_REQUEST = "formRequest";
  private static final String SHORT_CODE_PARAM = "shortCode";
  private static final String SHORT_CODE = "ABERD";
  private static final String DIFFERENT_SERVICE_SIGNPOST_URL_PARAM = "differentServiceSignpostUrl";
  private static final String DIFFERENT_SERVICE_SIGNPOST_URL = "http://localhost:1111";
  private static final String DIFFERENT_SERVICE_SIGNPOST_URL_EMPTY = "";
  private static final String DIFFERENT_SERVICE_SIGNPOST_URL_INVALID = "invalid";

  @Mock private ReferenceDataService referenceDataServiceMock;

  private User dftAdminUserSignedIn;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    LocalAuthorityDetailsController controller =
        new LocalAuthorityDetailsController(referenceDataServiceMock);

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
        new LocalAuthorityRefData.LocalAuthorityMetaData();
    metadata.setDifferentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL);
    referenceData.setLocalAuthorityMetaData(metadata);
    when(referenceDataServiceMock.retrieveBadgeReferenceDataItem(RefDataGroupEnum.LA, SHORT_CODE))
        .thenReturn(Optional.of(referenceData));

    LocalAuthorityDetailsFormRequest expectedFormRequest = new LocalAuthorityDetailsFormRequest();
    expectedFormRequest.setDifferentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL);

    mockMvc
        .perform(
            get(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE).sessionAttr("user", dftAdminUserSignedIn))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().attribute(SHORT_CODE_PARAM, SHORT_CODE));
  }

  @Test
  public void submit_shouldRedirectToManageLocalAuthorities_WhenDifferentServiceSignpostUrlIsValid()
      throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest = new LocalAuthorityDetailsFormRequest();
    expectedFormRequest.setDifferentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_LOCAL_AUTHORITIES))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest));

    verify(referenceDataServiceMock)
        .updateLocalAuthority(SHORT_CODE, DIFFERENT_SERVICE_SIGNPOST_URL);
  }

  @Test
  public void submit_shouldRedirectToManageLocalAuthorities_WhenDifferentServiceSignpostUrlIsEmpty()
      throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest = new LocalAuthorityDetailsFormRequest();
    expectedFormRequest.setDifferentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL_EMPTY);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL_EMPTY))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_LOCAL_AUTHORITIES))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest));

    verify(referenceDataServiceMock)
        .updateLocalAuthority(SHORT_CODE, DIFFERENT_SERVICE_SIGNPOST_URL_EMPTY);
  }

  @Test
  public void
      submit_shouldDisplayLocalAuthorityTemplateWithErrors_WhenThereAreValidationErrorsInTheClient()
          throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest = new LocalAuthorityDetailsFormRequest();
    expectedFormRequest.setDifferentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL_INVALID);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(
                    DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL_INVALID))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().errorCount(1))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, "URL"));

    verify(referenceDataServiceMock, times(0)).updateLocalAuthority(any(), any());
  }

  @Test
  public void
      submit_shouldDisplayLocalAuthorityTemplateWithErrors_WhenThereAreValidationErrorsInTheServer()
          throws Exception {
    LocalAuthorityDetailsFormRequest expectedFormRequest = new LocalAuthorityDetailsFormRequest();
    expectedFormRequest.setDifferentServiceSignpostUrl(DIFFERENT_SERVICE_SIGNPOST_URL);

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
        .updateLocalAuthority(SHORT_CODE, DIFFERENT_SERVICE_SIGNPOST_URL);

    mockMvc
        .perform(
            post(URL_LOCAL_AUTHORITY_DETAILS + SHORT_CODE)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, DIFFERENT_SERVICE_SIGNPOST_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_LOCAL_AUTHORITY_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().errorCount(1))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, "Enter a valid URL"));

    verify(referenceDataServiceMock, times(1))
        .updateLocalAuthority(SHORT_CODE, DIFFERENT_SERVICE_SIGNPOST_URL);
  }
}
