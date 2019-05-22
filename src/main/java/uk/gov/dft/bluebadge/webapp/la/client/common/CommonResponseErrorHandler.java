package uk.gov.dft.bluebadge.webapp.la.client.common;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;

@Slf4j
public class CommonResponseErrorHandler extends DefaultResponseErrorHandler {

  private final ObjectMapper om;

  public CommonResponseErrorHandler(ObjectMapper objectMapper) {
    this.om = objectMapper;
  }

  @Override
  public void handleError(ClientHttpResponse httpResponse) throws IOException {
    try {
      if (httpResponse.getStatusCode().equals(BAD_REQUEST)) {
        CommonResponse c = om.readValue(httpResponse.getBody(), CommonResponse.class);
        throw new BadRequestException(c);
      } else if (httpResponse.getStatusCode().equals(NOT_FOUND)) {
        CommonResponse c = om.readValue(httpResponse.getBody(), CommonResponse.class);
        throw new NotFoundException(c);
      }
    } catch (IOException e) {
      log.error(
          "Could not parse {} response. Falling back to default handling",
          httpResponse.getStatusCode(),
          e);
    }

    super.handleError(httpResponse);
  }
}
