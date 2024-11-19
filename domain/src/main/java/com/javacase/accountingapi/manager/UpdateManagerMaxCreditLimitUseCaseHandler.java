package com.javacase.accountingapi.manager;


import com.javacase.accountingapi.common.usecase.UseCaseHandler;
import com.javacase.accountingapi.common.util.DomainComponent;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.port.ManagerPort;
import com.javacase.accountingapi.manager.usecase.UpdateManagerMaxCreditLimit;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class UpdateManagerMaxCreditLimitUseCaseHandler implements
    UseCaseHandler<ManagerDto, UpdateManagerMaxCreditLimit> {

  private final ManagerPort managerPort;

  @Override
  public ManagerDto handle(UpdateManagerMaxCreditLimit useCase) {
    return managerPort.updateManagerMaxCreditLimit(useCase);
  }
}
