package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class ModelViewFormats implements Serializable {

  private ModelViewFormats() {}

  private static final String VIEW_MODEL_FIELD_DATE_FORMAT = "dd MMMM yyyy";

  private static final String VIEW_MODEL_GRID_DATE_FORMAT = "d MMM yyyy";

  private static final String VIEW_MODEL_GRID_DATE_TIME_FORMAT = "d MMM yyyy 'at' HH:mm";

  private static final String VIEW_MODEL_FIELD_DATE_TIME_FORMAT = "dd MMMM yyyy 'at' HH:mm";

  public static final DateTimeFormatter viewModelFieldDateFormatter =
      DateTimeFormatter.ofPattern(ModelViewFormats.VIEW_MODEL_FIELD_DATE_FORMAT);

  public static final DateTimeFormatter viewModelGridDateFormatter =
      DateTimeFormatter.ofPattern(ModelViewFormats.VIEW_MODEL_GRID_DATE_FORMAT);

  public static final DateTimeFormatter viewModelGridDateTimeFormatter =
      DateTimeFormatter.ofPattern(ModelViewFormats.VIEW_MODEL_GRID_DATE_TIME_FORMAT);

  public static final DateTimeFormatter viewModelFieldDateTimeFormatter =
      DateTimeFormatter.ofPattern(ModelViewFormats.VIEW_MODEL_FIELD_DATE_TIME_FORMAT);
}
