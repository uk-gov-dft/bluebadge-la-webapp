package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;

@Service
public class SignInServiceImpl implements SignInService {

  private UserManagementClient userManagementClient;
  private UserService userService;

  public SignInServiceImpl(UserManagementClient userManagementClient, UserService userService) {
    this.userManagementClient = userManagementClient;
    this.userService = userService;
  }

  public Optional<UserResponse> signIn(String email) {
    // TODO: Improve implementation once authorisation service is implemented.
    return userService.findOneByEmail(email);
  }
}
