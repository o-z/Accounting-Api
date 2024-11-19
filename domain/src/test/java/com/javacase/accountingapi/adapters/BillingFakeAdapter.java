package com.javacase.accountingapi.adapters;


import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.port.BillingPort;
import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.model.dto.AmountDto;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillingFakeAdapter implements BillingPort {

  private final Map<Long, BillingDto> billings = new HashMap<>();

  @Override
  public BillingDto getBillingById(Long id) {
    return billings.get(id);
  }

  @Override
  public BillingDto saveBilling(SaveBilling saveBilling, BillingProgressEnum progressEnum) {
    BillingDto billingDto = BillingDto.builder()
        .id((long) (billings.size() + 1))
        .accountingManagerId(saveBilling.getAccountingManagerId())
        .amountDto(AmountDto.builder()
            .price(saveBilling.getAmount().getPrice())
            .currency(saveBilling.getAmount().getCurrency())
            .build())
        .productName(saveBilling.getProductName())
        .billNo(saveBilling.getBillNo())
        .progressStatus(progressEnum)
        .createDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .build();
    billings.put((long) (billings.size() + 1), billingDto);
    return billingDto;
  }

  @Override
  public List<BillingDto> getAllByProgressStatusAndManagerId(BillingProgressEnum progressStatus,
      Long accountingManagerId) {
    return billings.values().stream()
        .filter(billingDto -> billingDto.getProgressStatus().equals(progressStatus)
            && billingDto.getAccountingManagerId().equals(accountingManagerId)).collect(
            Collectors.toList());
  }
}