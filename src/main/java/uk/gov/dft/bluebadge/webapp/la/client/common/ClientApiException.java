package uk.gov.dft.bluebadge.webapp.la.client.common;

import java.util.List;
import java.util.stream.Collectors;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;

public class ClientApiException extends RuntimeException {
  final transient CommonResponse commonResponse;

  public ClientApiException(CommonResponse commonResponse) {
    super(extractMessage(commonResponse));
    this.commonResponse = commonResponse;
  }

  public CommonResponse getCommonResponse() {
    return commonResponse;
  }

  private static String extractMessage(CommonResponse cr) {
    if (null == cr || null == cr.getError()) {
      return null;
    }

    StringBuilder tmp = new StringBuilder();
    if (null != cr.getError().getMessage()) {
      tmp.append(cr.getError().getMessage());
    }
    if (null != cr.getError().getReason()) {
      tmp.append(": ");
      tmp.append(cr.getError().getReason());
    }

    String result = tmp.toString();
    List<ErrorErrors> errors = cr.getError().getErrors();
    if (null != errors && !errors.isEmpty()) {
      result =
          errors
              .stream()
              .map(ErrorErrors::getMessage)
              .collect(Collectors.joining(", ", result + ": ", ""));
    }
    return result;
  }
}
