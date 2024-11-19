package com.javacase.accountingapi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  INTERNAL_SERVER_ERROR("1000", "Internal server error.", 500),
  FIELD_VALIDATION_ERROR("1001", "Field validation error.", 400),
  PRICE_NOT_POSITIVE("1002", "Price must be positive.", 400),
  BILLING_NOT_FOUND_ERROR("1003", "Billing not found.", 400),
  MANAGER_NOT_FOUND_ERROR("1004", "Manager not found.", 400),
  ACCOUNT_LOCK_ERROR("1005", "Account already locked error.", 400);


  private final String code;
  private final String message;
  private final Integer httpStatusCode;
}
