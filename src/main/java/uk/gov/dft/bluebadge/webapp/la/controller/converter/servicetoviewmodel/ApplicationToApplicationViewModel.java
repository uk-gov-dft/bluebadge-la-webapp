package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ModelViewFormats.viewModelDateFormatter;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Artifacts;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Eligibility;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingAid;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingDifficulty;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
@SuppressWarnings("squid:S2589Boolean")
public class ApplicationToApplicationViewModel
    implements Converter<Application, ApplicationViewModel> {

  private ReferenceDataService referenceDataService;
  private PartyToAddressViewModel partyToAddressViewModel;

  @Autowired
  public ApplicationToApplicationViewModel(
      ReferenceDataService referenceDataService, PartyToAddressViewModel partyToAddressViewModel) {
    this.referenceDataService = referenceDataService;
    this.partyToAddressViewModel = partyToAddressViewModel;
  }

  @Override
  public ApplicationViewModel convert(Application source) {
    Assert.notNull(source, "Source cannot be null");
    ApplicationViewModel.ApplicationViewModelBuilder builder = ApplicationViewModel.builder();
    buildPersonalDetails(builder, source);
    buildProofOfEligibility(builder, source);
    return builder.build();
  }

  private ApplicationViewModel.ApplicationViewModelBuilder buildPersonalDetails(
      ApplicationViewModel.ApplicationViewModelBuilder builder, Application source) {
    String fullName = null;
    String gender = null;
    String dob = null;
    String nino = null;
    String address = null;
    String contactDetailsName = null;
    String contactDetailsPhoneNumber = null;
    String contactDetailsEmail = null;
    String proofOfIdentityUrl = null;
    String proofOfAddressUrl = null;
    String photoUrl = null;

    Party party = source.getParty();
    if (party != null) {
      Person person = party.getPerson();
      if (person != null) {
        fullName =
            (StringUtils.isNotBlank(person.getBadgeHolderName())
                ? person.getBadgeHolderName()
                : null);
        gender = referenceDataService.retrieveApplicationGenderDisplayValue(person.getGenderCode());
        dob = (person.getDob() != null ? person.getDob().format(viewModelDateFormatter) : null);
        nino = person.getNino();
      }
      Contact contact = party.getContact();
      if (contact != null) {
        address = partyToAddressViewModel.convert(contact);
        contactDetailsName = contact.getFullName();
        contactDetailsPhoneNumber = contact.getPrimaryPhoneNumber();
        contactDetailsEmail = contact.getEmailAddress();
      }
    }
    Artifacts artifacts = source.getArtifacts();
    if (artifacts != null) {
      proofOfIdentityUrl = artifacts.getProofOfIdentityUrl();
      proofOfAddressUrl = artifacts.getProofOfAddressUrl();
      photoUrl = artifacts.getBadgePhotoUrl();
    }

    return builder
        .applicationId(source.getApplicationId())
        .fullName(fullName)
        .gender(gender)
        .dob(dob)
        .nino(nino)
        .address(address)
        .contactDetailsName(contactDetailsName)
        .contactDetailsPhoneNumber(contactDetailsPhoneNumber)
        .contactDetailsEmail(contactDetailsEmail)
        .proofOfIdentityUrl(proofOfIdentityUrl)
        .proofOfAddressUrl(proofOfAddressUrl)
        .photoUrl(photoUrl);
  }

  private ApplicationViewModel.ApplicationViewModelBuilder buildProofOfEligibility(
      ApplicationViewModel.ApplicationViewModelBuilder builder, Application source) {
    Eligibility eligibility = source.getEligibility();
    if (eligibility != null) {
      String eligibilityTypeCodeViewModel =
          referenceDataService.retrieveApplicationEligibilityDisplayValue(
              eligibility.getTypeCode().name());
      builder.reasonForApplying(eligibilityTypeCodeViewModel);
      WalkingDifficulty walkingDifficulty = eligibility.getWalkingDifficulty();
      if (walkingDifficulty != null) {
        if (walkingDifficulty.getTypeCodes() != null) {
          List<String> walkingDifficulties =
              walkingDifficulty
                  .getTypeCodes()
                  .stream()
                  .map(
                      key ->
                          referenceDataService.retrieveApplicationWalkingDifficultyDisplayValue(
                              key.name()))
                  .collect(Collectors.toList());
          builder.walkingDifficulties(walkingDifficulties);
        }
        List<WalkingAid> walkingAids = walkingDifficulty.getWalkingAids();
        if (walkingAids != null) {
          List<String> walkingAidsViewModel =
              walkingAids.stream().map(WalkingAid::getDescription).collect(Collectors.toList());
          builder.mobilityAids(walkingAidsViewModel);
        }
        String walkingSpeedViewModel =
            referenceDataService.retrieveApplicationWalkingSpeedDisplayValue(
                walkingDifficulty.getWalkingSpeedCode().name());
        builder.walkingSpeed(walkingSpeedViewModel);
      }
    }
    return builder;
  }
}
