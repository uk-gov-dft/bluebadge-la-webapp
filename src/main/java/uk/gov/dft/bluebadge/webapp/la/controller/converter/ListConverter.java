package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListConverter<Source, Destination> {

  public List<Destination> convert(
      List<Source> source, Converter<Source, Destination> oneElementConverter) {
    return source
        .stream()
        .map(item -> oneElementConverter.convert(item))
        .collect(Collectors.toList());
  }
}
