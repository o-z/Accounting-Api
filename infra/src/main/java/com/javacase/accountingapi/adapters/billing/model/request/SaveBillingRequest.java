package com.javacase.accountingapi.adapters.billing.model.request;


import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.exception.AccountingApiException;
import com.javacase.accountingapi.common.exception.ErrorCode;
import com.javacase.accountingapi.common.model.dto.AmountDto;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveBillingRequest implements Serializable {

  @NotNull
  private Long accountingManagerId;
  @NotNull
  private AmountDto amount;
  @NotNull
  private String productName;
  @NotNull
  private String billNo;

  public SaveBilling toModel() {
    return SaveBilling.builder()
        .accountingManagerId(accountingManagerId)
        .amount(amount)
        .productName(productName)
        .billNo(billNo)
        .build();
  }

  public void setAmount(AmountDto amount) {
    if (amount.getPrice().compareTo(BigDecimal.ZERO) < 0) {
      throw new AccountingApiException(ErrorCode.PRICE_NOT_POSITIVE);
    }
    this.amount = amount;
  }
}
