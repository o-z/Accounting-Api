package com.javacase.accountingapi.adapters.manager.jpa;


import com.javacase.accountingapi.adapters.manager.jpa.entity.ManagerEntity;
import com.javacase.accountingapi.adapters.manager.jpa.repository.ManagerRepository;
import com.javacase.accountingapi.common.exception.AccountingApiException;
import com.javacase.accountingapi.common.exception.ErrorCode;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.port.ManagerPort;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import com.javacase.accountingapi.manager.usecase.UpdateManagerMaxCreditLimit;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerDataAdapter implements ManagerPort {

  private final ManagerRepository managerRepository;
  @Value("${accountingManager.defaultMaxCreditLimit.price}")
  private Integer defaultMaxCreditLimit = 200;

  @Transactional(readOnly = true)
  @Override
  public ManagerDto getManagerById(Long id) {
    return managerRepository.findById(id)
        .orElseThrow(() -> new AccountingApiException(ErrorCode.MANAGER_NOT_FOUND_ERROR)).toModel();
  }

  @Transactional
  @Override
  public ManagerDto saveManager(SaveManager saveManager) {
    ManagerEntity managerEntity = ManagerEntity.builder()
        .firstName(saveManager.getFirstName())
        .lastName(saveManager.getLastName())
        .email(saveManager.getEmail())
        .maxCreditLimit(new BigDecimal(defaultMaxCreditLimit))
        .build();
    return managerRepository.save(managerEntity).toModel();
  }

  @Transactional
  @Override
  public ManagerDto updateManagerMaxCreditLimit(
      UpdateManagerMaxCreditLimit updateManagerMaxCreditLimit) {
    ManagerEntity managerEntity = managerRepository.findById(updateManagerMaxCreditLimit.getId())
        .orElseThrow(() -> new AccountingApiException(ErrorCode.MANAGER_NOT_FOUND_ERROR));
    managerEntity.setMaxCreditLimit(updateManagerMaxCreditLimit.getMaxCreditLimit());
    return managerRepository.save(managerEntity).toModel();
  }

  @Transactional
  @Override
  public ManagerDto useCreditLimit(Long id, BigDecimal amount) {
    ManagerEntity managerEntity = managerRepository.findById(id)
        .orElseThrow(() -> new AccountingApiException(ErrorCode.MANAGER_NOT_FOUND_ERROR));
    managerEntity.setUsedCreditLimit(managerEntity.getUsedCreditLimit().add(amount));
    return managerRepository.save(managerEntity).toModel();
  }
}
