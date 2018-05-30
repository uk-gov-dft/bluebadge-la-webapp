package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.model.usermanagement.*;
import uk.gov.dft.bluebadge.model.usermanagement.Error;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.CreateANewUserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

public class CreateANewUserControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String NAME = "joeblogs@joe.com";
  private static final String NAME_WRONG_FORMAT = "111";
  private static final int ROLE_ID = 1;
  private static final int LOCAL_AUTHORITY = 1;
  public static final String ERROR_IN_EMAIL_ADDRESS = "error in emailAddress";
  public static final String ERROR_IN_NAME = "error in name";

  private MockMvc mockMvc;

  @Mock private UserService userServiceMock;

  private CreateANewUserController controller;

  // Test Data
  private UserData userDataSignedIn;
  private User user;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new CreateANewUserControllerImpl(userServiceMock, new CreateANewUserFormRequestToUser());

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    userDataSignedIn =
        new UserData()
            .name("Joe")
            .id(1)
            .emailAddress("joe.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY);

    user =
        new User().emailAddress(EMAIL).name(NAME).localAuthorityId(LOCAL_AUTHORITY).roleId(ROLE_ID);
  }

  @Test
  public void showCreateANewUser_shouldDisplaySignInTemplate_WhenUserIsNotSignedIn()
      throws Exception {
    mockMvc
        .perform(get("/manage-users/create-a-new-user"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/sign-in"));
  }

  @Test
  public void showCreateANewUser_shouldDisplayCreateANewUserTemplate_WhenUserIsSignedIn()
      throws Exception {
    mockMvc
        .perform(get("/manage-users/create-a-new-user").sessionAttr("user", userDataSignedIn))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users/create-a-new-user"));
  }

  @Test
  public void createANewUser_shouldDisplaySignInTemplate_WhenUserIsNotSignedIn() throws Exception {
    mockMvc
        .perform(post("/manage-users/create-a-new-user"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/sign-in"));
  }

  @Test
  public void
      createANewUser_shouldCreateANewUserAndRedirectToManageUserTemplate_WhenThereAreNoValidationError()
          throws Exception {
    User user =
        new User().emailAddress(EMAIL).name(NAME).localAuthorityId(LOCAL_AUTHORITY).roleId(ROLE_ID);
    UserResponse userResponse =
        new UserResponse()
            .data(
                new UserData()
                    .emailAddress(EMAIL)
                    .name(NAME)
                    .localAuthorityId(LOCAL_AUTHORITY)
                    .roleId(ROLE_ID));
    when(userServiceMock.create(user)).thenReturn(userResponse);
    mockMvc
        .perform(
            post("/manage-users/create-a-new-user")
                .sessionAttr("user", userDataSignedIn)
                .param("emailAddress", EMAIL)
                .param("name", NAME))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/manage-users"));
    verify(userServiceMock, times(1)).create(user);
  }

  @Test
  public void
      createANewUser_shouldDisplayCreateANewUserTemplateWithValidationErrors_WhenThereAreValidationErrors()
          throws Exception {
    user.setEmailAddress(EMAIL_WRONG_FORMAT);
    user.setName(NAME_WRONG_FORMAT);
    ErrorErrors emailError =
        new ErrorErrors().field("emailAddress").message(ERROR_IN_EMAIL_ADDRESS);
    ErrorErrors nameError = new ErrorErrors().field("name").message(ERROR_IN_NAME);
    UserResponse userResponse = new UserResponse();
    userResponse.setError(new Error().errors(Lists.newArrayList(emailError, nameError)));
    when(userServiceMock.create(user)).thenReturn(userResponse);
    mockMvc
        .perform(
            post("/manage-users/create-a-new-user")
                .sessionAttr("user", userDataSignedIn)
                .param("emailAddress", EMAIL_WRONG_FORMAT)
                .param("name", NAME_WRONG_FORMAT))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users/create-a-new-user"))
        .andExpect(model().errorCount(2))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", "emailAddress", ERROR_IN_EMAIL_ADDRESS))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "name", ERROR_IN_NAME));

    verify(userServiceMock, times(1)).create(user);
  }

  @Test
  public void createANewUser_shouldDisplayCreateANewUserWithError_WhenThereIsAnUnexpectedException()
      throws Exception {
    when(userServiceMock.create(user)).thenThrow(new Exception());
    mockMvc
        .perform(
            post("/manage-users/create-a-new-user")
                .sessionAttr("user", userDataSignedIn)
                .param("emailAddress", EMAIL)
                .param("name", NAME))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users/create-a-new-user"))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    new ErrorViewModel(
                        "error.createUser.generalError.title",
                        "error.createUser.generalError.description")));
  }
}
