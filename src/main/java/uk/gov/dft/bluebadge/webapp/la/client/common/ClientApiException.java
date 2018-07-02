package uk.gov.dft.bluebadge.webapp.la.client.common;

import uk.gov.dft.bluebadge.webapp.la.client.common.model.CommonResponse;

public class ClientApiException extends RuntimeException {
  CommonResponse commonResponse;

  ClientApiException(CommonResponse commonResponse) {
    this.commonResponse = commonResponse;
  }

  public CommonResponse getCommonResponse() {
    return commonResponse;
  }
}
