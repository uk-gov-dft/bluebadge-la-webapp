package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class ApplicationToApplicationViewModelTest extends ApplicationTestData {
  @Mock ReferenceDataService referenceDataServiceMock;

  ApplicationToApplicationViewModel converter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(referenceDataServiceMock.retrieveApplicationGenderDisplayValue(GENDER_SHORTCODE))
        .thenReturn(GENDER_VIEW_MODEL);
    when(referenceDataServiceMock.retrieveApplicationEligibilityDisplayValue(
            ELIGIBILITY_SHORT_CODE))
        .thenReturn(ELIGIBILITY_VIEW_MODEL);
    when(referenceDataServiceMock.retrieveApplicationWalkingDifficultyDisplayValue(
            WALKING_DIFFICULTY_1_SHORTCODE))
        .thenReturn(WALKING_DIFFICULTY_1);
    when(referenceDataServiceMock.retrieveApplicationWalkingDifficultyDisplayValue(
            WALKING_DIFFICULTY_2_SHORTCODE))
        .thenReturn(WALKING_DIFFICULTY_2);
    when(referenceDataServiceMock.retrieveApplicationWalkingSpeedDisplayValue(
            WALKING_SPEED_SHORT_CODE.name()))
        .thenReturn(WALKING_SPEED_VIEW_MODEL);
    converter =
        new ApplicationToApplicationViewModel(
            referenceDataServiceMock, new PartyToAddressViewModel());
  }

  @Test
  public void convert_shouldConvertAServiceModelToAViewModel() {
    ApplicationViewModel viewModel = converter.convert(APPLICATION_FOR_PERSON_WALKING_DIFFICULTIES);
    assertThat(viewModel).isEqualTo(APPLICATION_VIEW_MODEL_FOR_PERSON_WALKING_DIFFICULTIES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convert_shouldThrowIllegalArgumentException_whenApplicationIsNull() {
    converter.convert(null);
  }
}
