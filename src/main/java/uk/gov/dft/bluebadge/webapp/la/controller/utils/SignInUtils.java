package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dft.bluebadge.model.usermanagement.UserData;

@Deprecated
public class SignInUtils {
  public static final Optional<UserData> getUserSignedIn(HttpSession session) {
    UserData user = ((UserData) session.getAttribute("user"));
    return Optional.ofNullable(user);
  }

  public static final Optional<String> getEmailSignedIn(HttpSession session) {
    Optional<UserData> user = getUserSignedIn(session);
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
