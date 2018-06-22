package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;

@Service
public class SignInServiceImpl implements SignInService {

  private UserManagementApiClient userManagementApiClient;
  private UserService userService;

  public SignInServiceImpl(
      UserManagementApiClient userManagementApiClient, UserService userService) {
    this.userManagementApiClient = userManagementApiClient;
    this.userService = userService;
  }

  public Optional<UserResponse> signIn(String email) {
    // TODO: Improve implementation once authorisation service is implemented.
    return userService.findOneByEmail(email);
  }
}
