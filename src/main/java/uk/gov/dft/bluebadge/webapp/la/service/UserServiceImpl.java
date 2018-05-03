package uk.gov.dft.bluebadge.webapp.la.service;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.service.model.User;

@Service
public class UserServiceImpl implements UserService {

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
  public boolean isAuthorised(String email, String password) {
    if (email.startsWith("mg")) {
      return false;
    }
    return true;
  }
}
