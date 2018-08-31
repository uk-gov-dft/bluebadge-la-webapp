package uk.gov.dft.bluebadge.webapp.la.service.referencedata;

@SuppressWarnings("SpellCheckingInspection")
public enum RefDataGroupEnum {
  ELIGIBILITY("ELIGIBILIT"),
  APP_SOURCE("APPSOURCE"),
  PARTY("PARTY"),
  STATUS("STATUS"),
  DELIVER_TO("DELIVER"),
  DELIVERY_OPTIONS("DELOP"),
  GENDER("GENDER"),
  LA("LA"),
  CANCEL("CANCEL"),
  WALKING_DIFFICULTIES("WALKDIFF"),
  WALKING_SPEED("WALKSPEED");

  public String getGroupKey() {
    return groupKey;
  }

  private final String groupKey;

  RefDataGroupEnum(String groupKey) {

    this.groupKey = groupKey;
  }
}
