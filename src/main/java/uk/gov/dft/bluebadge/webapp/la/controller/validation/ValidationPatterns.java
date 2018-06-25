package uk.gov.dft.bluebadge.webapp.la.controller.validation;

public class ValidationPatterns {
  public static final String EMAIL =
      "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

  public static final String NAME = "^[\\p{L} \\.'\\-]+$";

  public static final String NINO =
      "^(?!BG)(?!GB)(?!NK)(?!KN)(?!TN)(?!NT)(?!ZZ)(?:[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z])(?:\\s*\\d\\s*){6}([A-D]|\\s)$";

  public static final String NINO_CASE_INSENSITIVE = "(?i)" + NINO;

  public static final String PHONE_NUMBER =
      "^(((\\+44\\s?\\d{4}|\\(?0\\d{4}\\)?)\\s?\\d{3}\\s?\\d{3})|"
          + "((\\+44\\s?\\d{3}|\\(?0\\d{3}\\)?)\\s?\\d{3}\\s?\\d{4})|"
          + "((\\+44\\s?\\d{2}|\\(?0\\d{2}\\)?)\\s?\\d{4}\\s?\\d{4}))(\\s?\\#(\\d{4}|\\d{3}))?$";

  public static final String POSTCODE =
      "^([Gg][Ii][Rr] 0[Aa]{2})|"
          + "((([A-Za-z][0-9]{1,2})|"
          + "(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|"
          + "(([AZa-z][0-9][A-Za-z])|"
          + "([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z]))))[\\s]*[0-9][A-Za-z]{2})$";

  public static final String POSTCODE_CASE_INSENSITIVE = "(?i)" + POSTCODE;
}
