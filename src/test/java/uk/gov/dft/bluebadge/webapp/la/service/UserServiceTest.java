package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.SetPasswordApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;

public class UserServiceTest {

  private static final String EMAIL_ADDRESS = "email@email.com";
  private static final String NAME = "My name";
  private static final UUID USER_UUID_1 = java.util.UUID.randomUUID();
  private static final UUID USER_UUID_2 = java.util.UUID.randomUUID();
  private static final UUID USER_UUID_3 = java.util.UUID.randomUUID();
  private static final UUID USER_UUID_4 = java.util.UUID.randomUUID();
  private static final UUID USER_UUID_5 = java.util.UUID.randomUUID();

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

    user = User.builder().uuid(USER_UUID_1).emailAddress(EMAIL_ADDRESS).name(NAME).build();
  }

  @Test
  public void find_ShouldReturnUsersInAlphabeticalAscendingOrder_WhenThereAreUsers() {
    final String LOCAL_AUTHORITY_ID = "BIRM";
    User user1 =
        User.builder()
            .uuid(USER_UUID_1)
            .name("z")
            .localAuthorityShortCode(LOCAL_AUTHORITY_ID)
            .emailAddress("name-1@email.com")
            .build();
    User user2 =
        User.builder()
            .uuid(USER_UUID_2)
            .name("c")
            .localAuthorityShortCode(LOCAL_AUTHORITY_ID)
            .emailAddress("name-2@email.com")
            .build();
    User user3 =
        User.builder()
            .uuid(USER_UUID_3)
            .name("a")
            .localAuthorityShortCode(LOCAL_AUTHORITY_ID)
            .emailAddress("name-3@email.com")
            .build();
    User user4 =
        User.builder()
            .uuid(USER_UUID_4)
            .name("m")
            .localAuthorityShortCode(LOCAL_AUTHORITY_ID)
            .emailAddress("name-4@email.com")
            .build();
    User user5 =
        User.builder()
            .uuid(USER_UUID_5)
            .name("h")
            .localAuthorityShortCode(LOCAL_AUTHORITY_ID)
            .emailAddress("name-5@email.com")
            .build();
    List<User> usersFromClient = Arrays.asList(user1, user2, user3, user4, user5);

    when(userManagementServiceMock.getUsersForAuthority(LOCAL_AUTHORITY_ID, ""))
        .thenReturn(usersFromClient);
    List<User> users = userService.find(LOCAL_AUTHORITY_ID);
    List<User> expectedUsers = Arrays.asList(user3, user2, user5, user4, user1);
    assertThat(users).isEqualTo(expectedUsers);
  }

  @Test
  public void find_ShouldReturnEmptyList_WhenThereAreNoUsers() {
    final String LOCAL_AUTHORITY = "BIRM";
    List<User> usersFromClient = Arrays.asList();

    when(userManagementServiceMock.getUsersForAuthority(LOCAL_AUTHORITY, ""))
        .thenReturn(usersFromClient);
    List<User> users = userService.find(LOCAL_AUTHORITY);
    List<User> expectedUsers = Arrays.asList();
    assertThat(users).isEqualTo(expectedUsers);
  }

  @Test
  public void retrieve_ShouldReturnUser() {
    when(userManagementServiceMock.getByUuid(USER_UUID_1)).thenReturn(user);
    User userRetrieved = userService.retrieve(USER_UUID_1);
    assertThat(userRetrieved).isEqualTo(user);
    verify(userManagementServiceMock).getByUuid(USER_UUID_1);
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
    userService.delete(USER_UUID_1);
    verify(userManagementServiceMock).deleteUser(USER_UUID_1);
  }

  @Test
  public void requestPasswordReset_ShouldDeleteAUser() {
    userService.requestPasswordReset(USER_UUID_1);
    verify(userManagementServiceMock).requestPasswordReset(USER_UUID_1);
  }

  @Test
  public void updatePassword_shouldUpdatePassword() {
    when(setPasswordApiClientMock.updatePassword(USER_UUID_1.toString(), PASSWORD, PASSWORD))
        .thenReturn(user);
    User userUpdated = userService.updatePassword(USER_UUID_1.toString(), PASSWORD, PASSWORD);
    assertThat(userUpdated).isEqualTo(user);
    verify(setPasswordApiClientMock).updatePassword(USER_UUID_1.toString(), PASSWORD, PASSWORD);
  }
}
