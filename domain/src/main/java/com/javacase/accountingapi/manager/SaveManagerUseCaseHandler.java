package com.javacase.accountingapi.manager;


import com.javacase.accountingapi.common.usecase.UseCaseHandler;
import com.javacase.accountingapi.common.util.DomainComponent;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.port.ManagerPort;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class SaveManagerUseCaseHandler implements
    UseCaseHandler<ManagerDto, SaveManager> {

  private final ManagerPort managerPort;

  @Override
  public ManagerDto handle(SaveManager useCase) {
    return managerPort.saveManager(useCase);
  }
}
