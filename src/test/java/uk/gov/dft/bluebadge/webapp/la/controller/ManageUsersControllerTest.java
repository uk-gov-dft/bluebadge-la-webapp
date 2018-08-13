package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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
  final String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  final UUID USER_UUID_1 = UUID.randomUUID();
  final UUID USER_UUID_2 = UUID.randomUUID();
  final UUID USER_UUID_3 = UUID.randomUUID();  private static final String NAME_JANE = "Jane";
  private static final String NAME_NOT_FOUND = "NotFound";
  private static final Integer ROLE_ID = 1;

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
            .localAuthority(LocalAuthority.builder().shortCode(LOCAL_AUTHORITY_SHORT_CODE).build())
            .roleId(ROLE_ID)
            .build();

    userDataSignedIn =
        uk.gov.dft.bluebadge.common.security.model.User.builder()
            .name("Joe")
            .id(1)
            .emailAddress("joe.blogs@email.com")
            .localAuthority(LocalAuthority.builder().shortCode(LOCAL_AUTHORITY_SHORT_CODE).build())
            .build();

    when(securityUtilsMock.getCurrentUserDetails()).thenReturn(userDataSignedIn);

    userJane =
        User.builder()
            .name(NAME_JANE)
            .uuid(USER_UUID_1)
            .emailAddress("jane.blogs@email.com")
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .build();
    user2 =
        User.builder()
            .name("Joe")
            .uuid(USER_UUID_2)
            .emailAddress("joe.blogs@email.com")
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .roleId(ROLE_ID)
            .build();

    user3 =
        User.builder()
            .name("Fred")
            .uuid(USER_UUID_3)
            .emailAddress("jfred.blogs@email.com")
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .build();

    allUsers = Arrays.asList(user2, userJane, user3);
    List<User> users = new ArrayList<>(allUsers);
    when(userServiceMock.find(userSignedIn.getLocalAuthority().getShortCode())).thenReturn(users);
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
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY_SHORT_CODE);
  }

  @Test
  public void
      showManageUsers_shouldDisplayUsersFilteredFromCurrentUserLocalAuthority_WhenSearchIsNonEmpty()
          throws Exception {
    List<User> users = Lists.newArrayList(userJane);
    when(userServiceMock.find(userSignedIn.getLocalAuthority().getShortCode(), NAME_JANE))
        .thenReturn(users);
    mockMvc
        .perform(get("/manage-users").param("search", NAME_JANE))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("search", NAME_JANE))
        .andExpect(model().attribute("users", users))
        .andExpect(model().attribute("allUsersSize", 3))
        .andExpect(model().attribute("searchCount", 1));
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY_SHORT_CODE);
  }

  @Test
  public void showManageUsers_shouldDisplayNoUsers_WhenSearchIsNonEmptyAndThereNoAreUsers()
      throws Exception {
    List<User> users = Lists.newArrayList();
    when(userServiceMock.find(userSignedIn.getLocalAuthority().getShortCode(), NAME_NOT_FOUND))
        .thenReturn(users);
    mockMvc
        .perform(get("/manage-users").param("search", NAME_NOT_FOUND))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("search", NAME_NOT_FOUND))
        .andExpect(model().attribute("users", users))
        .andExpect(model().attribute("allUsersSize", 3))
        .andExpect(model().attribute("searchCount", 0));
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY_SHORT_CODE);
  }
}
