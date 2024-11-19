package com.javacase.accountingapi.adapters.billing.jpa.entity;


import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.common.entity.BaseEntity;
import com.javacase.accountingapi.common.model.dto.AmountDto;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import com.javacase.accountingapi.common.model.enums.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BILLING", schema = "ACCOUNTING")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class BillingEntity extends BaseEntity implements Serializable {


  @Column(name = "MANAGER_ID", nullable = false)
  @NotNull
  private Long managerId;

  @Column(name = "PRICE", nullable = false)
  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  @Column(name = "CURRENCY", nullable = false)
  private Currency currency;

  @Column(name = "PRODUCT_NAME", nullable = false)
  private String productName;

  @Column(name = "BILL_NO", nullable = false)
  private String billNo;

  @Enumerated(EnumType.STRING)
  @Column(name = "PROGRESS_STATUS", nullable = false)
  private BillingProgressEnum progressStatus;


  public BillingDto toModel() {
    return BillingDto.builder()
        .id(super.getId())
        .accountingManagerId(managerId)
        .amountDto(AmountDto.builder().price(price).currency(currency).build())
        .productName(productName)
        .billNo(billNo)
        .progressStatus(progressStatus)
        .createDate(super.getCreateDate())
        .updateDate(super.getUpdateDate())
        .build();
  }
}
