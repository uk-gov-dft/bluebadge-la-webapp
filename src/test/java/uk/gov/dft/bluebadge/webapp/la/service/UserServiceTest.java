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
  public void shouldReturnUsersInAlfabeticalAscendingOrder_WhenThereAreUsers() {
    User user1 = new User().id(1).name("z").localAuthorityId(1).emailAddress("name-1@email.com");
    User user2 = new User().id(2).name("c").localAuthorityId(1).emailAddress("name-2@email.com");
    User user3 = new User().id(3).name("a").localAuthorityId(1).emailAddress("name-3@email.com");
    User user4 = new User().id(4).name("m").localAuthorityId(1).emailAddress("name-4@email.com");
    User user5 = new User().id(5).name("h").localAuthorityId(1).emailAddress("name-5@email.com");
    List<User> usersFromClient = Arrays.asList(user1, user2, user3, user4, user5);
    when(userManagementServiceMock.getUsersForAuthority(1, ""))
        .thenReturn(new UsersResponse().data(new UsersData().users(usersFromClient)));
    List<User> users = userService.getUsers();
    List<User> expectedUsers = Arrays.asList(user3, user2, user5, user4, user1);
    assertThat(users).isEqualTo(expectedUsers);
  }
}
