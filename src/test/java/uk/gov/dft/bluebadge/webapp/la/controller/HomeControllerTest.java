package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;

public class HomeControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String PASSWORD = "password";

  private MockMvc mockMvc;

  private HomeController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new HomeControllerImpl();

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void showHome_shouldDisplayHomePageAndAddEmailAttribute_WhenUserIsSignedIn()
      throws Exception {
    User user = new User().emailAddress("joeBlogs");
    mockMvc
        .perform(get("/").sessionAttr("user", user))
        .andExpect(status().isOk())
        .andExpect(view().name("home"));
    // TODO: We should expect the name of the user printed out
  }

  @Test
  public void shouldRedirectToSignInPage_WhenUserIsNotSignedIn() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isFound()).andExpect(redirectedUrl("/sign-in"));
  }
}
