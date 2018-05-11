package uk.gov.service.bluebadge.test.acceptance.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class FormatHelper {

    public static String formatInstant(final Instant nominalPublicationDate, final String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(nominalPublicationDate.toEpochMilli()));
    }
}
