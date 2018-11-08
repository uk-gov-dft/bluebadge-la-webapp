package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import lombok.Data;

@Data
public class ManageUsersFormRequest implements Serializable {
  private String search;
}
