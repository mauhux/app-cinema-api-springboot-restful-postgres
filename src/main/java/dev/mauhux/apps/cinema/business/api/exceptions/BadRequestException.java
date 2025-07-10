package dev.mauhux.apps.cinema.business.api.exceptions;

public class BadRequestException extends RuntimeException {

  private static final String DESCRIPTION = "Bad Request Exception";

  public BadRequestException(String detail) {
    super(DESCRIPTION + ". " + detail);
  }
}
