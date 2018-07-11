package uk.gov.dft.bluebadge.webapp.la.service;

// Make sure this is the user we want

import java.util.List;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;

public interface UserService {

  User findOneById(int id);

  List<User> find(int localAuthority, String nameFilter);

  List<User> find(int localAuthority);

  User create(User user);

  User update(User user);

  User updatePassword(String uuid, String password, String passwordConfirm);

  void delete(Integer id);

  void requestPasswordReset(Integer id);
}
