package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;

@Data
@Builder
@SuppressWarnings("squid:S1948")
public class FindApplicationFormRequest implements Serializable {
  private transient String searchBy;

  private String searchTerm;

  private ApplicationTypeCodeField applicationTypeCode;

  @Min(1)
  private Integer pageNum = 1;

  @Max(50)
  @Min(1)
  private Integer pageSize = 50;

  public PagingInfo getPagingInfo() {
    PagingInfo pageInfo = new PagingInfo();
    pageInfo.setPageSize(pageSize);
    pageInfo.setPageNum(pageNum);

    return pageInfo;
  }

  public boolean isSearchTermEmpty() {
    return StringUtils.isBlank(searchTerm);
  }

  public String getSearchTerm() {
    return StringUtils.trimToNull(searchTerm);
  }
}
