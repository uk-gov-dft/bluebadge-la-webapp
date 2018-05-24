package uk.gov.dft.bluebadge.webapp.la.comparator;

import java.io.Serializable;
import java.util.Comparator;
import uk.gov.dft.bluebadge.model.usermanagement.User;

/** To be used to sort <code>User</code> by name in ascending order. */
public class UserComparatorByFullName implements Comparator<User>, Serializable {
  public int compare(User a, User b) {
    return a.getName().compareTo(b.getName());
  }
}
