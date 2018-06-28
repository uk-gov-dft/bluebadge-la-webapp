package uk.gov.dft.bluebadge.webapp.la.client;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    HttpStatus statusCode = response.getStatusCode();
    return HttpStatus.Series.SUCCESSFUL != statusCode.series()
        && HttpStatus.BAD_REQUEST != statusCode;
  }
}
