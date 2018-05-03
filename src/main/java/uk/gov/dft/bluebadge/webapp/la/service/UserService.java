package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.List;
import java.util.Optional;
import uk.gov.dft.bluebadge.webapp.la.service.model.User;

public interface UserService {

  Optional<User> findById(Long id);

  List<User> findAll();

  int create(User user);

  int update(User user);

  int delete(Long id);

  boolean isAuthorised(String email, String password);
}
