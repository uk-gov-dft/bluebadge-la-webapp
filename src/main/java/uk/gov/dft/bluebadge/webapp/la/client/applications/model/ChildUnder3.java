package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class ChildUnder3 {
  private BulkyMedicalEquipmentTypeCodeField bulkyMedicalEquipmentTypeCode;
  private List<BulkyMedicalEquipmentTypeCodeField> bulkyMedicalEquipmentTypeCodes;
  private String otherMedicalEquipment;
}
