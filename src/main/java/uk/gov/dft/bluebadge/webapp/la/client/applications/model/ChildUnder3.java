package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class ChildUnder3 {
  private BulkyMedicalEquipmentTypeCodeField bulkyMedicalEquipmentTypeCode;
  private List<BulkyMedicalEquipmentTypeCodeField> bulkyMedicalEquipmentTypeCodes;
  private String otherMedicalEquipment;
}
