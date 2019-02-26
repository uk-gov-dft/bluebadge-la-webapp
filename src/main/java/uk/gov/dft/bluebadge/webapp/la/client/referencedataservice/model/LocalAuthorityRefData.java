package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LocalAuthorityRefData extends ReferenceData {

  @JsonProperty("metaData")
  private LocalAuthorityMetaData localAuthorityMetaData = null;

  @JsonIgnore
  public String getContactUrl() {
    return getLocalAuthorityMetaData().map(LocalAuthorityMetaData::getContactUrl).orElse(null);
  }

  @JsonIgnore
  public Nation getNation() {
    return getLocalAuthorityMetaData().map(LocalAuthorityMetaData::getNation).orElse(null);
  }

  @JsonIgnore
  public String getWelshDescription() {
    return getLocalAuthorityMetaData()
        .map(LocalAuthorityMetaData::getWelshDescription)
        .orElse(null);
  }

  @JsonIgnore
  public String getClockType() {
    return getLocalAuthorityMetaData().map(LocalAuthorityMetaData::getClockType).orElse(null);
  }

  @JsonIgnore
  public Boolean getPaymentsEnabled() {
    return getLocalAuthorityMetaData().map(LocalAuthorityMetaData::getPaymentsEnabled).orElse(null);
  }

  @JsonIgnore
  public BigDecimal getBadgeCost() {
    return getLocalAuthorityMetaData().map(LocalAuthorityMetaData::getBadgeCost).orElse(null);
  }

  @JsonIgnore
  public String getDifferentServiceSignpostUrl() {
    return getLocalAuthorityMetaData()
        .map(LocalAuthorityMetaData::getDifferentServiceSignpostUrl)
        .orElse(null);
  }

  public Optional<LocalAuthorityMetaData> getLocalAuthorityMetaData() {
    return Optional.ofNullable(localAuthorityMetaData);
  }

  @Data
  @Builder
  public static class LocalAuthorityMetaData implements Serializable {
    private String contactUrl;
    private String welshDescription;
    private String nameLine2;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String town;
    private String county;
    private String postcode;
    private String country;
    private Nation nation;
    private String contactNumber;
    private String emailAddress;
    private String clockType;
    private Boolean paymentsEnabled;
    private BigDecimal badgeCost;
    private String differentServiceSignpostUrl;
  }
}
