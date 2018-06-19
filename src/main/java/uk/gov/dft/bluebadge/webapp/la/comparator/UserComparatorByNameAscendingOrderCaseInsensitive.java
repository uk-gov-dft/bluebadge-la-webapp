package uk.gov.dft.bluebadge.webapp.la.comparator;

import java.io.Serializable;
import java.util.Comparator;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;

/** To be used to sort <code>User</code> by name in ascending order case sensitive. */
public class UserComparatorByNameAscendingOrderCaseInsensitive
    implements Comparator<User>, Serializable {
  public int compare(User u1, User u2) {
    String name1 = u1.getName();
    String name2 = u2.getName();
    int res = String.CASE_INSENSITIVE_ORDER.compare(name1, name2);
    if (res == 0) {
      res = name1.compareTo(name2);
    }
    return res;
  }
}
