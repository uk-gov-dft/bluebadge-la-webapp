package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;

@Service
public class BadgeManagementApiClient {

  class Endpoints {
    // static final String GET_USER_BY_EMAIL_ENDPOINT = "/users?emailAddress={emailAddress}";
    // static final String CREATE_ENDPOINT = "/authorities/{authorityId}/users";
  }

  private RestTemplateFactory restTemplateFactory;
  // private ServiceConfiguration serviceConfiguration;

  @Autowired
  public BadgeManagementApiClient(RestTemplateFactory restTemplateFactory) {
    // this.serviceConfiguration = serviceConfiguration;
    this.restTemplateFactory = restTemplateFactory;
  }

  public List<BadgeSummary> findBadges(String name, String niNumber, String badgeNumber) {

    // TODO mock version.
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

    ResponseEntity<BadgesResponse> response =
        restTemplateFactory
            .getInstance()
            .exchange(
                "http://virtserver.swaggerhub.com:80/uk-gov-dft/blue-badge/1.0.0/badges?badgeNumber=ddd&name=ff&ni=gg",
                HttpMethod.GET,
                entity,
                BadgesResponse.class);
    return response.getBody().getData();
  }

  public Badge retrieveBadge(String badgeNumber) {

    // TODO mock version.
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

    ResponseEntity<BadgeResponse> response =
        restTemplateFactory
            .getInstance()
            .exchange(
                "http://virtserver.swaggerhub.com:80/uk-gov-dft/blue-badge/1.0.0/badges/abc",
                HttpMethod.GET,
                entity,
                BadgeResponse.class,
                "abc");
    return response.getBody().getData();
  }
}
