package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.common.collect.Lists;

import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.BBPrincipal;
import uk.gov.dft.bluebadge.common.util.TestBBPrincipal;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.UserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.UserFormValidator;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class CreateUserControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String NAME = "joeblogs@joe.com";
  private static final String NAME_WRONG_FORMAT = "111";
  private static final String ROLE_NAME = Role.LA_ADMIN.name();
  private static final String DFT_ROLE_NAME = Role.DFT_ADMIN.name();
  private static final int ROLE_ID = 2;
  private static final int DFT_ROLE_ID = 1;
  private static final String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  public static final String ERROR_IN_EMAIL_ADDRESS = "error in emailAddress";
  public static final String ERROR_IN_NAME = "error in name";
  public static final String ERROR_NOT_BLANK = "NotBlank";

  private MockMvc mockMvc;

  @Mock private UserService userServiceMock;
  @Mock private SecurityUtils securityUtilsMock;
  @Mock private ReferenceDataService referenceDataServiceMock;
  @Mock private UserFormValidator userValidator;

  private CreateUserController controller;

  // Test Data
  private BBPrincipal userDataSignedIn;
  private User user;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new CreateUserController(
            userServiceMock,
            new UserFormRequestToUser(),
            securityUtilsMock,
            referenceDataServiceMock,
            userValidator);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    userDataSignedIn =
        TestBBPrincipal.user()
            .emailAddress("joe.blogs@email.com")
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .build();

    when(securityUtilsMock.getCurrentAuth()).thenReturn(userDataSignedIn);

    user =
        User.builder()
            .emailAddress(EMAIL)
            .name(NAME)
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .roleId(ROLE_ID)
            .build();
  }

  @Test
  public void showCreateANewUser_shouldDisplayCreateANewUserTemplate_WhenUserIsSignedIn()
      throws Exception {
    mockMvc
        .perform(get("/manage-users/create-user"))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users/create-user"));
  }

  @Test
  public void
      createUser_shouldCreateUserAndRedirectToManageUserTemplate_WhenThereAreNoValidationError()
          throws Exception {
    User user =
        User.builder()
            .emailAddress(EMAIL)
            .name(NAME)
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .roleId(ROLE_ID)
            .build();

    UserFormRequest form = new UserFormRequest();
    form.setEmailAddress(EMAIL);
    form.setName(NAME);
    form.setRole(Role.valueOf(ROLE_NAME));
    doNothing().when(userValidator).validate(form);
    
    when(securityUtilsMock.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(false);
    when(userServiceMock.create(user)).thenReturn(user);
    mockMvc
        .perform(
            post("/manage-users/create-user")
                .param("emailAddress", EMAIL)
                .param("name", NAME)
                .param("role", ROLE_NAME))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/manage-users"));
    verify(userServiceMock, times(1)).create(user);
  }

  @Test
  public void
      createUser_shouldDisplayCreateUserTemplateWithValidationErrors_WhenThereAreValidationErrors()
          throws Exception {
    user.setEmailAddress(EMAIL_WRONG_FORMAT);
    user.setName(NAME_WRONG_FORMAT);

    ErrorErrors emailError =
        new ErrorErrors().field("emailAddress").message(ERROR_IN_EMAIL_ADDRESS);
    ErrorErrors nameError = new ErrorErrors().field("name").message(ERROR_IN_NAME);

    CommonResponse commonResponse = new CommonResponse();
    commonResponse.setError(new Error().errors(Lists.newArrayList(emailError, nameError)));
    when(userServiceMock.create(user)).thenThrow(new BadRequestException(commonResponse));

    UserFormRequest form = new UserFormRequest();
    form.setEmailAddress(EMAIL_WRONG_FORMAT);
    form.setName(NAME_WRONG_FORMAT);
    form.setRole(Role.valueOf(ROLE_NAME));
    doNothing().when(userValidator).validate(form);

    mockMvc
        .perform(
            post("/manage-users/create-user")
                .sessionAttr("user", userDataSignedIn)
                .param("emailAddress", EMAIL_WRONG_FORMAT)
                .param("name", NAME_WRONG_FORMAT)
                .param("role", ROLE_NAME))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users/create-user"))
        .andExpect(model().errorCount(2))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", "emailAddress", ERROR_IN_EMAIL_ADDRESS))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "name", ERROR_IN_NAME));

    verify(userServiceMock, times(1)).create(user);
  }

  @Test
  public void createUser_shouldDisplayCreateUserTemplateWithValidationErrors_WhenNoFieldsPopulated()
      throws Exception {
    user.setEmailAddress("");
    user.setName("");
    user.setRoleName("");

    ErrorErrors emailError = new ErrorErrors().field("emailAddress").message(ERROR_NOT_BLANK);
    ErrorErrors nameError = new ErrorErrors().field("name").message(ERROR_NOT_BLANK);
    ErrorErrors roleError = new ErrorErrors().field("roleName").message(ERROR_NOT_BLANK);

    CommonResponse commonResponse = new CommonResponse();
    commonResponse.setError(
        new Error().errors(Lists.newArrayList(emailError, nameError, roleError)));
    when(userServiceMock.create(user)).thenThrow(new BadRequestException(commonResponse));

    UserFormRequest form = new UserFormRequest();
    form.setEmailAddress("");
    form.setName("");
    form.setRole(null);
    doNothing().when(userValidator).validate(form);
    when(securityUtilsMock.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(false);

    mockMvc
        .perform(
            post("/manage-users/create-user")
                .sessionAttr("user", userDataSignedIn)
                .param("emailAddress", "")
                .param("name", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users/create-user"))
        .andExpect(model().errorCount(3))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", "emailAddress", ERROR_NOT_BLANK))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "name", ERROR_NOT_BLANK))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "role", "NotNull"));

    verifyZeroInteractions(userServiceMock);
  }


  @Test
  public void createUser_shouldDisplayCreateUserTemplateWithValidationErrors_WhenLoggedAsDfTAdminAndLANotPopulatedForNonDfTUser()
      throws Exception {

	ErrorErrors laError = new ErrorErrors().field("localAuthorityShortCode").message(ERROR_NOT_BLANK);

    CommonResponse commonResponse = new CommonResponse();
    commonResponse.setError(new Error().errors(Lists.newArrayList(laError)));
    
    UserFormRequest form = new UserFormRequest();
    form.setEmailAddress(EMAIL);
    form.setName(NAME);
    form.setRole(Role.valueOf(ROLE_NAME));
    form.setLocalAuthorityShortCode(null);
    doThrow(new BadRequestException(commonResponse)).when(userValidator).validate(form);
    when(securityUtilsMock.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(true);

    mockMvc
        .perform(
            post("/manage-users/create-user")
                .sessionAttr("user", userDataSignedIn)
                .param("emailAddress", EMAIL)
                .param("name", NAME)
                .param("role", ROLE_NAME))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users/create-user"))
        .andExpect(model().errorCount(1))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", "localAuthorityShortCode", ERROR_NOT_BLANK));

    verifyZeroInteractions(userServiceMock);
  }


  @Test
  public void shouldCreateDfTAdminUserAndRedirectToManageUserTemplate()
      throws Exception {
    
	    User user =
	            User.builder()
	                .emailAddress(EMAIL)
	                .name(NAME)
	                .localAuthorityShortCode(null)
	                .roleId(DFT_ROLE_ID)
	                .build();

	UserFormRequest form = new UserFormRequest();
    form.setEmailAddress(EMAIL);
    form.setName(NAME);
    form.setRole(Role.valueOf(DFT_ROLE_NAME));
    form.setLocalAuthorityShortCode(null);

    doNothing().when(userValidator).validate(form);
    when(securityUtilsMock.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(true);

    mockMvc
        .perform(
            post("/manage-users/create-user")
                .sessionAttr("user", userDataSignedIn)
                .param("emailAddress", EMAIL)
                .param("name", NAME)
                .param("role", DFT_ROLE_NAME))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/manage-users"));

    verify(userServiceMock, times(1)).create(user);
  }
}
