package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumbersResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.common.ServiceConfiguration;

@Service
public class BadgeManagementApiClient extends BaseApiClient {

  private static final String BADGES_BASE_ENDPOINT = "badges";

  private static final String ORDER_BADGES_ENDPOINT = "/" + BADGES_BASE_ENDPOINT;

  private RestTemplateFactory restTemplateFactory;
  private ServiceConfiguration serviceConfiguration;

  @Autowired
  public BadgeManagementApiClient(
      RestTemplateFactory restTemplateFactory, ServiceConfiguration badgeManagementApiConfig) {
    this.restTemplateFactory = restTemplateFactory;
    this.serviceConfiguration = badgeManagementApiConfig;
  }

  public List<String> orderBlueBadges(BadgeOrderRequest badgeOrder) {
    Assert.notNull(badgeOrder, "orderBlueBadges - badgeOrder must be set");

    HttpEntity<BadgeOrderRequest> request = new HttpEntity<>(badgeOrder);

    try {
      return Objects.requireNonNull(
              restTemplateFactory
                  .getInstance()
                  .postForObject(
                      serviceConfiguration.getUrlPrefix() + ORDER_BADGES_ENDPOINT,
                      request,
                      BadgeNumbersResponse.class))
          .getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    return null;
  }

  /*
  Creates a builder for a given apiEndpoint using standard configuration
   */
  private UriComponentsBuilder getUriComponentsBuilder(String apiEndpoint) {

    return UriComponentsBuilder.newInstance()
        .host(serviceConfiguration.getHost())
        .scheme(serviceConfiguration.getScheme())
        .port(serviceConfiguration.getPort())
        .path(serviceConfiguration.getContextpath())
        .pathSegment(apiEndpoint);
  }

  /*
  Convenience method to add non-null parameters to an endpoint
   */
  private void addParameterMethod(
      String name, UriComponentsBuilder builder, String queryParamName) {
    if (StringUtils.isNotBlank(name)) {
      builder.queryParam(queryParamName, name);
    }
  }
}
