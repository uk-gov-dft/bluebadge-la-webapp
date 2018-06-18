package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import org.apache.commons.lang3.math.NumberUtils;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.ErrorErrors;

import java.util.Comparator;
import java.util.List;

public class ErrorComparator implements Comparator<ErrorErrors> {

    List<String> errorListOrder;

    public ErrorComparator(List<String> errorListOrder) {

        this.errorListOrder = errorListOrder;

    }


    @Override
    public int compare(ErrorErrors o1, ErrorErrors o2) {
        String field1 = o1.getField();
        String field2 = o2.getField();

        int field1Index = this.errorListOrder.indexOf(field1);
        int field2Index = this.errorListOrder.indexOf(field2);

        return NumberUtils.compare(field1Index, field2Index);
    }
}
