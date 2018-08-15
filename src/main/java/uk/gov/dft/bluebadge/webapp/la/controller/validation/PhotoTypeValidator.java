package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class PhotoTypeValidator implements ConstraintValidator<PhotoType, MultipartFile> {

  private String[] allowedFileTypes =
      new String[] {"image/jpg", "image/jpeg", "image/png", "image/gif"};

  @Override
  public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
    return Arrays.asList(allowedFileTypes).contains(value.getContentType().toLowerCase());
  }
}
