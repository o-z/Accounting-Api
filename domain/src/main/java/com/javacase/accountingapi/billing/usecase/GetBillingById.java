package com.javacase.accountingapi.billing.usecase;

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
public class GetBillingById implements UseCase, Serializable {

  private Long id;
}
