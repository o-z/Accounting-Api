package com.javacase.accountingapi.billing;

import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.port.BillingPort;
import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import com.javacase.accountingapi.common.usecase.UseCaseHandler;
import com.javacase.accountingapi.common.util.DomainComponent;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.port.ManagerLockPort;
import com.javacase.accountingapi.manager.port.ManagerPort;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class SaveBillingUseCaseHandler implements
    UseCaseHandler<BillingDto, SaveBilling> {

  private final BillingPort billingPort;
  private final ManagerPort managerPort;
  private final ManagerLockPort managerLockPort;

  @Override
  public BillingDto handle(SaveBilling useCase) {
    BillingProgressEnum progressStatus = checkMaxCreditLimitForBilling(useCase);
    managerLockPort.lock(useCase.getAccountingManagerId());
    BillingDto billingDto;
    try {
      billingDto = billingPort.saveBilling(useCase, progressStatus);
      if (progressStatus.equals(BillingProgressEnum.SUCCESS)) {
        managerPort.useCreditLimit(useCase.getAccountingManagerId(),
            useCase.getAmount().getPrice());
      }
    } finally {
      managerLockPort.unlock(useCase.getAccountingManagerId());
    }

    return billingDto;

  }

  private BillingProgressEnum checkMaxCreditLimitForBilling(
      SaveBilling useCase) {
    ManagerDto managerDto = managerPort.getManagerById(useCase.getAccountingManagerId());
    return managerDto.getMaxCreditLimit()
        .compareTo(managerDto.getUsedCreditLimit().add(useCase.getAmount().getPrice()))
        >= 0
        ? BillingProgressEnum.SUCCESS : BillingProgressEnum.NOT_ACCEPTED;
  }
}
