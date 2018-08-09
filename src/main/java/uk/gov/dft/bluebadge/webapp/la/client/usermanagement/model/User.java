package uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.validation.annotation.Validated;

/** User */
@Validated
public class User {

  @JsonProperty("uuid")
  private UUID uuid;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("emailAddress")
  private String emailAddress = null;

  @JsonProperty("localAuthorityShortCode")
  private String localAuthorityShortCode = null;

  @JsonProperty("roleId")
  private Integer roleId = null;

  @JsonProperty("roleName")
  private String roleName = null;

  public User() {
    // Added to allow Jackson to create an instance using the default constructor
  }

  /**
   * Users name.
   *
   * @return name
   */
  @ApiModelProperty(example = "Robert Worthington", required = true, value = "Users name.")
  @NotNull
  @Pattern(regexp = "^[\\p{L} \\.'\\-]+$")
  @Size(max = 100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  /**
   * email address.
   *
   * @return emailAddress
   */
  @ApiModelProperty(
    example = "rob.worthington@norealserver.com",
    required = true,
    value = "email address."
  )
  @NotNull
  @Pattern(regexp = ".+\\@.+")
  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /**
   * Id of users role
   *
   * @return roleId
   */
  @ApiModelProperty(example = "2", required = true, value = "Id of users role")
  @NotNull
  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public User roleName(String roleName) {
    this.roleName = roleName;
    return this;
  }

  /**
   * User role description
   *
   * @return roleName
   */
  @ApiModelProperty(example = "LA Admin", value = "User role description")
  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /**
   * UUID of the User. Used instead of a primary key.
   *
   * @return
   */
  @NotNull
  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  /**
   * loaclAuthjorityShortCode that the User belongs to.
   *
   * @return
   */
  @NotNull
  @Size(max = 10)
  public String getLocalAuthorityShortCode() {
    return localAuthorityShortCode;
  }

  public void setLocalAuthorityShortCode(String localAuthorityShortCode) {
    this.localAuthorityShortCode = localAuthorityShortCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    return new EqualsBuilder()
        .append(uuid, user.uuid)
        .append(name, user.name)
        .append(emailAddress, user.emailAddress)
        .append(localAuthorityShortCode, user.localAuthorityShortCode)
        .append(roleId, user.roleId)
        .append(roleName, user.roleName)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(uuid)
        .append(name)
        .append(emailAddress)
        .append(localAuthorityShortCode)
        .append(roleId)
        .append(roleName)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("uuid", uuid)
        .append("name", name)
        .append("emailAddress", emailAddress)
        .append("localAuthorityShortCode", localAuthorityShortCode)
        .append("roleId", roleId)
        .append("roleName", roleName)
        .toString();
  }

  // Method compatibility with lombok
  public static Builder builder() {
    return Builder.builder();
  }

  public static final class Builder {
    private UUID uuid;
    private String name = null;
    private String emailAddress = null;
    private String localAuthorityShortCode = null;
    private Integer roleId = null;
    private String roleName = null;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder uuid(UUID uuid) {
      this.uuid = uuid;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder emailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
      return this;
    }

    public Builder localAuthorityShortCode(String localAuthorityShortCode) {
      this.localAuthorityShortCode = localAuthorityShortCode;
      return this;
    }

    public Builder roleId(Integer roleId) {
      this.roleId = roleId;
      return this;
    }

    public Builder roleName(String roleName) {
      this.roleName = roleName;
      return this;
    }

    public User build() {
      User user = new User();
      user.setUuid(uuid);
      user.setName(name);
      user.setEmailAddress(emailAddress);
      user.setLocalAuthorityShortCode(localAuthorityShortCode);
      user.setRoleId(roleId);
      user.setRoleName(roleName);
      return user;
    }
  }
}
