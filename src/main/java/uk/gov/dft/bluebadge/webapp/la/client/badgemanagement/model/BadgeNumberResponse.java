package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;

/** Returns a badge number. */
@Validated
@Data
public class BadgeNumberResponse extends CommonResponse {
  @JsonProperty("data")
  private String data = null;
}
