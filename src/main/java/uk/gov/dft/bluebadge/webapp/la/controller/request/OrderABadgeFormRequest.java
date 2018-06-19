package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

@Data
public class OrderABadgeFormRequest {
  private String name;
  private Integer dobDay;
  private Integer dobMonth;
  private Integer dobYear;
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
