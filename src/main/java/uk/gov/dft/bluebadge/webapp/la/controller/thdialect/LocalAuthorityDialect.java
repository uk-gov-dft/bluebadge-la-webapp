package uk.gov.dft.bluebadge.webapp.la.controller.thdialect;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class LocalAuthorityDialect extends AbstractDialect implements IExpressionObjectDialect {

  private final IExpressionObjectFactory localAuthorityExpressionFactory =
      new LocalAuthorityExpressionFactory();

  public LocalAuthorityDialect() {
    super("la");
  }

  @Override
  public IExpressionObjectFactory getExpressionObjectFactory() {
    return localAuthorityExpressionFactory;
  }
}
