package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

public class ManageUsersControllerTest {

  private MockMvc mockMvc;

  @Mock private UserService userServiceMock;
  @Mock private SecurityUtils securityUtilsMock;

  private ManageUsersController controller;

  // Test Data
  private final static int LOCAL_AUTHORITY_ID = 1;
  private final static String NAME_JANE = "Jane";
  private final static String NAME_NOT_FOUND = "NotFound";
  private final static Integer ROLE_ID = 1;

  private uk.gov.dft.bluebadge.common.security.model.User userDataSignedIn;
  private uk.gov.dft.bluebadge.common.security.model.User userSignedIn;
  private User userJane;
  private User user2;
  private User user3;
  private List<User> allUsers;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new ManageUsersController(userServiceMock, securityUtilsMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    userSignedIn =
        uk.gov.dft.bluebadge.common.security.model.User.builder()
            .name("Joe")
            .id(1)
            .emailAddress("joe.blogs@email.com")
            .localAuthority(LocalAuthority.builder().id(LOCAL_AUTHORITY_ID).build())
            .roleId(ROLE_ID)
            .build();

    userDataSignedIn =
        uk.gov.dft.bluebadge.common.security.model.User.builder()
            .name("Joe")
            .id(1)
            .emailAddress("joe.blogs@email.com")
            .localAuthority(LocalAuthority.builder().id(LOCAL_AUTHORITY_ID).build())
            .build();

    when(securityUtilsMock.getCurrentUserDetails()).thenReturn(userDataSignedIn);

    userJane =
        new User()
            .name(NAME_JANE)
            .id(2)
            .emailAddress("jane.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY_ID);
    user2 =
        new User()
            .name("Joe")
            .id(1)
            .emailAddress("joe.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY_ID)
            .roleId(ROLE_ID);

    user3 =
        new User()
            .name("Fred")
            .id(3)
            .emailAddress("jfred.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY_ID);

    allUsers = Arrays.asList(user2, userJane, user3);
    List<User> users = new ArrayList<>(allUsers);
    when(userServiceMock.find(userSignedIn.getLocalAuthority().getId())).thenReturn(users);
  }

  @Test
  public void showManageUsers_shouldDisplayUsersFromCurrentUserLocalAuthority_WhenSearchIsEmpty()
      throws Exception {
    mockMvc
        .perform(get("/manage-users"))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("search", ""))
        .andExpect(model().attribute("users", allUsers))
        .andExpect(model().attribute("allUsersSize", 3));
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY_ID);
  }

  @Test
  public void
      showManageUsers_shouldDisplayUsersFilteredFromCurrentUserLocalAuthority_WhenSearchIsNonEmpty()
          throws Exception {
    List<User> users = Lists.newArrayList(userJane);
    when(userServiceMock.find(userSignedIn.getLocalAuthority().getId(), NAME_JANE))
        .thenReturn(users);
    mockMvc
        .perform(get("/manage-users").param("search", NAME_JANE))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("search", NAME_JANE))
        .andExpect(model().attribute("users", users))
        .andExpect(model().attribute("allUsersSize", 3))
        .andExpect(model().attribute("searchCount", 1));
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY_ID);
  }

  @Test
  public void showManageUsers_shouldDisplayNoUsers_WhenSearchIsNonEmptyAndThereNoAreUsers()
      throws Exception {
    List<User> users = Lists.newArrayList();
    when(userServiceMock.find(userSignedIn.getLocalAuthority().getId(), NAME_NOT_FOUND))
        .thenReturn(users);
    mockMvc
        .perform(get("/manage-users").param("search", NAME_NOT_FOUND))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("search", NAME_NOT_FOUND))
        .andExpect(model().attribute("users", users))
        .andExpect(model().attribute("allUsersSize", 3))
        .andExpect(model().attribute("searchCount", 0));
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY_ID);
  }
}
