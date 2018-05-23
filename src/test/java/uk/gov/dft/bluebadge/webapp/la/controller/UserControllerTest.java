package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.CreateANewUserRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralServiceException;
import uk.gov.dft.bluebadge.webapp.la.service.SignInService;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

public class UserControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String PASSWORD = "password";

  private MockMvc mockMvc;

  @Mock private UserService userService;
  @Mock private SignInService signInService;

  private UserController controller;

  private final SignInFormRequest emptySignInFormRequest = new SignInFormRequest(null, null);

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new UserControllerImpl(userService, signInService, new CreateANewUserRequestToUser());

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void shouldDisplaySignInPage() throws Exception {
    mockMvc
        .perform(get("/sign-in"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(
            model()
                .attribute(
                    "formRequest",
                    allOf(
                        hasProperty("email", isEmptyOrNullString()),
                        hasProperty("password", isEmptyOrNullString()))));
  }

  @Test
  public void shouldDisplayHomePage_WhenUserIsSignedIn() throws Exception {
    User user = new User().emailAddress("joeblogs");
    mockMvc
        .perform(get("/sign-in").sessionAttr("user", user))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/"));
  }

  @Test
  public void shouldRedirectToHomePageWithEmail_WhenSignInIsSuccessful() throws Exception {
    when(signInService.signIn(EMAIL)).thenReturn(Optional.of(new User().emailAddress(EMAIL)));
    mockMvc
        .perform(post("/sign-in").param("email", EMAIL).param("password", PASSWORD))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/"));
  }

  @Ignore
  @Test
  public void
      shouldDisplaySignInTemplateAndShowAccessDeniedMessageAndHttpStatusIsOK_WhenSignInIsNotSuccessful()
          throws Exception {
    when(signInService.signIn(EMAIL)).thenReturn(Optional.empty());
    mockMvc
        .perform(post("/sign-in").param("email", EMAIL).param("password", PASSWORD))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    is(
                        new ErrorViewModel(
                            "Access Denied",
                            "You've entered an incorrect email address or password"))));
  }

  @Ignore
  @Test
  public void
      shouldDisplaySignInTemplateWithErrorMessageForEmailAndPasswordAndHttpStatusIsOK_WhenEmailAndPasswordAreEmpty()
          throws Exception {
    mockMvc
        .perform(post("/sign-in").param("email", "").param(" ***REMOVED***))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().errorCount(2))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "email", "NotEmpty"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", " ***REMOVED***));
  }

  @Ignore
  @Test
  public void
      shouldDisplaySignInTemplateWithErrorMessageForEmailAndHttpStatusIsOK_WhenEmailIsWrongFormat()
          throws Exception {
    mockMvc
        .perform(post("/sign-in").param("email", EMAIL_WRONG_FORMAT).param("password", PASSWORD))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "email", "Email"));
  }

  @Ignore
  @Test
  public void shouldDisplaySignInTemplateWithServerErrorMessage_WhenThereIsAServerError()
      throws Exception {
    when(signInService.signIn(EMAIL))
        .thenThrow(
            new GeneralServiceException(
                "General Service Exception", new Exception("Cause Exception")));

    mockMvc
        .perform(post("/sign-in").param("email", EMAIL).param("password", PASSWORD))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(
            model()
                .attribute(
                    "errorSummary", is(new ErrorViewModel("Can't sign in", "Please try again."))));
  }

  @Ignore
  @Test
  public void shouldDisplayServerError() throws Exception {
    mockMvc
        .perform(get("/server-error"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("formRequest", emptySignInFormRequest))
        .andExpect(
            model()
                .attribute(
                    "errorSummary", is(new ErrorViewModel("Can't sign in", "Please try again."))));
  }

  @Ignore
  @Test
  public void shouldDisplayAccessDenied() throws Exception {
    mockMvc
        .perform(get("/access-denied"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("formRequest", emptySignInFormRequest))
        .andExpect(model().attribute("formRequest", emptySignInFormRequest))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    is(
                        new ErrorViewModel(
                            "Access Denied",
                            "You've entered an incorrect email address or password"))));
  }

  @Ignore
  @Test
  public void shouldDisplayExpiredSession() throws Exception {
    mockMvc
        .perform(get("/expired-session"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("formRequest", emptySignInFormRequest))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    is(
                        new ErrorViewModel(
                            "You've been signed out",
                            "You were inactive for 2 hours so we've signed you out to secure your account"))));
  }

  @Test
  public void shouldDisplaySignInPage_WhenSignOutAndUserWasSignedIn() throws Exception {
    mockMvc
        .perform(get("/sign-out").sessionAttr("email", "joeblogs"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/sign-in"));
  }

  @Test
  public void shouldDisplaySignInPage_WhenSignOutAndUserWasNotSignedIn() throws Exception {
    mockMvc
        .perform(get("/sign-out"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/sign-in"));
  }

  @Test
  public void
      shouldDisplayManagerUsersTemplateWithUsersFromTheLocalAuthorityOfTheUserSignedIn_WhenThereAreUsers()
          throws Exception {
    final int LOCAL_AUTHORITY = 1;
    User userSignedIn =
        new User()
            .name("Joe")
            .id(1)
            .emailAddress("joe.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY);
    User user2 =
        new User()
            .name("Jane")
            .id(2)
            .emailAddress("jane.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY);
    User user3 =
        new User()
            .name("Fred")
            .id(3)
            .emailAddress("jfred.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY);

    List<User> users = Arrays.asList(userSignedIn, user2, user3);

    when(userService.findAll(userSignedIn.getLocalAuthorityId())).thenReturn(users);

    mockMvc
        .perform(get("/manage-users").sessionAttr("user", userSignedIn))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("users", users));
    //verify(userService.findAll(LOCAL_AUTHORITY), times(1));
  }
}
