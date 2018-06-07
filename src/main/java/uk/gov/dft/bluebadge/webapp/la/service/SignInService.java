package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Optional;
import uk.gov.dft.bluebadge.model.usermanagement.UserResponse;

public interface SignInService {
  Optional<UserResponse> signIn(String email) throws Exception;
}