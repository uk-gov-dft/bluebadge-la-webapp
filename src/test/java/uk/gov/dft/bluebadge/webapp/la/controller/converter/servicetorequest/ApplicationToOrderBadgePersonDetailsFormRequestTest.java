package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Artifact;
import uk.gov.dft.bluebadge.webapp.la.config.GeneralConfig;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationToOrderBadgeTestData;

public class ApplicationToOrderBadgePersonDetailsFormRequestTest
    extends ApplicationToOrderBadgeTestData {

  private ApplicationToOrderBadgePersonDetailsFormRequest converter;

  @Before
  public void setUp() {
    GeneralConfig config = new GeneralConfig();
    config.setThumbnailHeight(300);
    converter = new ApplicationToOrderBadgePersonDetailsFormRequest(config);
  }

  @Test
  public void convert_shouldWork() {
    OrderBadgePersonDetailsFormRequest formRequest = converter.convert(getApplication());
    assertThat(formRequest)
        .isEqualTo(
            ApplicationToOrderBadgeTestData.getApplicationToOrderBadgePersonDetailsFormRequest());
  }

  @Test
  public void convert_withImage() {
    Application application = getApplication();
    application.setArtifacts(
        Lists.newArrayList(
            Artifact.builder()
                .type(Artifact.TypeEnum.PHOTO)
                .link(getClass().getResource("/icon-test.jpg").toString())
                .build()));
    OrderBadgePersonDetailsFormRequest formRequest = converter.convert(application);
    assertThat(formRequest.getByteImage().length).isGreaterThan(100);
    assertThat(formRequest.getThumbBase64().length()).isGreaterThan(100);
  }
}
