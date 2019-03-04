package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/** Application */
@Validated
@Data
@Builder
public class Application {
  private String applicationId;
  private ApplicationTypeCodeField applicationTypeCode;
  private String localAuthorityCode;
  private Boolean paymentTaken;
  private OffsetDateTime submissionDate;
  private String existingBadgeNumber;
  private AppParty party;
  private Eligibility eligibility;
  private String paymentReference;
  private List<Artifact> artifacts;
  private ApplicationStatusField applicationStatus;

  public List<Artifact> getProofOfIdentityArtifacts() {
    return getArtifactsByType(Artifact.TypeEnum.PROOF_ID);
  }

  public List<Artifact> getProofOfAddressArtifacts() {
    return getArtifactsByType(Artifact.TypeEnum.PROOF_ADD);
  }

  public List<Artifact> getProofOfEligibilityArtifacts() {
    return getArtifactsByType(Artifact.TypeEnum.PROOF_ELIG);
  }

  public List<Artifact> getBadgePhotoArtifacts() {
    return getArtifactsByType(Artifact.TypeEnum.PHOTO);
  }

  public List<Artifact> getSupportDocsArtifacts() {
    return getArtifactsByType(Artifact.TypeEnum.SUPPORT_DOCS);
  }

  public List<Artifact> getArtifactsByType(Artifact.TypeEnum artifactType) {
    if (null == artifacts) {
      return Collections.emptyList();
    }
    return artifacts.stream().filter(a -> artifactType == a.getType()).collect(Collectors.toList());
  }
}
