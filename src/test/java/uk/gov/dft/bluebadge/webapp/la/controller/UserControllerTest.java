package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.CreateANewUserFormequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SignInFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

public class UserControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String PASSWORD = "password";

  private MockMvc mockMvc;

  @Mock private UserService userService;

  private UserController controller;

  private final SignInFormRequest emptySignInFormRequest = new SignInFormRequest(null, null);

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new UserControllerImpl(userService, new CreateANewUserFormequestToUser());

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
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
    // verify(userService.findAll(LOCAL_AUTHORITY), times(1));
  }
}
