package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/** WalkingDifficulty */
@Validated
@Data
public class WalkingDifficulty {
  @JsonProperty("typeCodes")
  private List<WalkingDifficultyTypeCodeField> typeCodes = null;

  @JsonProperty("painDescription")
  private String painDescription = null;

  @JsonProperty("balanceDescription")
  private String balanceDescription = null;

  @JsonProperty("healthProfessionsForFalls")
  private Boolean healthProfessionsForFalls = null;

  @JsonProperty("dangerousDescription")
  private String dangerousDescription = null;

  @JsonProperty("chestLungHeartEpilepsy")
  private Boolean chestLungHeartEpilepsy = null;

  @JsonProperty("otherDescription")
  private String otherDescription = null;

  @JsonProperty("walkingAids")
  @Valid
  private List<WalkingAid> walkingAids = null;

  @JsonProperty("walkingLengthOfTimeCode")
  @NotNull
  @Valid
  private WalkingLengthOfTimeCodeField walkingLengthOfTimeCode = null;

  @JsonProperty("walkingSpeedCode")
  @Valid
  private WalkingSpeedCodeField walkingSpeedCode = null;

  @JsonProperty("treatments")
  @Valid
  private List<Treatment> treatments = null;

  @JsonProperty("medications")
  @Valid
  private List<Medication> medications = null;

  @JsonProperty("breathlessness")
  @Valid
  private Breathlessness breathlessness = null;
}
