package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Optional;
import uk.gov.dft.bluebadge.model.usermanagement.User;

public interface SignInService {
  Optional<User> signIn(String email) throws Exception;
}
