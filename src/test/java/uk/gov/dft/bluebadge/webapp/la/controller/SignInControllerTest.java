package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.model.usermanagement.UserData;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.SignInService;

public class SignInControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String PASSWORD = "password";

  private MockMvc mockMvc;

  @Mock private SignInService signInService;

  private SignInController controller;

  private final SignInFormRequest emptySignInFormRequest = new SignInFormRequest(null, null);

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new SignInControllerImpl(signInService);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void showSignIn_shouldDisplaySignInPage() throws Exception {
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
  public void showSignIn_shouldDisplayHomePage_WhenUserIsSignedIn() throws Exception {
    UserData user = new UserData().emailAddress("joeblogs");
    mockMvc
        .perform(get("/sign-in").sessionAttr("user", user))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/"));
  }

  @Test
  public void signIn_shouldRedirectToHomePage_WhenSignInIsSuccessful() throws Exception {
    when(signInService.signIn(EMAIL))
        .thenReturn(Optional.of(new UserResponse().data(new UserData().emailAddress(EMAIL))));
    mockMvc
        .perform(post("/sign-in").param("email", EMAIL).param("password", PASSWORD))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/"));
  }

  @Test
  public void
      signIn_shouldDisplaySignInTemplateAndShowAccessDeniedMessageAndHttpStatusIsOK_WhenSignInIsNotSuccessful()
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
                            "error.form.global.accessDenied.title",
                            "error.form.global.accessDenied.description"))));
  }

  @Test
  public void
      signIn_shouldDisplaySignInTemplateWithValidationErrorMessageForEmailAndPasswordAndHttpStatusIsOK_WhenEmailAndPasswordAreEmpty()
          throws Exception {
    mockMvc
        .perform(post("/sign-in").param("email", "").param(" ***REMOVED***))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().errorCount(2))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "email", "NotEmpty"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", " ***REMOVED***));
  }

  @Test
  public void
      signIn_shouldDisplaySignInTemplateWithErrorMessageForEmailAndHttpStatusIsOK_WhenEmailIsWrongFormat()
          throws Exception {
    mockMvc
        .perform(post("/sign-in").param("email", EMAIL_WRONG_FORMAT).param("password", PASSWORD))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "email", "Email"));
  }

  @Test
  public void signIn_shouldDisplaySignInTemplateWithServerErrorMessage_WhenThereIsAServerError()
      throws Exception {
    when(signInService.signIn(EMAIL))
        .thenThrow(new Exception("Exception", new Exception("Cause Exception")));

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
                            "error.form.global.serverError.title",
                            "error.form.global.serverError.description"))));
  }

  @Test
  public void showServerError_shouldDisplayServerError() throws Exception {
    mockMvc
        .perform(get("/server-error"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("formRequest", emptySignInFormRequest))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    is(
                        new ErrorViewModel(
                            "error.form.global.serverError.title",
                            "error.form.global.serverError.description"))));
  }

  @Test
  public void showAccessDenied_shouldDisplayAccessDenied() throws Exception {
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
                            "error.form.global.accessDenied.title",
                            "error.form.global.accessDenied.description"))));
  }

  @Test
  public void showExpiredSession_shouldDisplayExpiredSession() throws Exception {
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
                            "error.form.global.expiredSession.title",
                            "error.form.global.expiredSession.description"))));
  }

  @Test
  public void signOut_shouldDisplaySignInPage_WhenSignOutAndUserWasSignedIn() throws Exception {
    UserData user = new UserData().emailAddress("joeblogs");
    mockMvc
        .perform(get("/sign-out").sessionAttr("user", user))
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
}
