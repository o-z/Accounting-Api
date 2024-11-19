package com.javacase.accountingapi.common.exception;

import lombok.Data;

@Data
public class AccountingApiException extends RuntimeException {

  private final String code;
  private final String message;
  private final String errorResponse;
  private final Integer httpStatusCode;

  public AccountingApiException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.errorResponse = null;
    this.httpStatusCode = errorCode.getHttpStatusCode();
  }

  public AccountingApiException(ErrorCode errorCode, String errorResponse) {
    super(errorCode.getMessage());
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.errorResponse = errorResponse;
    this.httpStatusCode = errorCode.getHttpStatusCode();
  }

  public AccountingApiException(String code, String message, Integer httpStatusCode) {
    super(message);
    this.code = code;
    this.message = message;
    this.errorResponse = null;
    this.httpStatusCode = httpStatusCode;
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return null;
  }
}
