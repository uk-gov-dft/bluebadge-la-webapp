package uk.gov.dft.bluebadge.webapp.la.service;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.client.usermanagement.api.UserManagementService;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UsersResponse;
import uk.gov.dft.bluebadge.webapp.la.comparator.UserComparatorByFullName;

@Service
public class UserServiceImpl implements UserService {

  private UserManagementService userManagementService;

  @Autowired
  public UserServiceImpl(UserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  @Override
  public Optional<User> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public List<User> findAll() {
    return Lists.newArrayList();
  }

  @Override
  public int create(User user) {
    return 1;
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
  public List<User> getUsers() {
    UsersResponse usersResponse = this.userManagementService.getUsersForAuthority(1, "");
    List<User> users = usersResponse.getData().getUsers();
    Collections.sort(users, new UserComparatorByFullName());
    return users;
  }
}
