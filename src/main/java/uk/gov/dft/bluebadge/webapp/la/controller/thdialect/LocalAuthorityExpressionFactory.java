package uk.gov.dft.bluebadge.webapp.la.controller.thdialect;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class LocalAuthorityExpressionFactory implements IExpressionObjectFactory {

  private static final String LA_EVALUATION_VARIABLE_NAME = "la";

  private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES =
      Collections.unmodifiableSet(new HashSet<>(Arrays.asList(LA_EVALUATION_VARIABLE_NAME)));

  public LocalAuthorityExpressionFactory() {
    super();
  }

  @Override
  public Set<String> getAllExpressionObjectNames() {
    return ALL_EXPRESSION_OBJECT_NAMES;
  }

  @Override
  public Object buildObject(IExpressionContext context, String expressionObjectName) {
    if (LA_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
      return new LocalAuthorityThymeleafUtils();
    }
    return null;
  }

  @Override
  public boolean isCacheable(String expressionObjectName) {
    return true;
  }
}
