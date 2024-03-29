package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.SetPasswordApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.comparator.UserComparatorByNameAscendingOrderCaseInsensitive;

@Service
public class UserService {

  private UserManagementApiClient userManagementApiClient;
  private SetPasswordApiClient setPasswordApiClient;
  private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

  @Autowired
  public UserService(
      UserManagementApiClient userManagementApiClient,
      SetPasswordApiClient setPasswordApiClient,
      FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
    this.userManagementApiClient = userManagementApiClient;
    this.setPasswordApiClient = setPasswordApiClient;
    this.sessionRepository = sessionRepository;
  }

  public User retrieve(UUID uuid) {
    return userManagementApiClient.getByUuid(uuid);
  }

  public List<User> find(String localAuthorityShortCode, String nameFilter) {
    List<User> usersResponse =
        this.userManagementApiClient.getUsersForAuthority(localAuthorityShortCode, nameFilter);
    if (!usersResponse.isEmpty()) {
      Collections.sort(usersResponse, new UserComparatorByNameAscendingOrderCaseInsensitive());
    }
    return usersResponse;
  }

  public List<User> find(String localAuthorityShortCode) {
    return find(localAuthorityShortCode, "");
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

  public void delete(UUID uuid) {
    userManagementApiClient.deleteUser(uuid);
  }

  public void requestPasswordReset(UUID uuid) {
    userManagementApiClient.requestPasswordReset(uuid);
    User user = userManagementApiClient.getByUuid(uuid);
    Map<String, ? extends Session> sessions =
        sessionRepository.findByPrincipalName(user.getEmailAddress());
    sessions.values().stream().forEach(s -> sessionRepository.deleteById(((Session) s).getId()));
  }
}
