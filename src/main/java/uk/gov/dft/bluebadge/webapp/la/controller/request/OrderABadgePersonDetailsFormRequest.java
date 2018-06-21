package uk.gov.dft.bluebadge.webapp.la.controller.request;

import javax.validation.constraints.*;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.ConsistentDate2;

@Data
//@ConsistentDate(message = "{Pattern.badge.dob}")
public class OrderABadgePersonDetailsFormRequest {

  @NotNull
  @Pattern(regexp = "^[\\p{L} \\.'\\-]+$", message = "{Pattern.user.name}")
  @Size(max = 100)
  private String name;

  private Integer dobDay;

  private Integer dobMonth;

  private Integer dobYear;

  private String dob;

  @ConsistentDate2(message = "{Pattern.badge.dob}")
  public String getDob() {
    return "" + dobDay + "/" + dobMonth + "/" + dobYear;
  }

  @Pattern(
    regexp =
        "(^[AEHKLTYZ]{1}[ABEHK-MPR-TW-Z}{1}"
            + "|^[B]{1}[ABEK-MT]{1}"
            + "|^[C]{1}[ABEHKLR]{1}"
            + "|^[G]{1}[Y]{1}"
            + "|^[J]{1}[A-CEGHJ-NPR-TW-Z]{1}"
            + "|^[M]{1}[AWX]{1}"
            + "|^[N]{1}[ABEHLMPRSW-Z]{1]"
            + "|^[O]{1}[ABEHK-MPRSX]{1}"
            + "|^[P]{1}[A-CEGHJ-NPR-TW-Y]{1}"
            + "|^[R]{1}[ABEHKMPR-TW-Z]{1}"
            + "|^[S]{1}[A-CEGHJ-NPR-TW-Z]{1}"
            + "|^[W]{1}[ABEK-MP]{1})[0-9]{6}[A-DFM]{1}$\"",
    message = "{Pattern.badge.nino}"
  )
  private String nino;

  @NotNull(message = "{NotNull.badge.buildingAndStreet}")
  private String buildingAndStreet;

  private String optionalAddressField;

  @NotNull(message = "{NotNull.badge.townOrCity}")
  private String townOrCity;

  @NotNull(message = "{NotNull.badge.postcode}")
  @Pattern(
    regexp =
        "^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)$",
    message = "{Pattern.badge.postcode}"
  )
  private String postcode;

  @Pattern(regexp = "^[\\p{L} \\.'\\-]+$", message = "{Pattern.user.name}")
  private String contactDetailsName;

  @NotNull(message = "{NotNull.badge.contactDetailsContactNumber}")
  private String contactDetailsContactNumber;

  @Pattern(
    regexp =
        "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
    message = "{NotNull.user.emailAddress}"
  )
  private String contactDetailsEmailAddress;

  @NotNull(message = "{NotNull.badge.eligibility}")
  private String eligibility;
}
