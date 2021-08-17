package com.connection.api.exception;

public class ExceptionCentral extends RuntimeException {
  public ExceptionCentral(Exception cause) {
    super(cause);
  }
}
