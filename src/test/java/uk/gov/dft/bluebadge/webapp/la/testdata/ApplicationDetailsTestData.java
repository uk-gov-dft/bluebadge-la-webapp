package uk.gov.dft.bluebadge.webapp.la.testdata;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppContact;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppOrganisation;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppParty;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppPerson;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Benefit;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Blind;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.BulkyMedicalEquipmentTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ChildUnder3;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.DisabilityArms;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Eligibility;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.GenderCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.HealthcareProfessional;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Vehicle;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.VehicleTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingDifficulty;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingDifficultyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingLengthOfTimeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingSpeedCodeField;

public class ApplicationDetailsTestData {

  private ApplicationDetailsTestData() {}

  public interface ModelValues {
    String LA_CODE = "BIRM";
    String BADGE_HOLDER_NAME = "Harry Bloggs";
    String ORG_BADGE_HOLDER_NAME = "Poodle Collar Shop";
    String BADGE_HOLDER_NAME_AT_BIRTH = "Harry Bloggs The Third";
    Boolean IS_CHARITY = Boolean.TRUE;
    String ORG_CHARITY_NO = "123456";
    Integer ORG_NO_OF_BADGES = 1;
    LocalDate PERSON_DOB = LocalDate.now().minus(Period.ofYears(30));
    GenderCodeField PERSON_GENDER = GenderCodeField.MALE;
    String PERSON_NINO = "NS123456A";
    LocalDate BENEFIT_EXPIRY = LocalDate.now().plus(Period.ofYears(1));
    Boolean BENEFIT_IS_INDEFINITE = Boolean.FALSE;
    String PROFESSIONAL_NAME = "pro name";
    String PROFESSIONAL_LOCATION = "pro location";
    String VEH_REG = "VK61VXX";
    String VEH_USAGE = "veh usage";
    VehicleTypeCodeField VEH_TYPE = VehicleTypeCodeField.CAR;
    WalkingDifficultyTypeCodeField WALKING_DIFFICULTY_TYPE_CODE_FIELD =
        WalkingDifficultyTypeCodeField.BALANCE;
    List<WalkingDifficultyTypeCodeField> WALKING_DIFFICULTY_TYPE_CODES =
        Lists.newArrayList(WALKING_DIFFICULTY_TYPE_CODE_FIELD);
    WalkingLengthOfTimeCodeField WALKING_LENGTH_OF_TIME_CODE_FIELD =
        WalkingLengthOfTimeCodeField.FEWMIN;
    WalkingSpeedCodeField WALKING_SPEED_CODE_FIELD = WalkingSpeedCodeField.FAST;
    String ARMS_DRIVE_FREQ = "drive freq";
    Boolean ARMS_IS_ADAPTED = Boolean.TRUE;
    String PHONE_NO = "123456";
    String CONTACT_BUILDING = "29 A Street";
    String CONTACT_EMAIL = "a@b.c";
    String CONTACT_NAME = "Mr Contact";
    String LINE2 = "Town Area";
    String POSTCODE = "WV164AW";
    String TOWN = "Arlington";
    ApplicationTypeCodeField APP_TYPE_CODE = ApplicationTypeCodeField.NEW;
    String ARMS_ADAPTED_DESC = "Vehicle description";
    String ID = "75a0d22f-8daf-4217-b1de-6d4136612a1d";
    java.util.UUID UUID = java.util.UUID.fromString(ID);
    Boolean PAYMENT_TAKEN = Boolean.TRUE;
    String EXISTING_BADGE_NO = "ABCDEF";
    BulkyMedicalEquipmentTypeCodeField BULKY_MEDICAL_EQUIPMENT_TYPE_CODE_FIELD =
        BulkyMedicalEquipmentTypeCodeField.CAST;
    String WALK_OTHER_DESC = "Walk Other Desc";
    String DESCRIPTION_OF_CONDITIONS = "Description of Conditions";
    OffsetDateTime SUBMISSION_DATE_TIME =
        OffsetDateTime.of(2018, 1, 31, 14, 30, 0, 0, ZoneOffset.UTC);
  }

  public interface DisplayValues {
    String SUBMISSION_DATE_TIME = "31 January 2018 at 14:30";
  }

  public static Application getOrganisationApp() {
    return new ApplicationBuilder().setOrganisation().build();
  }

  public static Application getPersonPipApp() {
    return new ApplicationBuilder().setPerson().setEligibilityPip().build();
  }

  public static Application getPersonDlaApp() {
    return new ApplicationBuilder().setPerson().setEligibilityDla().build();
  }

  public static Application getPersonAfrFcsApp() {
    return new ApplicationBuilder().setPerson().setEligibilityAfrfcs().build();
  }

  public static Application getPersonWpmsApp() {
    return new ApplicationBuilder().setPerson().setEligibilityWpms().build();
  }

  public static Application getPersonBlindApp() {
    return new ApplicationBuilder().setPerson().setEligibilityBlind().build();
  }

  public static Application getPersonWalkingApp() {
    return new ApplicationBuilder().setPerson().setEligibilityWalking().build();
  }

  public static Application getPersonArmsApp() {
    return new ApplicationBuilder().setPerson().setEligibilityArms().build();
  }

  public static Application getPersonChildbulkApp() {
    return new ApplicationBuilder().setPerson().setEligibilityChildBulk().build();
  }

  public static Application getPersonChildvehicleApp() {
    return new ApplicationBuilder().setPerson().setEligibilityChildVehic().build();
  }

  protected static class ApplicationBuilder {

    private Application application;

    ApplicationBuilder() {
      AppContact contact =
          new AppContact()
              .secondaryPhoneNumber(ModelValues.PHONE_NO)
              .buildingStreet(ModelValues.CONTACT_BUILDING)
              .emailAddress(ModelValues.CONTACT_EMAIL)
              .fullName(ModelValues.CONTACT_NAME)
              .line2(ModelValues.LINE2)
              .postCode(ModelValues.POSTCODE)
              .primaryPhoneNumber(ModelValues.PHONE_NO)
              .townCity(ModelValues.TOWN);
      AppParty party = new AppParty().contact(contact);
      application =
          new Application()
              .party(party)
              .localAuthorityCode(ModelValues.LA_CODE)
              .applicationTypeCode(ModelValues.APP_TYPE_CODE)
              .applicationId(ModelValues.ID)
              .paymentTaken(ModelValues.PAYMENT_TAKEN)
              .existingBadgeNumber(ModelValues.EXISTING_BADGE_NO)
              .submissionDate(ModelValues.SUBMISSION_DATE_TIME);
    }

    private void addBenefit() {
      Benefit benefit = new Benefit();
      benefit.setExpiryDate(ModelValues.BENEFIT_EXPIRY);
      benefit.setIsIndefinite(ModelValues.BENEFIT_IS_INDEFINITE);
      application.getEligibility().setBenefit(benefit);
    }

    private void addOrganisation() {
      AppOrganisation organisation = new AppOrganisation();
      organisation.setBadgeHolderName(ModelValues.ORG_BADGE_HOLDER_NAME);
      organisation.setNumberOfBadges(ModelValues.ORG_NO_OF_BADGES);
      organisation.setIsCharity(ModelValues.IS_CHARITY);
      organisation.setCharityNumber(ModelValues.ORG_CHARITY_NO);
      application.getParty().setOrganisation(organisation);
      addVehicle();
      addVehicle();
    }

    ApplicationBuilder setOrganisation() {
      application.getParty().setTypeCode(PartyTypeCodeField.ORG);
      addOrganisation();
      return this;
    }

    ApplicationBuilder setPerson() {
      application.getParty().setTypeCode(PartyTypeCodeField.PERSON);
      AppPerson person = new AppPerson();
      person.setBadgeHolderName(ModelValues.BADGE_HOLDER_NAME);
      person.setDob(ModelValues.PERSON_DOB);
      person.setGenderCode(ModelValues.PERSON_GENDER);
      person.setNameAtBirth(ModelValues.BADGE_HOLDER_NAME_AT_BIRTH);
      person.setNino(ModelValues.PERSON_NINO);
      application.getParty().setPerson(person);
      return this;
    }

    private void addEligibility() {
      application.setEligibility(new Eligibility());
      application
          .getEligibility()
          .setDescriptionOfConditions(ModelValues.DESCRIPTION_OF_CONDITIONS);
      addHealthcarePro();
      addHealthcarePro();
    }

    ApplicationBuilder setEligibilityPip() {
      addEligibility();
      application.getEligibility().setTypeCode(EligibilityCodeField.PIP);
      addBenefit();
      return this;
    }

    ApplicationBuilder setEligibilityDla() {
      addEligibility();
      application.getEligibility().setTypeCode(EligibilityCodeField.DLA);
      addBenefit();
      return this;
    }

    ApplicationBuilder setEligibilityWpms() {
      addEligibility();
      application.getEligibility().setTypeCode(EligibilityCodeField.WPMS);
      addBenefit();
      return this;
    }

    public Application build() {
      return application;
    }

    ApplicationBuilder setEligibilityArms() {
      addEligibility();
      application.getEligibility().setTypeCode(EligibilityCodeField.ARMS);
      addArms();
      return this;
    }

    void addHealthcarePro() {
      if (null == application.getEligibility().getHealthcareProfessionals()) {
        application.getEligibility().setHealthcareProfessionals(new ArrayList<>());
      }
      HealthcareProfessional pro = new HealthcareProfessional();
      pro.setName(ModelValues.PROFESSIONAL_NAME);
      pro.setLocation(ModelValues.PROFESSIONAL_LOCATION);
      application.getEligibility().getHealthcareProfessionals().add(pro);
    }

    void addVehicle() {
      if (null == application.getParty().getOrganisation().getVehicles()) {
        application.getParty().getOrganisation().setVehicles(new ArrayList<>());
      }
      Vehicle vehicle = new Vehicle();
      vehicle.setRegistrationNumber(ModelValues.VEH_REG);
      vehicle.setUsageFrequency(ModelValues.VEH_USAGE);
      vehicle.setTypeCode(ModelValues.VEH_TYPE);
      application.getParty().getOrganisation().getVehicles().add(vehicle);
    }

    void addWalking() {
      WalkingDifficulty walkingDifficulty = new WalkingDifficulty();
      walkingDifficulty.setTypeCodes(ModelValues.WALKING_DIFFICULTY_TYPE_CODES);
      walkingDifficulty.setWalkingLengthOfTimeCode(ModelValues.WALKING_LENGTH_OF_TIME_CODE_FIELD);
      walkingDifficulty.setWalkingSpeedCode(ModelValues.WALKING_SPEED_CODE_FIELD);
      walkingDifficulty.setOtherDescription(ModelValues.WALK_OTHER_DESC);
      application.getEligibility().setWalkingDifficulty(walkingDifficulty);
    }

    void addChild() {
      ChildUnder3 child = new ChildUnder3();
      child.setBulkyMedicalEquipmentTypeCode(ModelValues.BULKY_MEDICAL_EQUIPMENT_TYPE_CODE_FIELD);
      application.getEligibility().setChildUnder3(child);
    }

    void addArms() {
      DisabilityArms arms = new DisabilityArms();
      arms.setDrivingFrequency(ModelValues.ARMS_DRIVE_FREQ);
      arms.setIsAdaptedVehicle(ModelValues.ARMS_IS_ADAPTED);
      arms.setAdaptedVehicleDescription(ModelValues.ARMS_ADAPTED_DESC);
      application.getEligibility().disabilityArms(arms);
    }

    private ApplicationBuilder setEligibilityWalking() {
      addEligibility();
      application.getEligibility().setTypeCode(EligibilityCodeField.WALKD);
      addWalking();
      return this;
    }

    private ApplicationBuilder setEligibilityChildBulk() {
      addEligibility();
      application.getEligibility().setTypeCode(EligibilityCodeField.CHILDBULK);
      addChild();
      return this;
    }

    void addBlind() {
      Blind blind = new Blind();
      blind.setRegisteredAtLaId(ModelValues.LA_CODE);
      application.getEligibility().setBlind(blind);
    }

    ApplicationBuilder setEligibilityBlind() {
      application.setEligibility(new Eligibility());
      addBlind();
      application.getEligibility().setTypeCode(EligibilityCodeField.BLIND);
      return this;
    }

    private ApplicationBuilder setEligibilityAfrfcs() {
      application.setEligibility(new Eligibility());
      application.getEligibility().setTypeCode(EligibilityCodeField.AFRFCS);
      return this;
    }

    private ApplicationBuilder setEligibilityChildVehic() {
      application.setEligibility(new Eligibility());
      application.getEligibility().setTypeCode(EligibilityCodeField.CHILDVEHIC);
      return this;
    }
  }
}
