package uk.gov.dft.bluebadge.webapp.la.service;

// Make sure this is the user we want
import java.util.List;
import java.util.Optional;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;

public interface UserService {

  Optional<User> findOneByEmail(String email);

  List<User> find(int localAuthority, String nameFilter);

  List<User> find(int localAuthority);

  boolean checkUserExistsForEmail(String email);

  UserResponse create(User user) throws Exception;

  int update(User user);

  int delete(Long id);
}
