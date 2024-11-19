package com.javacase.accountingapi.billing.port;

import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import java.util.List;

public interface BillingPort {

  BillingDto getBillingById(Long id);

  BillingDto saveBilling(SaveBilling saveBilling, BillingProgressEnum progressEnum);

  List<BillingDto> getAllByProgressStatusAndManagerId(
      BillingProgressEnum progressStatus,
      Long accountingManagerId);

}
