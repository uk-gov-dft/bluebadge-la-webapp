package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.client.usermanagement.api.UserManagementService;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralServiceException;

public class UserControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String PASSWORD = "password";

  private MockMvc mockMvc;

  @Mock private UserManagementService service;

  private UserController controller;

  private final SignInFormRequest emptySignInFormRequest = new SignInFormRequest(null, null);

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new UserControllerImpl(service);

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
  public void shouldRedirectToHomePageWithEmail_WhenSignInIsSuccessful() throws Exception {
    when(service.checkUserExistsForEmail(EMAIL)).thenReturn(true);
    mockMvc
        .perform(post("/sign-in").param("email", EMAIL).param("password", PASSWORD))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/"));
  }

  @Test
  public void
      shouldDisplaySignInTemplateAndShowAccessDeniedMessageAndHttpStatusIsOK_WhenSignInIsNotSuccessful()
          throws Exception {
    when(service.checkUserExistsForEmail(EMAIL)).thenReturn(false);
    mockMvc
        .perform(post("/sign-in").param("email", EMAIL).param("password", PASSWORD))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("accessDenied", is(true)));
  }

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

  @Test
  public void shouldDisplaySignInTemplateWithServerErrorMessage_WhenThereIsAServerError()
      throws Exception {
    when(service.checkUserExistsForEmail(EMAIL))
        .thenThrow(
            new GeneralServiceException(
                "General Service Exception", new Exception("Cause Exception")));

    mockMvc
        .perform(post("/sign-in").param("email", EMAIL).param("password", PASSWORD))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("serverError", true));
  }

  @Test
  public void shouldDisplayServerError() throws Exception {
    mockMvc
        .perform(get("/server-error"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("formRequest", emptySignInFormRequest))
        .andExpect(model().attribute("serverError", true));
  }

  @Test
  public void shouldDisplayAccessDenied() throws Exception {
    mockMvc
        .perform(get("/access-denied"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("formRequest", emptySignInFormRequest))
        .andExpect(model().attribute("accessDenied", true));
  }

  @Test
  public void shouldDisplayExpiredSession() throws Exception {
    mockMvc
        .perform(get("/expired-session"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("formRequest", emptySignInFormRequest))
        .andExpect(model().attribute("expiredSession", true));
  }

  @Test
  public void shouldDisplaySignedOut() throws Exception {
    mockMvc
        .perform(get("/signed-out"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("formRequest", emptySignInFormRequest))
        .andExpect(model().attribute("signedOut", true));
  }
}
