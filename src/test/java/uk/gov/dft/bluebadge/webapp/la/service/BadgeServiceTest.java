package uk.gov.dft.bluebadge.webapp.la.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;

public class BadgeServiceTest {
  /*
    private static final String EMAIL = "joeblogs@joe.com";
    private static final String EMAIL_WRONG_FORMAT = "joeblogs";
    private static final String PASSWORD = "password";
  */
  @Mock private BadgeManagementApiClient badgeManagementApiClient;

  private BadgeService badgeService;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    badgeService = new BadgeService(badgeManagementApiClient);
  }

  @Ignore
  @Test
  public void orderBadge_shouldOrderABadge() {
    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest();
    String badgeNumber = badgeService.orderABadge(badgeOrderRequest);
  }

  /*
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

    when(userManagementServiceMock.getUsersForAuthority(LOCAL_AUTHORITY, ""))
        .thenReturn(usersFromClient);
    List<User> users = userService.find(LOCAL_AUTHORITY);
    List<User> expectedUsers = Arrays.asList(user3, user2, user5, user4, user1);
    assertThat(users).isEqualTo(expectedUsers);
  }
  */
}
