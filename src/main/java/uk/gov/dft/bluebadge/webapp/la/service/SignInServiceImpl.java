package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.client.usermanagement.api.UserManagementService;
import uk.gov.dft.bluebadge.model.usermanagement.User;

@Service
public class SignInServiceImpl implements SignInService {

  private UserManagementService userManagementService;
  private UserService userService;

  public SignInServiceImpl(UserManagementService userManagementService, UserService userService) {
    this.userManagementService = userManagementService;
    this.userService = userService;
  }

  public Optional<User> signIn(String email) {
    // TODO: Improve implementation once authorisation service is implemented.
    return userService.findOneByEmail(email);
  }
}
