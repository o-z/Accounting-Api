package com.javacase.accountingapi.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BillingProgressEnum {
  SUCCESS("SUCCESS"),
  NOT_ACCEPTED("NOT_ACCEPTED");


  private String progressStatus;
}
