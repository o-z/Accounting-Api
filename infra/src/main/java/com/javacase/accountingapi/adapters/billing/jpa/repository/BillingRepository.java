package com.javacase.accountingapi.adapters.billing.jpa.repository;

import com.javacase.accountingapi.adapters.billing.jpa.entity.BillingEntity;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<BillingEntity, Long> {

  List<BillingEntity> getAllByProgressStatus(BillingProgressEnum progressStatus);

  List<BillingEntity> getAllByProgressStatusAndManagerId(
      BillingProgressEnum progressStatus,
      Long accountingManagerId);

}
