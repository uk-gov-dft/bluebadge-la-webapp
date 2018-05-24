package uk.gov.dft.bluebadge.webapp.la.service;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.client.usermanagement.api.UserManagementService;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserData;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;
import uk.gov.dft.bluebadge.model.usermanagement.UsersResponse;
import uk.gov.dft.bluebadge.webapp.la.comparator.UserComparatorByNameAscendingOrderCaseSensitive;

@Service
public class UserServiceImpl implements UserService {

  private UserManagementService userManagementService;

  @Autowired
  public UserServiceImpl(UserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  public Optional<User> findOneByEmail(String email) {
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

  @Override
  public List<User> find(int localAuthority, String nameFilter) {
    UsersResponse usersResponse =
        this.userManagementService.getUsersForAuthority(localAuthority, nameFilter);
    List<User> users = usersResponse.getData().getUsers();
    if (users == null) {
      return Lists.newArrayList();
    }
    Collections.sort(users, new UserComparatorByNameAscendingOrderCaseSensitive());
    return users;
  }

  @Override
  public List<User> find(int localAuthority) {
    return find(localAuthority, "");
  }

  @Override
  public UserResponse create(User user) {
    return userManagementService.createUser(user.getLocalAuthorityId(), user);
  }

  @Override
  public int update(User user) {
    return 1;
  }

  @Override
  public int delete(Long id) {
    return 1;
  }

  @Override
  public boolean checkUserExistsForEmail(String email) {
    return userManagementService.checkUserExistsForEmail(email);
  }
}
