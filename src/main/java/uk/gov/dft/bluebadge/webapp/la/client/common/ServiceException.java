package uk.gov.dft.bluebadge.webapp.la.client.common;

class ServiceException extends RuntimeException {
  ServiceException(String s, Exception e) {
    super(s, e);
  }
}
