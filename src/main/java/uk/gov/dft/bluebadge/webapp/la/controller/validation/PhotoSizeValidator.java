package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class PhotoSizeValidator implements ConstraintValidator<PhotoSize, MultipartFile> {

  @Override
  public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
    return value.getSize() > 0;
  }
}
