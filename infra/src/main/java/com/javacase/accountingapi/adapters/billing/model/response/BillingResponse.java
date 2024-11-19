package com.javacase.accountingapi.adapters.billing.model.response;

import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.common.model.dto.AmountDto;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponse implements Serializable {

  private Long id;
  private Long accountingManagerId;
  private AmountDto amountDto;
  private String productName;
  private String billNo;
  private BillingProgressEnum progressStatus;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;


  public static BillingResponse from(BillingDto billingDto) {
    return BillingResponse.builder()
        .id(billingDto.getId())
        .accountingManagerId(billingDto.getAccountingManagerId())
        .amountDto(billingDto.getAmountDto())
        .productName(billingDto.getProductName())
        .billNo(billingDto.getBillNo())
        .progressStatus(billingDto.getProgressStatus())
        .createDate(billingDto.getCreateDate())
        .updateDate(billingDto.getUpdateDate())
        .build();
  }
}
