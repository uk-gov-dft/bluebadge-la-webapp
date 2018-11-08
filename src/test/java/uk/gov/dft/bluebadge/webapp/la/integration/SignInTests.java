package uk.gov.dft.bluebadge.webapp.la.integration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.dft.bluebadge.webapp.la.BaseSpringBootTest;

@RunWith(SpringRunner.class)
public class SignInTests extends BaseSpringBootTest {

  private static final String VALID_USERNAME_1 = "abc@dft.gov.uk";
  private static final String USERNAME_1_PASSWORD = "password";

  @Autowired private WebApplicationContext wac;
  protected MockMvc mockMvc;

  @Before
  public void setupMockMVC() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
  }

  @Test
  @SneakyThrows
  public void givenNoCredentials_whenLoginFormRequest_thenSuccess() {
    mockMvc.perform(get("/sign-in")).andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  public void givenInvalidUsername_whenLoginRequest_thenRedirectBackToLogin() {
    mockMvc
        .perform(
            post("/sign-in")
                .param("username", "blahblahblah")
                .param(" ***REMOVED***)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/sign-in?error"));
  }

  @Test
  @SneakyThrows
  public void givenInvalidCredentials_whenLoginRequest_thenRedirectBackToLogin() {
    mockMvc
        .perform(
            post("/sign-in")
                .param("username", VALID_USERNAME_1)
                .param(" ***REMOVED***)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/sign-in?error"));
  }

  @Test
  @SneakyThrows
  public void givenInvalidCSRF_whenLoginRequest_thenForbidden() {
    mockMvc
        .perform(
            post("/sign-in")
                .param("username", VALID_USERNAME_1)
                .param(" ***REMOVED***)
                .with(SecurityMockMvcRequestPostProcessors.csrf().useInvalidToken()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/something-went-wrong"));
  }

  @Test
  @SneakyThrows
  public void givenNoCSRF_whenLoginRequest_thenForbidden() {
    mockMvc
        .perform(
            post("/sign-in").param("username", VALID_USERNAME_1).param(USERNAME_1_PASSWORD, "xxx"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/something-went-wrong"));
  }
}
