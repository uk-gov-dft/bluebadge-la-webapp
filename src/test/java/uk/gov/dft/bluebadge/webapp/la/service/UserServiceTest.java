package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
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

  @Mock private UserManagementApiClient userManagementApiClientMock;
  @Mock private SetPasswordApiClient setPasswordApiClientMock;
  @Mock private FindByIndexNameSessionRepository sessionRepositoryMock;

  private UserService userService;

  private User user;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    userService =
        new UserService(
            userManagementApiClientMock, setPasswordApiClientMock, sessionRepositoryMock);

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

    when(userManagementApiClientMock.getUsersForAuthority(LOCAL_AUTHORITY_ID, ""))
        .thenReturn(usersFromClient);
    List<User> users = userService.find(LOCAL_AUTHORITY_ID);
    List<User> expectedUsers = Arrays.asList(user3, user2, user5, user4, user1);
    assertThat(users).isEqualTo(expectedUsers);
  }

  @Test
  public void find_ShouldReturnEmptyList_WhenThereAreNoUsers() {
    final String LOCAL_AUTHORITY = "BIRM";
    List<User> usersFromClient = Arrays.asList();

    when(userManagementApiClientMock.getUsersForAuthority(LOCAL_AUTHORITY, ""))
        .thenReturn(usersFromClient);
    List<User> users = userService.find(LOCAL_AUTHORITY);
    List<User> expectedUsers = Arrays.asList();
    assertThat(users).isEqualTo(expectedUsers);
  }

  @Test
  public void retrieve_ShouldReturnUser() {
    when(userManagementApiClientMock.getByUuid(USER_UUID_1)).thenReturn(user);
    User userRetrieved = userService.retrieve(USER_UUID_1);
    assertThat(userRetrieved).isEqualTo(user);
    verify(userManagementApiClientMock).getByUuid(USER_UUID_1);
  }

  @Test
  public void create_ShouldCreateAUser() {
    when(userManagementApiClientMock.createUser(user)).thenReturn(user);
    User userCreated = userService.create(user);
    assertThat(userCreated).isEqualTo(user);
    verify(userManagementApiClientMock).createUser(user);
  }

  @Test
  public void update_ShouldUpdateAUser() {
    when(userManagementApiClientMock.updateUser(user)).thenReturn(user);
    User userUpdated = userService.update(user);
    assertThat(userUpdated).isEqualTo(user);
    verify(userManagementApiClientMock).updateUser(user);
  }

  @Test
  public void delete_ShouldDeleteAUser() {
    userService.delete(USER_UUID_1);
    verify(userManagementApiClientMock).deleteUser(USER_UUID_1);
  }

  @Test
  public void requestPasswordReset_ShouldRequestAPasswordReset() {
    when(userManagementApiClientMock.getByUuid(USER_UUID_1)).thenReturn(user);
    Map<String, Session> sessions = new HashMap<>();
    Session session1 = new MapSession();
    Session session2 = new MapSession();
    sessions.put("session1", session1);
    sessions.put("session2", session2);
    when(sessionRepositoryMock.findByPrincipalName(EMAIL_ADDRESS)).thenReturn(sessions);

    userService.requestPasswordReset(USER_UUID_1);

    verify(userManagementApiClientMock).requestPasswordReset(USER_UUID_1);
    verify(sessionRepositoryMock).deleteById(session1.getId());
    verify(sessionRepositoryMock).deleteById(session2.getId());
    verify(sessionRepositoryMock, times(2)).deleteById(any());
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
