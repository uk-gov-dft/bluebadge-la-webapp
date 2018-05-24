package uk.gov.dft.bluebadge.webapp.la.service;

// Make sure this is the user we want
import java.util.List;
import java.util.Optional;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;

public interface UserService {

  Optional<User> findById(Long id);

  List<User> findAll();

  boolean checkUserExistsForEmail(String email);

  UserResponse create(User user) throws Exception;

  int update(User user);

  int delete(Long id);

  List<User> findAll(int localAuthority);
}
