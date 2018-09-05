package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/** Party */
@Validated
public class AppParty {
  @JsonProperty("typeCode")
  private PartyTypeCodeField typeCode = null;

  @JsonProperty("contact")
  private AppContact contact = null;

  @JsonProperty("person")
  private AppPerson person = null;

  @JsonProperty("organisation")
  private AppOrganisation organisation = null;

  public AppParty typeCode(PartyTypeCodeField typeCode) {
    this.typeCode = typeCode;
    return this;
  }

  /**
   * Get typeCode
   *
   * @return typeCode
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public PartyTypeCodeField getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(PartyTypeCodeField typeCode) {
    this.typeCode = typeCode;
  }

  public AppParty contact(AppContact contact) {
    this.contact = contact;
    return this;
  }

  /**
   * Get contact
   *
   * @return contact
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public AppContact getContact() {
    return contact;
  }

  public void setContact(AppContact contact) {
    this.contact = contact;
  }

  public AppParty person(AppPerson person) {
    this.person = person;
    return this;
  }

  /**
   * Get person
   *
   * @return person
   */
  @ApiModelProperty(value = "")
  @Valid
  public AppPerson getPerson() {
    return person;
  }

  public void setPerson(AppPerson person) {
    this.person = person;
  }

  public AppParty organisation(AppOrganisation organisation) {
    this.organisation = organisation;
    return this;
  }

  /**
   * Get organisation
   *
   * @return organisation
   */
  @ApiModelProperty(value = "")
  @Valid
  public AppOrganisation getOrganisation() {
    return organisation;
  }

  public void setOrganisation(AppOrganisation organisation) {
    this.organisation = organisation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppParty party = (AppParty) o;
    return Objects.equals(this.typeCode, party.typeCode)
        && Objects.equals(this.contact, party.contact)
        && Objects.equals(this.person, party.person)
        && Objects.equals(this.organisation, party.organisation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeCode, contact, person, organisation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Party {\n");

    sb.append("    typeCode: ").append(toIndentedString(typeCode)).append("\n");
    sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
    sb.append("    person: ").append(toIndentedString(person)).append("\n");
    sb.append("    organisation: ").append(toIndentedString(organisation)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
