package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.time.format.DateTimeFormatter;

public class ModelViewFormats {

  private ModelViewFormats() {}

  private static final String VIEW_MODEL_DATE_FORMAT = "dd/MM/yy";

  private static final String VIEW_MODEL_DATE_TIME_FORMAT = "dd/MM/yy HH:mm";

  public static final DateTimeFormatter viewModelDateFormatter =
      DateTimeFormatter.ofPattern(VIEW_MODEL_DATE_FORMAT);

  public static final DateTimeFormatter viewModelDateTimeFormatter =
      DateTimeFormatter.ofPattern(ModelViewFormats.VIEW_MODEL_DATE_TIME_FORMAT);
}
