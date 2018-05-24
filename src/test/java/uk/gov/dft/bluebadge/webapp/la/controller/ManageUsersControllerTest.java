package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.*;
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
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

public class ManageUsersControllerTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String NAME = "joeblogs@joe.com";
  private static final int ROLE_ID = 1;

  private MockMvc mockMvc;

  @Mock private UserService userServiceMock;

  private ManageUsersController controller;

  // Test Data
  final int LOCAL_AUTHORITY = 1;
  private User userSignedIn;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new ManageUsersControllerImpl(userServiceMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    userSignedIn =
        new User()
            .name("Joe")
            .id(1)
            .emailAddress("joe.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY);
  }

  @Test
  public void showManageUser_shouldDisplaySignInTemplate_WhenUserIsNotSignedIn() throws Exception {
    mockMvc
        .perform(get("/manage-users"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/sign-in"));
  }

  @Test
  public void
      showManageUsers_shouldDisplayManagerUsersTemplateWithUsersFromTheLocalAuthorityOfTheUserSignedIn_WhenThereAreUsers()
          throws Exception {
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

    when(userServiceMock.find(userSignedIn.getLocalAuthorityId())).thenReturn(users);

    mockMvc
        .perform(get("/manage-users").sessionAttr("user", userSignedIn))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("users", users));
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY);
  }
}
