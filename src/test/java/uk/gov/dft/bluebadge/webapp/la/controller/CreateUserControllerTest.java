package uk.gov.dft.bluebadge.webapp.la.controller;

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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
import uk.gov.dft.bluebadge.webapp.la.service.UserService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class CreateUserControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String NAME = "joeblogs@joe.com";
  private static final String NAME_WRONG_FORMAT = "111";
  private static final String ROLE_NAME = Role.LA_ADMIN.name();
  private static final int ROLE_ID = 2;
  private static final String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  public static final String ERROR_IN_EMAIL_ADDRESS = "error in emailAddress";
  public static final String ERROR_IN_NAME = "error in name";
  public static final String ERROR_NOT_BLANK = "NotBlank";

  private MockMvc mockMvc;

  @Mock private UserService userServiceMock;
  @Mock private SecurityUtils securityUtilsMock;
  @Mock private ReferenceDataService referenceDataServiceMock;

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
            referenceDataServiceMock);

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
    mockMvc
        .perform(
            post("/manage-users/create-user")
                .sessionAttr("user", userDataSignedIn)
                .param("emailAddress", "")
                .param("name", "")
                .param("roleName", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users/create-user"))
        .andExpect(model().errorCount(3))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", "emailAddress", ERROR_NOT_BLANK))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "name", ERROR_NOT_BLANK))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "role", "NotNull"));

    verifyZeroInteractions(userServiceMock);
  }
}
