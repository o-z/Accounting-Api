package com.javacase.accountingapi.manager;


import com.javacase.accountingapi.common.usecase.UseCaseHandler;
import com.javacase.accountingapi.common.util.DomainComponent;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.port.ManagerPort;
import com.javacase.accountingapi.manager.usecase.GetManagerById;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetManagerByIdUseCaseHandler implements
    UseCaseHandler<ManagerDto, GetManagerById> {

  private final ManagerPort managerPort;

  @Override
  public ManagerDto handle(GetManagerById useCase) {
    return managerPort.getManagerById(useCase.getId());
  }
}
