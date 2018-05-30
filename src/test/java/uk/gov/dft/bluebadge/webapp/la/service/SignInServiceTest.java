package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.client.usermanagement.api.UserManagementService;

public class SignInServiceTest {

  private static final String EMAIL = "joeblogs@joe.com";
  private static final String EMAIL_WRONG_FORMAT = "joeblogs";
  private static final String PASSWORD = "password";

  @Mock private UserService userService;
  @Mock private UserManagementService userManagementServiceMock;

  private SignInService signInService;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    signInService = new SignInServiceImpl(userManagementServiceMock, userService);
  }

  // TODO: Once we have the final implementation of sign in, we should complete this "placeholder"
  @Test
  public void signIn_shouldWork() throws Exception {
    Object response = signInService.signIn(EMAIL);
    assertThat(response).isNotNull();
  }
}
