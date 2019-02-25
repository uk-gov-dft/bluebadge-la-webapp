package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.dft.bluebadge.webapp.la.BaseSpringBootTest;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.AuthServerConnectionException;
import uk.gov.dft.bluebadge.webapp.la.security.exceptions.InvalidEmailFormatException;

@RunWith(SpringRunner.class)
public class SignInControllerTest extends BaseSpringBootTest {

  @Autowired
  private  WebApplicationContext webApplicationContext;;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(springSecurity())
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

  @Test
  public void signIn_whenAlreadySignedIn_thenRedirectToHome() throws Exception {
    mockMvc
        .perform(get("/sign-in").with(user("bob")))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/"));
  }
}
