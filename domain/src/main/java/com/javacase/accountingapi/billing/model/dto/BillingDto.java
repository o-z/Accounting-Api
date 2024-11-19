package com.javacase.accountingapi.billing.model.dto;

import com.javacase.accountingapi.common.model.dto.AmountDto;
import com.javacase.accountingapi.common.model.dto.BaseDto;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BillingDto extends BaseDto implements Serializable {

  private Long accountingManagerId;
  private AmountDto amountDto;
  private String productName;
  private String billNo;
  private BillingProgressEnum progressStatus;

}
