package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/** WalkingDifficulty */
@Validated
@Data
public class WalkingDifficulty {
  @JsonProperty("typeCodes")
  @Valid
  private List<WalkingDifficultyTypeCodeField> typeCodes = null;

  @JsonProperty("painDescription")
  @Size(max = 2000)
  private String painDescription = null;

  @JsonProperty("balanceDescription")
  @Size(max = 2000)
  private String balanceDescription = null;

  @JsonProperty("healthProfessionsForFalls")
  private Boolean healthProfessionsForFalls = null;

  @JsonProperty("dangerousDescription")
  @Size(max = 2000)
  private String dangerousDescription = null;

  @JsonProperty("chestLungHeartEpilepsy")
  private Boolean chestLungHeartEpilepsy = null;

  @JsonProperty("otherDescription")
  @Size(max = 255)
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
