package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;

public class BadgeEntityToFindBadgeSearchResultViewModelTest {

  private static final String BADGE_NUMBER = "AAAAA1";
  private static final String BADGE_HOLDER_NAME = "MyName";
  private static final LocalDate EXPIRY_DATE = LocalDate.of(2099, 7, 9);
  private static final int LOCAL_AUTHORITY_ID = 2;
  private static final String POSTCODE = "AAA AAA";
  private static final String STATUS = "NEW";

  private static final String EXPIRY_DATE_VIEW_MODEL = "09/07/99";

  private static final Badge BADGE =
      new Badge()
          .badgeNumber(BADGE_NUMBER)
          .party(
              new Party()
                  .person(new Person().badgeHolderName(BADGE_HOLDER_NAME))
                  .contact(new Contact().postCode(POSTCODE)))
          .localAuthorityId(LOCAL_AUTHORITY_ID)
          .expiryDate(EXPIRY_DATE)
          .statusCode(STATUS);

  BadgeEntityToFindBadgeSearchResultViewModel converter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    converter = new BadgeEntityToFindBadgeSearchResultViewModel();
  }

  @Test
  public void convert_shouldConvertAServiceModelToAViewModel() {
    FindBadgeSearchResultViewModel expectedViewModel =
        FindBadgeSearchResultViewModel.builder()
            .badgeNumber(BADGE_NUMBER)
            .expiryDate(EXPIRY_DATE_VIEW_MODEL)
            .localAuthority(String.valueOf(LOCAL_AUTHORITY_ID))
            .name(BADGE_HOLDER_NAME)
            .postCode(POSTCODE)
            .status(STATUS)
            .build();
    FindBadgeSearchResultViewModel viewModel = converter.convert(BADGE);
    assertThat(viewModel).isEqualTo(expectedViewModel);
  }
}
