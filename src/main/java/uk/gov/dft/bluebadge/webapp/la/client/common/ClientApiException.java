package uk.gov.dft.bluebadge.webapp.la.client.common;

import uk.gov.dft.bluebadge.common.api.model.CommonResponse;

public class ClientApiException extends RuntimeException {
  final transient CommonResponse commonResponse;

  public ClientApiException(CommonResponse commonResponse) {
    this.commonResponse = commonResponse;
  }

  public CommonResponse getCommonResponse() {
    return commonResponse;
  }
}
