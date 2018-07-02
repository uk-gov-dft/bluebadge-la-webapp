package uk.gov.dft.bluebadge.webapp.la.client.common;

import uk.gov.dft.bluebadge.webapp.la.client.common.model.CommonResponse;

public class NotFoundException extends ClientApiException {
  NotFoundException(CommonResponse commonResponse) {
    super(commonResponse);
  }
}
