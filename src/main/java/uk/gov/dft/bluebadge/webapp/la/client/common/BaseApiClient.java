package uk.gov.dft.bluebadge.webapp.la.client.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.CommonResponse;

@Slf4j
public abstract class BaseApiClient {

  protected void handleHttpClientException(HttpClientErrorException clientEx) {
    ObjectMapper mapper = new ObjectMapper();
    CommonResponse c;
    try {
      if (clientEx.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
        c = mapper.readValue(clientEx.getResponseBodyAsString(), CommonResponse.class);
        throw new BadRequestException(c);
      } else if (clientEx.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
        c = mapper.readValue(clientEx.getResponseBodyAsString(), CommonResponse.class);
        throw new NotFoundException(c);
      } else {
        throw clientEx;
      }
    } catch (IOException e) {
      log.error("Could not parse 400 response", e);
      // TODO ?
      throw new RuntimeException("Could not parse 400 response", e);
    }
  }
}
