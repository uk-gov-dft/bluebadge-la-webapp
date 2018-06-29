package uk.gov.dft.bluebadge.webapp.la.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.dft.bluebadge.webapp.la.controller.MockMVCWithSecurityTests;

@RunWith(SpringRunner.class)
public class SignInTests extends MockMVCWithSecurityTests{

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
        .andExpect(status().isForbidden());
  }

  @Test
  @SneakyThrows
  public void givenNoCSRF_whenLoginRequest_thenForbidden() {
    mockMvc
        .perform(
            post("/sign-in").param("username", VALID_USERNAME_1).param(USERNAME_1_PASSWORD, "xxx"))
        .andExpect(status().isForbidden());
  }
}
