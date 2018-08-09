package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.SetPasswordApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;

public class UserServiceTest {

  private static final int ID = 1;
  private static final String EMAIL_ADDRESS = "email@email.com";
  private static final String NAME = "My name";
  private static final String UUID = "uuid";

  @SuppressWarnings("squid:S2068")
  private static final String PASSWORD = "lckxjvlkv";

  @Mock private UserManagementApiClient userManagementServiceMock;
  @Mock private SetPasswordApiClient setPasswordApiClientMock;

  private UserService userService;

  private User user;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    userService = new UserService(userManagementServiceMock, setPasswordApiClientMock);

    user = new User().id(ID).emailAddress(EMAIL_ADDRESS).name(NAME);
  }

  @Test
  public void find_ShouldReturnUsersInAlphabeticalAscendingOrder_WhenThereAreUsers() {
    final int LOCAL_AUTHORITY_ID = 1;
    User user1 =
        new User()
            .id(1)
            .name("z")
            .localAuthorityId(LOCAL_AUTHORITY_ID)
            .emailAddress("name-1@email.com");
    User user2 =
        new User()
            .id(2)
            .name("c")
            .localAuthorityId(LOCAL_AUTHORITY_ID)
            .emailAddress("name-2@email.com");
    User user3 =
        new User()
            .id(3)
            .name("a")
            .localAuthorityId(LOCAL_AUTHORITY_ID)
            .emailAddress("name-3@email.com");
    User user4 =
        new User()
            .id(4)
            .name("m")
            .localAuthorityId(LOCAL_AUTHORITY_ID)
            .emailAddress("name-4@email.com");
    User user5 =
        new User()
            .id(5)
            .name("h")
            .localAuthorityId(LOCAL_AUTHORITY_ID)
            .emailAddress("name-5@email.com");
    List<User> usersFromClient = Arrays.asList(user1, user2, user3, user4, user5);

    when(userManagementServiceMock.getUsersForAuthority(LOCAL_AUTHORITY_ID, ""))
        .thenReturn(usersFromClient);
    List<User> users = userService.find(LOCAL_AUTHORITY_ID);
    List<User> expectedUsers = Arrays.asList(user3, user2, user5, user4, user1);
    assertThat(users).isEqualTo(expectedUsers);
  }

  @Test
  public void find_ShouldReturnEmptyList_WhenThereAreNoUsers() {
    final int LOCAL_AUTHORITY = 1;
    List<User> usersFromClient = Arrays.asList();

    when(userManagementServiceMock.getUsersForAuthority(LOCAL_AUTHORITY, ""))
        .thenReturn(usersFromClient);
    List<User> users = userService.find(LOCAL_AUTHORITY);
    List<User> expectedUsers = Arrays.asList();
    assertThat(users).isEqualTo(expectedUsers);
  }

  @Test
  public void retrieve_ShouldReturnUser() {
    when(userManagementServiceMock.getById(ID)).thenReturn(user);
    User userRetrieved = userService.retrieve(ID);
    assertThat(userRetrieved).isEqualTo(user);
    verify(userManagementServiceMock).getById(ID);
  }

  @Test
  public void create_ShouldCreateAUser() {
    when(userManagementServiceMock.createUser(user)).thenReturn(user);
    User userCreated = userService.create(user);
    assertThat(userCreated).isEqualTo(user);
    verify(userManagementServiceMock).createUser(user);
  }

  @Test
  public void update_ShouldUpdateAUser() {
    when(userManagementServiceMock.updateUser(user)).thenReturn(user);
    User userUpdated = userService.update(user);
    assertThat(userUpdated).isEqualTo(user);
    verify(userManagementServiceMock).updateUser(user);
  }

  @Test
  public void delete_ShouldDeleteAUser() {
    userService.delete(ID);
    verify(userManagementServiceMock).deleteUser(ID);
  }

  @Test
  public void requestPasswordReset_ShouldDeleteAUser() {
    userService.requestPasswordReset(ID);
    verify(userManagementServiceMock).requestPasswordReset(ID);
  }

  @Test
  public void updatePassword_shouldUpdatePassword() {
    when(setPasswordApiClientMock.updatePassword(UUID, PASSWORD, PASSWORD)).thenReturn(user);
    User userUpdated = userService.updatePassword(UUID, PASSWORD, PASSWORD);
    assertThat(userUpdated).isEqualTo(user);
    verify(setPasswordApiClientMock).updatePassword(UUID, PASSWORD, PASSWORD);
  }
}
