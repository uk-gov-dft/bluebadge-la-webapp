package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData;

public class LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequestTest {

  private LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest converter;

  @Before
  public void setUp() {
    converter = new LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest();
  }

  @Test
  public void convert_shouldWork_whenAllFieldsArePopulated() {
    LocalAuthorityDetailsFormRequest la =
        LocalAuthorityTestData.getLocalAuthorityDetailsFormRequestAllFields();
    la.setDescription(null);
    LocalAuthorityDetailsFormRequest formRequest =
        converter.convert(LocalAuthorityTestData.LOCAL_AUTHORITY_META_DATA_ALL_FIELDS);
    assertThat(formRequest).isEqualTo(la);
  }

  @Test
  public void convert_shouldWork_whenAllFieldsAreEmpty() {
    LocalAuthorityDetailsFormRequest formRequest =
        converter.convert(LocalAuthorityRefData.LocalAuthorityMetaData.builder().build());
    assertThat(formRequest).isEqualTo(LocalAuthorityDetailsFormRequest.builder().build());
  }
}
