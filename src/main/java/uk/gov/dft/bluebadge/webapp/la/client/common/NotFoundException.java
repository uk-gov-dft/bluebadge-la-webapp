package uk.gov.dft.bluebadge.webapp.la.client.common;

import uk.gov.dft.bluebadge.common.api.model.CommonResponse;

public class NotFoundException extends ClientApiException {
  public NotFoundException(CommonResponse commonResponse) {
    super(commonResponse);
  }
}
