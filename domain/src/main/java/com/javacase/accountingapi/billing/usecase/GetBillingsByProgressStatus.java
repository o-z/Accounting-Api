package com.javacase.accountingapi.billing.usecase;

import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import com.javacase.accountingapi.common.usecase.UseCase;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBillingsByProgressStatus implements UseCase, Serializable {

  private BillingProgressEnum progressStatus;
  private Long accountingManagerId;
}
