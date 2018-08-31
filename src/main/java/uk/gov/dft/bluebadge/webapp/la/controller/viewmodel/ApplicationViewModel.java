package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationViewModel {
  // personal details
  private String applicationId;
  private String fullName;
  private String gender;
  private String dob;
  private String nino;
  private String address;
  private String contactDetailsName;
  private String contactDetailsPhoneNumber;
  private String contactDetailsEmail;
  private String proofOfIdentityUrl;
  private String proofOfAddressUrl;
  private String photoUrl;

  // shared
  private String reasonForApplying;
  private List<String> healthConditions;
  private List<String> healthcareProfessionals;
  private List<String> treatments;
  private List<String> medications;

  // walking
  private List<String> walkingDifficulties;
  private List<String> mobilityAids;
  private String walkingSpeed;
  private String walkingDuration;

  // Arms
  private String drivingFrequency;
  private String adaptedVehicle;
  private String proofOfUpdateVehicle;

  // Child with bulky equipment:
  private List<String> medicalEquipment;

  // Child near a car

  // application details
  private String type;
  private String submitted;
  private String applicant;
}
