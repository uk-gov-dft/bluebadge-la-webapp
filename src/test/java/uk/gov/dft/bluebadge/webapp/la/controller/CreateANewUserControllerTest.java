package uk.gov.dft.bluebadge.webapp.la.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserData;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.CreateANewUserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CreateANewUserControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String NAME = "joeblogs@joe.com";
  private static final int ROLE_ID = 1;
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";

  private MockMvc mockMvc;

  @Mock private UserService userServiceMock;

  private CreateANewUserController controller;

  private final SignInFormRequest emptySignInFormRequest = new SignInFormRequest(null, null);

  // Test Data
  final int LOCAL_AUTHORITY = 1;
  private User userSignedIn;
  private User user;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new CreateANewUserControllerImpl(userServiceMock, new CreateANewUserFormRequestToUser());

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    userSignedIn =
        new User()
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
        .perform(get("/manage-users/create-a-new-user").sessionAttr("user", userSignedIn))
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
                .sessionAttr("user", userSignedIn)
                .param("emailAddress", EMAIL)
                .param("name", NAME))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/manage-users"));
    verify(userServiceMock, times(1)).create(user);
  }

  @Test
  public void createANewUser_shouldDisplayCreateANewUserWithError_WhenThereIsAnUnexpectedException()
      throws Exception {
    when(userServiceMock.create(user)).thenThrow(new Exception());
    mockMvc
        .perform(
            post("/manage-users/create-a-new-user")
                .sessionAttr("user", userSignedIn)
                .param("emailAddress", EMAIL)
                .param("name", NAME))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users/create-a-new-user"))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    new ErrorViewModel("general error creating user", "error in creating user")));
  }
}
