package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData;

public class LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequestTest
    implements LocalAuthorityTestData {

  private LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest converter;

  @Before
  public void setUp() {
    converter = new LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest();
  }

  @Test
  public void convert_shouldWork_whenAllFieldsArePopulated() {
    LocalAuthorityDetailsFormRequest expected =
        LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_ALL_FIELDS.build();
    expected.setDescription(null);
    LocalAuthorityDetailsFormRequest formRequest =
        converter.convert(LocalAuthorityTestData.LOCAL_AUTHORITY_META_DATA_ALL_FIELDS);
    assertThat(formRequest).isEqualTo(expected);
  }

  @Test
  public void convert_shouldWork_whenAllFieldsAreEmpty() {
    LocalAuthorityDetailsFormRequest formRequest =
        converter.convert(LocalAuthorityTestData.LOCAL_AUTHORITY_META_DATA_EMPTY_FIELDS);
    assertThat(formRequest).isEqualTo(LOCAL_AUTHORITY_DETAILS_FORM_REQUEST_EMPTY_FIELDS);
  }
}
