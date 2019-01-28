package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ApplicationToOrderBadgeIndexFormRequestTest extends ApplicationToOrderBadgeBaseFormRequestTest {

    private ApplicationToOrderBadgeIndexFormRequest converter;

    @Before
    public void setUp() {
        converter = new ApplicationToOrderBadgeIndexFormRequest();
    }

    @Test
    public void convert_shouldWork() {
        OrderBadgeIndexFormRequest formRequest = converter.convert(APPLICATION);
        assertThat(formRequest).isEqualTo(FORM_REQUEST_INDEX);
    }
}
