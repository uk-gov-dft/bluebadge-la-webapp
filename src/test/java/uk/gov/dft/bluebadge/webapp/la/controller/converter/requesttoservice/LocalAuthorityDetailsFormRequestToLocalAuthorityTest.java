package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData;

public class LocalAuthorityDetailsFormRequestToLocalAuthorityTest
    implements LocalAuthorityTestData {

  private LocalAuthorityDetailsFormRequestToLocalAuthority converter;

  @Before
  public void setUp() {
    converter = new LocalAuthorityDetailsFormRequestToLocalAuthority();
  }

  @Test
  public void convert_shouldWork_whenAllFieldsArePopulated() {
    LocalAuthority localAuthority =
        converter.convert(
            LocalAuthorityTestData.LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_ALL_FIELDS.build());
    assertThat(localAuthority).isEqualTo(LOCAL_AUTHORITY_ALL_FIELDS);
  }

  @Test
  public void convert_shouldWork_whenAllFieldsAreEmpty() {
    LocalAuthority localAuthority =
        converter.convert(LocalAuthorityTestData.LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_EMPTY_FIELDS);
    assertThat(localAuthority).isEqualTo(LOCAL_AUTHORITY_EMPTY);
  }
}
