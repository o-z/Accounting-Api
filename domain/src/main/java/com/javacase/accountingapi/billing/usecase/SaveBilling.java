package com.javacase.accountingapi.billing.usecase;

import com.javacase.accountingapi.common.model.dto.AmountDto;
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
public class SaveBilling implements UseCase, Serializable {

  private Long accountingManagerId;
  private AmountDto amount;
  private String productName;
  private String billNo;
}
