package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.common.service.ImageProcessingUtils;
import uk.gov.dft.bluebadge.common.service.exception.InternalServerException;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppContact;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppPerson;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.config.GeneralConfig;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;

@Component
public class ApplicationToOrderBadgePersonDetailsFormRequest
    implements Converter<Application, OrderBadgePersonDetailsFormRequest> {

  private GeneralConfig generalConfig;

  @Autowired
  ApplicationToOrderBadgePersonDetailsFormRequest(GeneralConfig generalConfig) {
    this.generalConfig = generalConfig;
  }

  @Override
  public OrderBadgePersonDetailsFormRequest convert(Application source) {
    Assert.notNull(source, "Source cannot be null");

    AppPerson appPerson = source.getParty().getPerson();
    AppContact appContact = source.getParty().getContact();
    LocalDate dob = appPerson.getDob();
    byte[] photoBytes = null;
    String thumbnail = null;
    if (null != source.getBadgePhotoArtifacts() && !source.getBadgePhotoArtifacts().isEmpty()) {
      String photoUrl = source.getBadgePhotoArtifacts().get(0).getLink();
      try {
        URL url = new URL(photoUrl);

        photoBytes = IOUtils.toByteArray(url);
        BufferedImage sourceImageBuffer = ImageIO.read(url);

        InputStream stream =
            ImageProcessingUtils.getInputStreamForSizedBufferedImage(
                sourceImageBuffer, generalConfig.getThumbnailHeight());

        thumbnail =
            "data:image/jpeg;base64, "
                + Base64.getEncoder().encodeToString(IOUtils.toByteArray(stream));
      } catch (IOException e) {
        throw new InternalServerException(e);
      }
    }
    return OrderBadgePersonDetailsFormRequest.builder()
        // Personal data
        .name(appPerson.getBadgeHolderName())
        .nino(appPerson.getNino())
        .gender(appPerson.getGenderCode().toString())
        .eligibility(source.getEligibility().getTypeCode().toString())
        .dobDay(dob.getDayOfMonth())
        .dobMonth(dob.getMonthValue())
        .dobYear(dob.getYear())
        // Address
        .buildingAndStreet(appContact.getBuildingStreet())
        .optionalAddressField(appContact.getLine2())
        .townOrCity(appContact.getTownCity())
        .postcode(appContact.getPostCode())
        // Contact details
        .contactDetailsName(appContact.getFullName())
        .contactDetailsContactNumber(appContact.getPrimaryPhoneNumber())
        .contactDetailsSecondaryContactNumber(appContact.getSecondaryPhoneNumber())
        .contactDetailsEmailAddress(appContact.getEmailAddress())
        // Photo
        .byteImage(photoBytes)
        .thumbBase64(thumbnail)
        .build();
  }
}
