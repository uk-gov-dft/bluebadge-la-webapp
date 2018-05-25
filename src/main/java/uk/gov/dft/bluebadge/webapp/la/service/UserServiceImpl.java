package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.client.usermanagement.api.UserManagementService;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;
import uk.gov.dft.bluebadge.model.usermanagement.UsersResponse;
import uk.gov.dft.bluebadge.webapp.la.comparator.UserComparatorByNameAscendingOrderCaseInsensitive;

@Service
public class UserServiceImpl implements UserService {

  private UserManagementService userManagementService;

  @Autowired
  public UserServiceImpl(UserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  public Optional<UserResponse> findOneByEmail(String email) {
    if (userManagementService.checkUserExistsForEmail(email)) {
      UserResponse userResponse = userManagementService.getUserForEmail(email);
      return Optional.of(userResponse);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public UsersResponse find(int localAuthority, String nameFilter) {
    UsersResponse usersResponse =
        this.userManagementService.getUsersForAuthority(localAuthority, nameFilter);
    Collections.sort(
        usersResponse.getData().getUsers(),
        new UserComparatorByNameAscendingOrderCaseInsensitive());
    return usersResponse;
  }

  @Override
  public UsersResponse find(int localAuthority) {
    return find(localAuthority, "");
  }

  @Override
  public UserResponse create(User user) {
    return userManagementService.createUser(user.getLocalAuthorityId(), user);
  }

  @Override
  public UserResponse update(User user) {
    return userManagementService.updateUser(user);
  }

  @Override
  public boolean checkUserExistsForEmail(String email) {
    return userManagementService.checkUserExistsForEmail(email);
  }
}
