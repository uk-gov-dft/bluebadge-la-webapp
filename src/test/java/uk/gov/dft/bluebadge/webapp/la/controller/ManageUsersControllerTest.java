package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserData;
import uk.gov.dft.bluebadge.model.usermanagement.UsersData;
import uk.gov.dft.bluebadge.model.usermanagement.UsersResponse;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

public class ManageUsersControllerTest {

  private MockMvc mockMvc;

  @Mock private UserService userServiceMock;
  @Mock private SecurityUtils securityUtils;

  private ManageUsersController controller;

  // Test Data
  final int LOCAL_AUTHORITY = 1;
  final String NAME_JANE = "Jane";
  final String NAME_NOT_FOUND = "NotFound";

  private UserData userDataSignedIn;
  private User userSignedIn;
  private User userJane;
  private User user3;
  private List<User> allUsers;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new ManageUsersController(userServiceMock, securityUtils);

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

    userDataSignedIn =
        new UserData()
            .name("Joe")
            .id(1)
            .emailAddress("joe.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY);

    userJane =
        new User()
            .name(NAME_JANE)
            .id(2)
            .emailAddress("jane.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY);
    user3 =
        new User()
            .name("Fred")
            .id(3)
            .emailAddress("jfred.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY);

    allUsers = Arrays.asList(userSignedIn, userJane, user3);
    when(userServiceMock.find(userSignedIn.getLocalAuthorityId()))
        .thenReturn(new UsersResponse().data(new UsersData().users(allUsers)));
  }

  @Test
  public void
      showManageUsers_shouldDisplayManagerUsersTemplateWithUsersFromTheLocalAuthorityOfTheUserSignedIn_WhenSearchParamIsEmptyAndThereAreUsers()
          throws Exception {
    mockMvc
        .perform(get("/manage-users"))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("search", ""))
        .andExpect(model().attribute("users", allUsers))
        .andExpect(model().attribute("allUsersSize", 3));
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY);
  }

  @Test
  public void
      showManageUsers_shouldDisplayManagerUsersTemplateWithUsersFilteredBySearchTermAndFromTheLocalAuthorityOfTheUserSignedIn_WhenSearchParamIsNonEmptyAndThereAreUsers()
          throws Exception {
    List<User> users = Lists.newArrayList(userJane);
    when(userServiceMock.find(userSignedIn.getLocalAuthorityId(), NAME_JANE))
        .thenReturn(new UsersResponse().data(new UsersData().users(users)));
    mockMvc
        .perform(
            get("/manage-users").param("search", NAME_JANE))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("search", NAME_JANE))
        .andExpect(model().attribute("users", users))
        .andExpect(model().attribute("allUsersSize", 3))
        .andExpect(model().attribute("searchCount", 1));
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY);
  }

  @Test
  public void
      showManageUsers_shouldDisplayManagerUsersTemplateWithNoUsers_WhenSearchParamIsNonEmptyAndThereNoAreUsers()
          throws Exception {
    List<User> users = Lists.newArrayList();
    when(userServiceMock.find(userSignedIn.getLocalAuthorityId(), NAME_NOT_FOUND))
        .thenReturn(new UsersResponse().data(new UsersData().users(users)));
    mockMvc
        .perform(
            get("/manage-users")
                .param("search", NAME_NOT_FOUND))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-users"))
        .andExpect(model().attribute("search", NAME_NOT_FOUND))
        .andExpect(model().attribute("users", users))
        .andExpect(model().attribute("allUsersSize", 3))
        .andExpect(model().attribute("searchCount", 0));
    verify(userServiceMock, times(1)).find(LOCAL_AUTHORITY);
  }
}
