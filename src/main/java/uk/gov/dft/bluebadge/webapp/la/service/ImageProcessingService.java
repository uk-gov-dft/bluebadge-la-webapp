package uk.gov.dft.bluebadge.webapp.la.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageProcessingService {

  public BufferedImage reSizeImage(BufferedImage buffer, Dimension dimension) {

    BufferedImage outputBuffer =
        new BufferedImage(dimension.width, dimension.height, BufferedImage.SCALE_SMOOTH);

    Graphics2D g2d = outputBuffer.createGraphics();
    g2d.drawImage(buffer, 0, 0, dimension.width, dimension.height, null);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.dispose();

    return outputBuffer;
  }

  public String convertImageBufferToBase64(BufferedImage buffer) {

    String base64String = null;

    try {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(buffer, "JPG", os);
      base64String = Base64.getEncoder().encodeToString(os.toByteArray());
    } catch (IOException e) {
      log.error("Failed to convert image to base64", e);
    }

    return base64String;
  }

  public Dimension calculateWidthBasedOnHeight(double width, double height, int newHeight) {
    double ratio = height / width;
    int newWidth = (int) (newHeight / ratio);
    return new Dimension(newWidth, newHeight);
  }
}
