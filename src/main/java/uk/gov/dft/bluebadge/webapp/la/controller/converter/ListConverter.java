package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ListConverter<SOURCE, DESTINATION> {

  public List<DESTINATION> convert(
      List<SOURCE> source, Converter<SOURCE, DESTINATION> oneElementConverter) {
    Assert.notNull(source, "SOURCE cannot be null");
    Assert.notNull(oneElementConverter, "oneElementConverter cannot be null");
    return source
        .stream()
        .map(item -> oneElementConverter.convert(item))
        .collect(Collectors.toList());
  }
}
