package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.LookupBadgeViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class BadgeToLookupBadgeViewModelTest {

    private static final String BADGE_NUMBER = "AAAAA1";
    private static final LocalDate EXPIRY_DATE = LocalDate.of(2099, 7, 9);
    private static final String STATUS = "NEW";
    private static final String EXPIRY_DATE_VIEW_MODEL = "09 July 2099";
    private static final String STATUS_VIEW_MODEL = "new";

    private static final Badge BADGE =
            new Badge()
                    .badgeNumber(BADGE_NUMBER)
                    .expiryDate(EXPIRY_DATE)
                    .statusCode(STATUS);

    private static final LookupBadgeViewModel VIEW_MODEL =
            LookupBadgeViewModel.builder()
                    .badgeNumber(BADGE_NUMBER)
                    .expiryDate(EXPIRY_DATE_VIEW_MODEL)
                    .status(STATUS_VIEW_MODEL)
                    .build();

    @Mock ReferenceDataService referenceDataServiceMock;

    private BadgeToLookupBadgeViewModel converter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        converter = new BadgeToLookupBadgeViewModel(referenceDataServiceMock);
        when(referenceDataServiceMock.retrieveBadgeStatusDisplayValue(STATUS))
                .thenReturn(STATUS_VIEW_MODEL);
    }

    @Test
    public void convert_shouldConvertAServiceModelToAViewModel() {
        LookupBadgeViewModel viewModel = converter.convert(BADGE);
        assertThat(viewModel).isEqualTo(VIEW_MODEL);
    }
}
