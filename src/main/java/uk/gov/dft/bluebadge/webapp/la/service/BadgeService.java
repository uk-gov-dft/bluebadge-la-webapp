package uk.gov.dft.bluebadge.webapp.la.service;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.common.service.ImageProcessingUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataCancellationEnum;

@Service
@Slf4j
public class BadgeService {

  private BadgeManagementApiClient badgeManagementApiClient;

  @Autowired
  public BadgeService(BadgeManagementApiClient badgeManagementApiClient) {
    this.badgeManagementApiClient = badgeManagementApiClient;
  }

  public String orderABadgeForAPerson(BadgeOrderRequest badgeOrderRequest) {
    Assert.notNull(badgeOrderRequest, "badgeOrderRequest should not be null");

    List<String> badgeNumbers =
        badgeManagementApiClient.orderBlueBadges(badgeOrderRequest.numberOfBadges(1));

    Assert.notEmpty(badgeNumbers, "badgeNumbers should not be empty");

    return badgeNumbers.get(0);
  }

  public String orderABadgeForAPerson(BadgeOrderRequest badgeOrderRequest, byte[] imageByteArray)
      throws IOException {
    Assert.notNull(badgeOrderRequest, "badgeOrderRequest should not be null");
    Assert.notNull(imageByteArray, "image byte array cannot be null");

    InputStream stream = new ByteArrayInputStream(imageByteArray);
    BufferedImage bufferedImage = ImageIO.read(stream);
    String base64 = ImageProcessingUtils.getBase64FromBufferedImage(bufferedImage);
    badgeOrderRequest.setImageFile(base64);

    return orderABadgeForAPerson(badgeOrderRequest);
  }

  public Optional<Badge> retrieve(String badgeNumber) {
    if (StringUtils.isEmpty(badgeNumber)) {
      return Optional.empty();
    }
    try {
      return Optional.of(badgeManagementApiClient.retrieveBadge(badgeNumber));
    } catch (NotFoundException ex) {
      log.debug("Badge number:{} could not be found!", badgeNumber);
      return Optional.empty();
    }
  }

  public List<BadgeSummary> findBadgeByPostcode(String postcode) {
    if (StringUtils.isEmpty(postcode)) {
      return Lists.newArrayList();
    }
    return badgeManagementApiClient.findBadgeByPostCode(postcode);
  }

  public List<BadgeSummary> findBadgeByName(String name) {
    if (StringUtils.isEmpty(name)) {
      return Lists.newArrayList();
    }
    return badgeManagementApiClient.findBadgeByName(name);
  }

  public void cancelBadge(String badgeNumber, RefDataCancellationEnum reason) {
    Assert.notNull(badgeNumber, "Badge number should not be null");
    Assert.notNull(reason, "cancellation reason should not be null");
    badgeManagementApiClient.cancelBadge(badgeNumber, reason.getValue());
  }
}
