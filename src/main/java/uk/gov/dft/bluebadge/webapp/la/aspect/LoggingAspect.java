package uk.gov.dft.bluebadge.webapp.la.aspect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
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
    List<String> parameterValues =
        Arrays.asList(joinPoint.getArgs())
            .stream()
            .map(Object::toString)
            .collect(Collectors.toList());
    StringBuilder paramDebugInfo = new StringBuilder();
    Iterator<String> paramNamesIterator = parameterNames.iterator();
    Iterator<String> paramValuesIterator = parameterValues.iterator();

    while (paramNamesIterator.hasNext() && paramValuesIterator.hasNext()) {
      String parameterName = paramNamesIterator.next();
      String parameterValue = paramValuesIterator.next();
      paramDebugInfo.append(parameterName).append(": ").append(parameterValue).append(", ");
    }

    logger.debug(
        "***** Starting: {} with *****",
        signature.getName(),
        paramDebugInfo.toString(),
        signature.toString());
  }
}
