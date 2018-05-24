package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.client.usermanagement.api.UserManagementService;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserData;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;

@Service
public class SignInServiceImpl implements SignInService {

  private UserManagementService userManagementService;

  public SignInServiceImpl(UserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  public Optional<User> signIn(String email) {
    if (userManagementService.checkUserExistsForEmail(email)) {
      UserResponse userResponse = userManagementService.getUserForEmail(email);
      UserData userData = userResponse.getData();
      User user =
          new User()
              .id(userData.getId())
              .name(userData.getName())
              .localAuthorityId(userData.getLocalAuthorityId())
              .emailAddress(userData.getEmailAddress());
      return Optional.of(user);
    } else {
      return Optional.empty();
    }
  }
}
