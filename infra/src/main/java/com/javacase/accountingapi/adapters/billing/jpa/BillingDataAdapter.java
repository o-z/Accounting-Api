package com.javacase.accountingapi.adapters.billing.jpa;


import com.javacase.accountingapi.adapters.billing.jpa.entity.BillingEntity;
import com.javacase.accountingapi.adapters.billing.jpa.repository.BillingRepository;
import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.port.BillingPort;
import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.exception.AccountingApiException;
import com.javacase.accountingapi.common.exception.ErrorCode;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BillingDataAdapter implements BillingPort {

  private final BillingRepository billingRepository;

  @Transactional(readOnly = true)
  @Override
  public BillingDto getBillingById(Long id) {
    return billingRepository.findById(id)
        .orElseThrow(() -> new AccountingApiException(ErrorCode.BILLING_NOT_FOUND_ERROR)).toModel();
  }

  @Transactional
  @Override
  public BillingDto saveBilling(SaveBilling saveBilling, BillingProgressEnum progressEnum) {
    BillingEntity billingEntity = BillingEntity.builder()
        .managerId(saveBilling.getAccountingManagerId())
        .price(saveBilling.getAmount().getPrice())
        .currency(saveBilling.getAmount().getCurrency())
        .productName(saveBilling.getProductName())
        .billNo(saveBilling.getBillNo())
        .progressStatus(progressEnum)
        .build();
    return billingRepository.save(billingEntity).toModel();

  }

  @Transactional(readOnly = true)
  @Override
  public List<BillingDto> getAllByProgressStatusAndManagerId(BillingProgressEnum progressStatus,
      Long accountingManagerId) {
    return billingRepository.getAllByProgressStatusAndManagerId(progressStatus, accountingManagerId)
        .stream()
        .map(BillingEntity::toModel).toList();
  }


}
