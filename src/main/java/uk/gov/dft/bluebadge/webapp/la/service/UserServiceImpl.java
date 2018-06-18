package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UsersResponse;
import uk.gov.dft.bluebadge.webapp.la.comparator.UserComparatorByNameAscendingOrderCaseInsensitive;

@Service
public class UserServiceImpl implements UserService {

  private UserManagementClient userManagementClient;

  @Autowired
  public UserServiceImpl(UserManagementClient userManagementClient) {
    this.userManagementClient = userManagementClient;
  }

  @Override
  public UserResponse findOneById(int id) {
    // TODO: There should be a getById with only one id prettty soon.
    return userManagementClient.getById(2, id);
  }

  // TODO: Changes to return UserResponse, if it is empty or not is inside UserResponse.
  @Override
  public Optional<UserResponse> findOneByEmail(String email) {
    if (userManagementClient.checkUserExistsForEmail(email)) {
      UserResponse userResponse = userManagementClient.getUserForEmail(email);
      return Optional.of(userResponse);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public UsersResponse find(int localAuthority, String nameFilter) {
    UsersResponse usersResponse =
        this.userManagementClient.getUsersForAuthority(localAuthority, nameFilter);
    if (usersResponse.getData().getTotalItems() > 0) {
      Collections.sort(
          usersResponse.getData().getUsers(),
          new UserComparatorByNameAscendingOrderCaseInsensitive());
    }
    return usersResponse;
  }

  @Override
  public UsersResponse find(int localAuthority) {
    return find(localAuthority, "");
  }

  @Override
  public UserResponse create(User user) {
    return userManagementClient.createUser(user.getLocalAuthorityId(), user);
  }

  @Override
  public UserResponse update(User user) {
    return userManagementClient.updateUser(user);
  }

  @Override
  public UserResponse updatePassword(String uuid, String password, String passwordConfirm) {
    return userManagementClient.updatePassword(uuid, password, passwordConfirm);
  }

  @Override
  public void delete(Integer localAuthorityId, Integer id) {
    userManagementClient.deleteUser(localAuthorityId, id);
  }

  @Override
  public boolean checkUserExistsForEmail(String email) {
    return userManagementClient.checkUserExistsForEmail(email);
  }
}
