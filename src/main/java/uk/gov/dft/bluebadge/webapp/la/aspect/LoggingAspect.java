package uk.gov.dft.bluebadge.webapp.la.aspect;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  @AfterReturning("execution(* uk.gov.dft.bluebadge..*.*(..))")
  public void logMethodAccessAfter(JoinPoint joinPoint) {
    logger.debug("***** Completed: {} ***** ", joinPoint.getSignature().getName());
  }

  @Before("execution(* uk.gov.dft.bluebadge..*.*(..))")
  public void logMethodAccessBefore(JoinPoint joinPoint) {
    if (!logger.isDebugEnabled()) {
      return;
    }

    CodeSignature signature = (CodeSignature) joinPoint.getSignature();

    List<String> parameterNames = Arrays.asList(signature.getParameterNames());
    List<String> parameterValues = Lists.newArrayList();

    if (joinPoint.getArgs() != null) {
      for (Object arg : joinPoint.getArgs()) {
        parameterValues.add(String.valueOf(arg));
      }
    }
    StringBuilder paramDebugInfo = new StringBuilder();
    Iterator<String> paramNamesIterator = parameterNames.iterator();
    Iterator<String> paramValuesIterator = parameterValues.iterator();

    while (paramNamesIterator.hasNext() && paramValuesIterator.hasNext()) {
      String parameterName = paramNamesIterator.next();
      String parameterValue = paramValuesIterator.next();
      paramDebugInfo.append(parameterName).append(": ").append(parameterValue).append(", ");
    }
    String declaringType = signature.getDeclaringTypeName();
    String declaringTypeName = "";
    if (declaringType != null) {
      declaringTypeName = declaringType.substring(declaringType.lastIndexOf(".") + 1);
    }

    logger.debug(
        "***** Starting: {}.{} with *****",
        declaringTypeName,
        signature.getName(),
        paramDebugInfo.toString(),
        signature.toString());
  }
}
