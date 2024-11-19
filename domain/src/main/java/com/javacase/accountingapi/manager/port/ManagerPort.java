package com.javacase.accountingapi.manager.port;

import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import com.javacase.accountingapi.manager.usecase.UpdateManagerMaxCreditLimit;
import java.math.BigDecimal;

public interface ManagerPort {

  ManagerDto getManagerById(Long id);

  ManagerDto saveManager(SaveManager saveManager);

  ManagerDto updateManagerMaxCreditLimit(UpdateManagerMaxCreditLimit updateManagerMaxCreditLimit);

  ManagerDto useCreditLimit(Long id, BigDecimal amount);

}
