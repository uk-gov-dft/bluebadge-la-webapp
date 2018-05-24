package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dft.bluebadge.model.usermanagement.User;

public class SignInUtils {
  public static final Optional<User> getUserSignedIn(HttpSession session) {
    User user = ((User) session.getAttribute("user"));
    return Optional.ofNullable(user);
  }

  public static final Optional<String> getEmailSignedIn(HttpSession session) {
    Optional<User> user = getUserSignedIn(session);
    if (user.isPresent()) {
      String email = user.get().getEmailAddress();
      if (!StringUtils.isEmpty(email)) {
        return Optional.of(email);
      }
    }
    return Optional.empty();
  }

  public static final boolean isSignedIn(HttpSession session) {
    Optional<String> email = getEmailSignedIn(session);
    if (email.isPresent()) {
      return true;
    }
    return false;
  }
}
