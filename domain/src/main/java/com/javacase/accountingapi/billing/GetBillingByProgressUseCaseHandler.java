package com.javacase.accountingapi.billing;

import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.port.BillingPort;
import com.javacase.accountingapi.billing.usecase.GetBillingsByProgressStatus;
import com.javacase.accountingapi.common.usecase.UseCaseHandler;
import com.javacase.accountingapi.common.util.DomainComponent;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetBillingByProgressUseCaseHandler implements
    UseCaseHandler<List<BillingDto>, GetBillingsByProgressStatus> {

  private final BillingPort billingPort;

  @Override
  public List<BillingDto> handle(GetBillingsByProgressStatus useCase) {
    return billingPort.getAllByProgressStatusAndManagerId(useCase.getProgressStatus(),
        useCase.getAccountingManagerId());

  }
}
