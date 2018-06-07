package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.client.usermanagement.api.UserManagementService;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UsersData;
import uk.gov.dft.bluebadge.model.usermanagement.UsersResponse;

public class UserServiceTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String PASSWORD = "password";

  @Mock private UserManagementService userManagementServiceMock;

  private UserService userService;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    userService = new UserServiceImpl(userManagementServiceMock);
  }

  @Test
  public void shouldReturnUsersInAlphabeticalAscendingOrder_WhenThereAreUsers() {
    final int LOCAL_AUTHORITY = 1;
    User user1 =
        new User()
            .id(1)
            .name("z")
            .localAuthorityId(LOCAL_AUTHORITY)
            .emailAddress("name-1@email.com");
    User user2 =
        new User()
            .id(2)
            .name("c")
            .localAuthorityId(LOCAL_AUTHORITY)
            .emailAddress("name-2@email.com");
    User user3 =
        new User()
            .id(3)
            .name("a")
            .localAuthorityId(LOCAL_AUTHORITY)
            .emailAddress("name-3@email.com");
    User user4 =
        new User()
            .id(4)
            .name("m")
            .localAuthorityId(LOCAL_AUTHORITY)
            .emailAddress("name-4@email.com");
    User user5 =
        new User()
            .id(5)
            .name("h")
            .localAuthorityId(LOCAL_AUTHORITY)
            .emailAddress("name-5@email.com");
    List<User> usersFromClient = Arrays.asList(user1, user2, user3, user4, user5);
    UsersData usersData = new UsersData().users(usersFromClient);
    usersData.setTotalItems(usersFromClient.size());
    when(userManagementServiceMock.getUsersForAuthority(LOCAL_AUTHORITY, ""))
        .thenReturn(new UsersResponse().data(usersData));
    List<User> users = userService.find(LOCAL_AUTHORITY).getData().getUsers();
    List<User> expectedUsers = Arrays.asList(user3, user2, user5, user4, user1);
    assertThat(users).isEqualTo(expectedUsers);
  }
}