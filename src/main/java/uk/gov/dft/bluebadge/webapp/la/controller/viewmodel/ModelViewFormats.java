package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class ModelViewFormats implements Serializable {

  private ModelViewFormats() {}

  private static final String VIEW_MODEL_DATE_FORMAT = "dd/MM/yy";

  private static final String VIEW_MODEL_FIELD_DATE_FORMAT = "dd MMMM yyyy";

  private static final String VIEW_MODEL_GRID_DATE_TIME_FORMAT = "dd/MM/yy HH:mm";

  private static final String VIEW_MODEL_FIELD_DATE_TIME_FORMAT = "dd MMMM yyyy 'at' HH:mm";

  public static final DateTimeFormatter viewModelDateFormatter =
      DateTimeFormatter.ofPattern(VIEW_MODEL_DATE_FORMAT);

  public static final DateTimeFormatter viewModelGridDateTimeFormatter =
      DateTimeFormatter.ofPattern(ModelViewFormats.VIEW_MODEL_GRID_DATE_TIME_FORMAT);

  public static final DateTimeFormatter viewModelFieldDateTimeFormatter =
      DateTimeFormatter.ofPattern(ModelViewFormats.VIEW_MODEL_FIELD_DATE_TIME_FORMAT);

  public static final DateTimeFormatter viewModelFieldDateFormatter =
      DateTimeFormatter.ofPattern(ModelViewFormats.VIEW_MODEL_FIELD_DATE_FORMAT);
}
