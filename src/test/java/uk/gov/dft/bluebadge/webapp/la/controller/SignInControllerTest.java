package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.AuthServerConnectionException;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.InvalidEmailFormatException;

public class SignInControllerTest {

  private MockMvc mockMvc;
  private SignInController controller = new SignInController();;

  @Before
  public void setup() {
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void signIn_whenNoSignInException_thenNoErrors() throws Exception {
    mockMvc
        .perform(get("/sign-in"))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("errorSummary", Matchers.nullValue()));
  }

  @Test
  public void signIn_whenSignOut_thenHasSignOutMsg() throws Exception {
    mockMvc
        .perform(get("/sign-in").param("logout", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("errorSummary", Matchers.notNullValue()))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty(
                        "title", Matchers.equalTo("info.form.global.signedOut.title"))))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty("description", Matchers.equalTo("empty"))));
  }

  @Test
  public void signIn_whenBadCredentialsException_thenBadCredentialsError() throws Exception {

    mockMvc
        .perform(
            get("/sign-in")
                .param("error", "")
                .sessionAttr("SPRING_SECURITY_LAST_EXCEPTION", new BadCredentialsException("test")))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("errorSummary", Matchers.notNullValue()))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty("title", Matchers.equalTo("error.form.summary.title"))))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty(
                        "description",
                        Matchers.equalTo("error.form.global.accessDenied.description"))));
  }

  @Test
  public void signIn_whenLockedOutException_thenLockedOutError() throws Exception {

    mockMvc
        .perform(
            get("/sign-in")
                .param("error", "")
                .sessionAttr("SPRING_SECURITY_LAST_EXCEPTION", new LockedException("test")))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("errorSummary", Matchers.notNullValue()))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty(
                        "title", Matchers.equalTo("error.form.field.signin.locked.title"))))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty(
                        "description",
                        Matchers.equalTo("error.form.field.signin.locked.description"))));
  }

  @Test
  public void signIn_whenAuthServerConnectionException_thenConnectionFailureError()
      throws Exception {

    mockMvc
        .perform(
            get("/sign-in")
                .param("error", "")
                .sessionAttr(
                    "SPRING_SECURITY_LAST_EXCEPTION", new AuthServerConnectionException("test")))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("errorSummary", Matchers.notNullValue()))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty(
                        "title",
                        Matchers.equalTo("error.form.field.signin.connection.failure.title"))))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty(
                        "description",
                        Matchers.equalTo(
                            "error.form.field.signin.connection.failure.description"))));
  }

  @Test
  public void signIn_whenInvalidEmailFormatException_thenInvalidEmailErrorMsg() throws Exception {
    mockMvc
        .perform(
            get("/sign-in")
                .param("error", "")
                .sessionAttr(
                    "SPRING_SECURITY_LAST_EXCEPTION", new InvalidEmailFormatException("test")))
        .andExpect(status().isOk())
        .andExpect(view().name("sign-in"))
        .andExpect(model().attribute("errorSummary", Matchers.notNullValue()))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty("title", Matchers.equalTo("error.form.summary.title"))))
        .andExpect(
            model()
                .attribute(
                    "errorSummary",
                    Matchers.hasProperty(
                        "description", Matchers.equalTo("error.form.field.signin.email.invalid"))));
  }
}
