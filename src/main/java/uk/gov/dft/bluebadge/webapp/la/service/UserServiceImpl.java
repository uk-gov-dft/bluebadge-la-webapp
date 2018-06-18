package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UsersResponse;
import uk.gov.dft.bluebadge.webapp.la.comparator.UserComparatorByNameAscendingOrderCaseInsensitive;

@Service
public class UserServiceImpl implements UserService {

  private UserManagementApiClient userManagementApiClient;

  @Autowired
  public UserServiceImpl(UserManagementApiClient userManagementApiClient) {
    this.userManagementApiClient = userManagementApiClient;
  }

  @Override
  public UserResponse findOneById(int id) {
    // TODO: There should be a getById with only one id prettty soon.
    return userManagementApiClient.getById(2, id);
  }

  // TODO: Changes to return UserResponse, if it is empty or not is inside UserResponse.
  @Override
  public Optional<UserResponse> findOneByEmail(String email) {
    if (userManagementApiClient.checkUserExistsForEmail(email)) {
      UserResponse userResponse = userManagementApiClient.getUserForEmail(email);
      return Optional.of(userResponse);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public UsersResponse find(int localAuthority, String nameFilter) {
    UsersResponse usersResponse =
        this.userManagementApiClient.getUsersForAuthority(localAuthority, nameFilter);
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
    return userManagementApiClient.createUser(user.getLocalAuthorityId(), user);
  }

  @Override
  public UserResponse update(User user) {
    return userManagementApiClient.updateUser(user);
  }

  @Override
  public UserResponse updatePassword(String uuid, String password, String passwordConfirm) {
    return userManagementApiClient.updatePassword(uuid, password, passwordConfirm);
  }

  @Override
  public void delete(Integer localAuthorityId, Integer id) {
    userManagementApiClient.deleteUser(localAuthorityId, id);
  }

  @Override
  public boolean checkUserExistsForEmail(String email) {
    return userManagementApiClient.checkUserExistsForEmail(email);
  }
}
