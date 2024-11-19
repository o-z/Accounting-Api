package com.javacase.accountingapi.billing;


import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.port.BillingPort;
import com.javacase.accountingapi.billing.usecase.GetBillingById;
import com.javacase.accountingapi.common.usecase.UseCaseHandler;
import com.javacase.accountingapi.common.util.DomainComponent;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetBillingByIdUseCaseHandler implements
    UseCaseHandler<BillingDto, GetBillingById> {

  private final BillingPort billingPort;

  @Override
  public BillingDto handle(GetBillingById useCase) {
    return billingPort.getBillingById(useCase.getId());
  }
}
