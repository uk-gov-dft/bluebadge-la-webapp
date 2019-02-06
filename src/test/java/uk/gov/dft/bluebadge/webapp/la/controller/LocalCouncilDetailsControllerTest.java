package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalCouncil;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalCouncilRefData;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class LocalCouncilDetailsControllerTest extends BaseControllerTest {
  @Mock private ReferenceDataService referenceDataServiceMock;

  private MockMvc mockMvc;
  private User dftAdminUserSignedIn;

  private static final String EMAIL_ADDRESS = "joeblogs@joe.com";
  private static final String NAME = "joeblogs";
  private static final int ROLE_DFT_ID = Role.DFT_ADMIN.getRoleId();
  private static final String LOCAL_COUNCIL_SHORT_CODE = "ABERC";
  private static final String URL =
      LocalCouncilDetailsController.URL.replace("{shortCode}", LOCAL_COUNCIL_SHORT_CODE);
  private static final String URL_ERROR = URL + "#error";
  private static final String URL_MANAGE = ManageLocalCouncilsController.URL;
  private static final UUID USER_ID = UUID.randomUUID();

  class FormFields {
    static final String DESCRIPTION = "description";
    static final String DESCRIPTION_VALUE_ORIG = "LC description";
    static final String VALUE_TOO_LONG =
        "I am longer than 100 chars.123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
    static final String WELSH_DESCRIPTION = "welshDescription";
    static final String WELSH_DESCRIPTION_VALUE_ORIG = "Welsh";
  }

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    LocalCouncilDetailsController controller =
        new LocalCouncilDetailsController(referenceDataServiceMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    dftAdminUserSignedIn =
        User.builder()
            .uuid(USER_ID)
            .emailAddress(EMAIL_ADDRESS)
            .name(NAME)
            // .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .roleId(ROLE_DFT_ID)
            .build();
  }

  @Test
  public void show_shouldDisplayLocalCouncilTemplateWithLocalCouncilDetails() throws Exception {
    LocalCouncilRefData referenceData = new LocalCouncilRefData();
    referenceData.setShortCode(LOCAL_COUNCIL_SHORT_CODE);
    referenceData.setDescription(FormFields.DESCRIPTION_VALUE_ORIG);
    LocalCouncilRefData.LocalCouncilMetaData metadata =
        new LocalCouncilRefData.LocalCouncilMetaData();
    metadata.setWelshDescription(FormFields.WELSH_DESCRIPTION_VALUE_ORIG);
    metadata.setIssuingAuthorityShortCode("ABERD");
    referenceData.setLocalCouncilMetaData(metadata);
    when(referenceDataServiceMock.retrieveBadgeReferenceDataItem(
            RefDataGroupEnum.LC, LOCAL_COUNCIL_SHORT_CODE))
        .thenReturn(Optional.of(referenceData));

    LocalCouncil expectedFormRequest =
        LocalCouncil.builder()
            .description(FormFields.DESCRIPTION_VALUE_ORIG)
            .welshDescription(FormFields.WELSH_DESCRIPTION_VALUE_ORIG)
            .build();
    mockMvc
        .perform(get(URL).sessionAttr("user", dftAdminUserSignedIn))
        .andExpect(status().isOk())
        .andExpect(view().name(LocalCouncilDetailsController.TEMPLATE))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(model().attribute("shortCode", LOCAL_COUNCIL_SHORT_CODE));
  }

  @Test
  public void submit_shouldRedirectToManageLocalCouncils_WhenAllMandatoryFieldsAreValid()
      throws Exception {

    mockMvc
        .perform(
            post(URL)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(FormFields.DESCRIPTION, FormFields.DESCRIPTION_VALUE_ORIG))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE));

    verify(referenceDataServiceMock)
        .updateLocalCouncil(
            LOCAL_COUNCIL_SHORT_CODE,
            LocalCouncil.builder().description(FormFields.DESCRIPTION_VALUE_ORIG).build());
  }

  @Test
  public void submit_shouldRedirectToManageLocalCouncils_WhenAllFieldsAreValid() throws Exception {
    mockMvc
        .perform(
            post(URL)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(FormFields.DESCRIPTION, FormFields.DESCRIPTION_VALUE_ORIG)
                .param(FormFields.WELSH_DESCRIPTION, FormFields.WELSH_DESCRIPTION_VALUE_ORIG))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE));

    verify(referenceDataServiceMock)
        .updateLocalCouncil(
            LOCAL_COUNCIL_SHORT_CODE,
            LocalCouncil.builder()
                .description(FormFields.DESCRIPTION_VALUE_ORIG)
                .welshDescription(FormFields.WELSH_DESCRIPTION_VALUE_ORIG)
                .build());
  }

  @Test
  public void
      submit_shouldDisplayLocalCouncilTemplateWithErrors_WhenThereAreInvalidValues_InTheClient()
          throws Exception {
    LocalCouncil expectedFormRequest =
        LocalCouncil.builder()
            .description(FormFields.VALUE_TOO_LONG)
            .welshDescription(FormFields.VALUE_TOO_LONG)
            .build();

    mockMvc
        .perform(
            post(URL)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(FormFields.DESCRIPTION, FormFields.VALUE_TOO_LONG)
                .param(FormFields.WELSH_DESCRIPTION, FormFields.VALUE_TOO_LONG))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_ERROR))
        .andExpect(flash().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(formRequestFlashAttributeCount(2))
        .andExpect(formRequestFlashAttributeHasFieldErrorCode(FormFields.DESCRIPTION, "Size"))
        .andExpect(
            formRequestFlashAttributeHasFieldErrorCode(FormFields.WELSH_DESCRIPTION, "Size"));
    verify(referenceDataServiceMock, never()).updateLocalCouncil(any(), any());
  }

  @Test
  public void
      submit_shouldDisplayLocalCouncilTemplateWithErrors_WhenThereAreEmptyValues_InTheClient()
          throws Exception {
    LocalCouncil expectedFormRequest = LocalCouncil.builder().description("").build();

    mockMvc
        .perform(
            post(URL).sessionAttr("user", dftAdminUserSignedIn).param(FormFields.DESCRIPTION, ""))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_ERROR))
        .andExpect(flash().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(formRequestFlashAttributeCount(1));
    verify(referenceDataServiceMock, never()).updateLocalCouncil(any(), any());
  }

  @Test
  public void
      submit_shouldDisplayLocalCouncilTemplateWithErrors_WhenThereAreValidationErrorsInTheServer()
          throws Exception {
    LocalCouncil expectedFormRequest =
        LocalCouncil.builder().description(FormFields.DESCRIPTION_VALUE_ORIG).build();

    CommonResponse commonResponse = new CommonResponse();
    Error error = new Error();
    List<ErrorErrors> errorErrors =
        Lists.newArrayList(
            new ErrorErrors()
                .field(FormFields.DESCRIPTION)
                .message("Size.localCouncil.description"));
    error.setErrors(errorErrors);
    commonResponse.setError(error);

    doThrow(new BadRequestException(commonResponse))
        .when(referenceDataServiceMock)
        .updateLocalCouncil(LOCAL_COUNCIL_SHORT_CODE, expectedFormRequest);

    mockMvc
        .perform(
            post(URL)
                .sessionAttr("user", dftAdminUserSignedIn)
                .param(FormFields.DESCRIPTION, FormFields.DESCRIPTION_VALUE_ORIG))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_ERROR))
        .andExpect(flash().attribute(MODEL_FORM_REQUEST, expectedFormRequest))
        .andExpect(formRequestFlashAttributeCount(1))
        .andExpect(
            formRequestFlashAttributeHasFieldErrorCode(
                FormFields.DESCRIPTION, "Size.localCouncil.description"));

    verify(referenceDataServiceMock, times(1))
        .updateLocalCouncil(LOCAL_COUNCIL_SHORT_CODE, expectedFormRequest);
  }
}
