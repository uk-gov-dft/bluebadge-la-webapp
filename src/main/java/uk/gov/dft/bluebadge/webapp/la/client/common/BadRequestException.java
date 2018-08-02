package uk.gov.dft.bluebadge.webapp.la.client.common;

import uk.gov.dft.bluebadge.common.api.model.CommonResponse;

public class BadRequestException extends ClientApiException {
  public BadRequestException(CommonResponse commonResponse) {
    super(commonResponse);
  }
}
