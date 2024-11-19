package com.javacase.accountingapi.adapters.manager.jpa.entity;


import com.javacase.accountingapi.common.entity.BaseEntity;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MANAGER", schema = "ACCOUNTING",
    indexes = @Index(name = "ACCOUNTING_MANAGER_EMAIL_INDEX", columnList = "EMAIL"),
    uniqueConstraints = @UniqueConstraint(columnNames = {"FIRST_NAME", "LAST_NAME", "EMAIL"}))
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class ManagerEntity extends BaseEntity implements Serializable {

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "MAX_CREDIT_LIMIT")
  private BigDecimal maxCreditLimit;

  @Column(name = "USED_CREDIT_LIMIT", columnDefinition = "Decimal(19,2) default '0.00'", nullable = false)
  private BigDecimal usedCreditLimit;


  public ManagerDto toModel() {
    return ManagerDto.builder()
        .id(super.getId())
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .maxCreditLimit(maxCreditLimit)
        .usedCreditLimit(usedCreditLimit)
        .createDate(super.getCreateDate())
        .updateDate(super.getUpdateDate())
        .build();
  }

  @PrePersist
  public void prePersist() {
    if (usedCreditLimit == null) {
      usedCreditLimit = BigDecimal.valueOf(0.00);
    }
  }
}
