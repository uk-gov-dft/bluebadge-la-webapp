package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SetPasswordControllerTests extends MockMVCWithSecurityTests {

  @Test
  public void givenNoAuth_whenGetSetPasswordRequest_thenSuccess() throws Exception {
    mockMvc.perform(get("/set***REMOVED***/{uuid}", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andExpect(view().name("set***REMOVED***"))
    ;
  }

  @Test
  public void givenNoAuth_whenPostSetPasswordRequest_thenSuccess() throws Exception {
    mockMvc.perform(
          post("/set***REMOVED***/{uuid}", UUID.randomUUID().toString())
              .param(" ***REMOVED***)
              .param(" ***REMOVED***)
              .with(csrf())
        )
        .andExpect(redirectedUrl("/"));
  }
}
