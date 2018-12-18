package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import java.util.Optional;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;

@Data
@Builder
public class FindApplicationFormRequest implements Serializable {
  private Optional<String> searchBy = Optional.empty();

  private Optional<String> searchTerm = Optional.empty();

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
    return !searchTerm.isPresent() || !searchTerm.get().isEmpty();
  }

  public Optional<String> getSearchTerm() {
    return searchTerm.map(StringUtils::trimToNull);
  }
}
