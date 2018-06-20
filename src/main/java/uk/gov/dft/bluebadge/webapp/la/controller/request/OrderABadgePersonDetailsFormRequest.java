package uk.gov.dft.bluebadge.webapp.la.controller.request;

import javax.validation.constraints.*;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.ConsistentDate;

@Data
@ConsistentDate(message = "{error.orderabadgepersondetails.dob.consistentdate}")
public class OrderABadgePersonDetailsFormRequest {

  @NotNull
  @Pattern(regexp = "^[\\p{L} \\.'\\-]+$", message = "{Pattern.user.name}")
  @Size(max = 100)
  private String name;

  @NotNull(message = "Day is mandatory")
  @Min(1)
  @Max(31)
  private Integer dobDay;

  @NotNull(message = "Month is mandatory")
  @Min(1)
  @Max(12)
  private Integer dobMonth;

  @NotNull(message = "Year is mandatory")
  private Integer dobYear;

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

  private String buildingAndStreet;
  private String optionalAddressField;
  private String townOrCity;
  private String postcode;
  private String contactDetailsName;
  private String contactDetailsContactNumber;
  private String contactDetailsEmailAddress;
  private String eligibility;
}
