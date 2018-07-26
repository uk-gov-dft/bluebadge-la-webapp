package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.SetPasswordApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.comparator.UserComparatorByNameAscendingOrderCaseInsensitive;

@Service
public class UserService {

  private UserManagementApiClient userManagementApiClient;
  private SetPasswordApiClient setPasswordApiClient;

  @Autowired
  public UserService(
      UserManagementApiClient userManagementApiClient, SetPasswordApiClient setPasswordApiClient) {
    this.userManagementApiClient = userManagementApiClient;
    this.setPasswordApiClient = setPasswordApiClient;
  }

  public User retrieve(int id) {
    return userManagementApiClient.getById(id);
  }

  public List<User> find(int localAuthority, String nameFilter) {
    List<User> usersResponse =
        this.userManagementApiClient.getUsersForAuthority(localAuthority, nameFilter);
    if (!usersResponse.isEmpty()) {
      Collections.sort(usersResponse, new UserComparatorByNameAscendingOrderCaseInsensitive());
    }
    return usersResponse;
  }

  public List<User> find(int localAuthority) {
    return find(localAuthority, "");
  }

  public User create(User user) {
    return userManagementApiClient.createUser(user);
  }

  public User update(User user) {
    return userManagementApiClient.updateUser(user);
  }

  public User updatePassword(String uuid, String password, String passwordConfirm) {
    return setPasswordApiClient.updatePassword(uuid, password, passwordConfirm);
  }

  public void delete(Integer id) {
    userManagementApiClient.deleteUser(id);
  }

  public void requestPasswordReset(Integer id) {
    userManagementApiClient.requestPasswordReset(id);
  }
}
