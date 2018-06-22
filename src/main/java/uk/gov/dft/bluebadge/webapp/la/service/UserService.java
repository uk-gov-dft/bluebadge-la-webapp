package uk.gov.dft.bluebadge.webapp.la.service;

// Make sure this is the user we want

import java.util.Optional;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UsersResponse;

public interface UserService {

  UserResponse findOneById(int id);

  Optional<UserResponse> findOneByEmail(String email);

  UsersResponse find(int localAuthority, String nameFilter);

  UsersResponse find(int localAuthority);

  boolean checkUserExistsForEmail(String email);

  UserResponse create(User user) throws Exception;

  UserResponse update(User user) throws Exception;

  UserResponse updatePassword(String uuid, String password, String passwordConfirm)
      throws Exception;

  void delete(Integer localAuthorityId, Integer id);

  void requestResetPassword(Integer localAuthorityId, Integer id);
}
