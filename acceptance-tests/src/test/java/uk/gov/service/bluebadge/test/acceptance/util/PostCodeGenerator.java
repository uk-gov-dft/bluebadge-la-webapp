package uk.gov.service.bluebadge.test.acceptance.util;

import java.util.Random;

public class PostCodeGenerator {
  private Random rand;

  private static final String[] POSTCODES = {
    "BT51 5YG",
    "ST4 3JL",
    "SN1 2AL",
    "CV32 6AG",
    "EH28 8LU",
    "OX16 5AB",
    "GL5 3BB",
    "EX2 8YS",
    "LS29 9AD",
    "S63 7FU",
    "CH2 2BF",
    "BH24 2SH",
    "CF3 0BL",
    "BT37 9RJ",
    "GU10 3LN",
    "SW19 8EF",
    "WA7 4SP",
    "ML8 5UA",
    "ME7 5UZ",
    "LL18 5LR",
    "EN5 4SJ",
    "LE17 9EN",
    "CF37 1LH",
    "PO14 1DU",
    "CT10 2HE",
    "S75 6NR",
    "SY16 4AX",
    "PE19 7AW",
    "DL10 9TD",
    "CT1 1WZ",
    "MK12 5BX",
    "BB10 3JS",
    "ML2 0BD",
    "OL8 4DT",
    "SK4 4BP",
    "BT81 7NH",
    "BN27 1EX",
    "PH2 7SZ",
    "ST3 7RZ",
    "AB39 2BT",
    "BT39 0BH",
    "W14 9NN",
    "HS2 0LF",
    "CV37 9SX",
    "CV3 1NZ",
    "GL54 2DZ",
    "CH7 3NE",
    "L39 5EW",
    "OX1 3TH",
    "WF6 1LL"
  };

  public PostCodeGenerator() {
    rand = new Random();
  }

  public String get_postcode() {
    return POSTCODES[rand.nextInt(POSTCODES.length)];
  }
}
