package com.javacase.accountingapi.adapters;


import com.javacase.accountingapi.common.exception.AccountingApiException;
import com.javacase.accountingapi.common.exception.ErrorCode;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.port.ManagerPort;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import com.javacase.accountingapi.manager.usecase.UpdateManagerMaxCreditLimit;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ManagerFakeAdapter implements ManagerPort {

  private final Map<Long, ManagerDto> managers = new HashMap<>();

  @Override
  public ManagerDto getManagerById(Long id) {
    return managers.get(id);
  }

  @Override
  public ManagerDto saveManager(SaveManager saveBilling) {
    ManagerDto managerDto = ManagerDto.builder()
        .id((long) (managers.size() + 1))
        .firstName(saveBilling.getFirstName())
        .lastName(saveBilling.getLastName())
        .email(saveBilling.getEmail())
        .usedCreditLimit(new BigDecimal(0))
        .maxCreditLimit(new BigDecimal(200))
        .createDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .build();
    managers.put((long) (managers.size() + 1), managerDto);
    return managerDto;
  }

  @Override
  public ManagerDto updateManagerMaxCreditLimit(
      UpdateManagerMaxCreditLimit updateManagerMaxCreditLimit) {
    ManagerDto managerDto = managers.get(updateManagerMaxCreditLimit.getId());
    if (managerDto == null) {
      throw new AccountingApiException(ErrorCode.MANAGER_NOT_FOUND_ERROR);
    }
    managerDto.setMaxCreditLimit(updateManagerMaxCreditLimit.getMaxCreditLimit());
    managers.remove(updateManagerMaxCreditLimit.getId());
    managers.put(updateManagerMaxCreditLimit.getId(), managerDto);
    return managerDto;
  }

  @Override
  public ManagerDto useCreditLimit(Long id, BigDecimal amount) {

    ManagerDto managerDto = managers.get(id);
    if (managerDto == null) {
      throw new AccountingApiException(ErrorCode.MANAGER_NOT_FOUND_ERROR);
    }
    managerDto.setUsedCreditLimit(managerDto.getUsedCreditLimit().add(amount));
    managers.remove(id);
    managers.put(id, managerDto);
    return managerDto;
  }
}