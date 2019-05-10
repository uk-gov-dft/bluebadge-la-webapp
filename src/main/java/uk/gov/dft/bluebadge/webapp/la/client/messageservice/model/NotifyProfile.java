package uk.gov.dft.bluebadge.webapp.la.client.messageservice.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class NotifyProfile {
  private String apiKey;
  private Map<TemplateName, String> templates;

  String getTemplate(TemplateName templateName) {
    if (null == templates) {
      return null;
    }

    return templates.get(templateName);
  }
}
