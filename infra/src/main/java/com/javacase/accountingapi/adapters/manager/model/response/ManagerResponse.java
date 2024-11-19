package com.javacase.accountingapi.adapters.manager.model.response;

import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponse implements Serializable {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private BigDecimal maxCreditLimit;
  private BigDecimal usedCreditLimit;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;


  public static ManagerResponse from(ManagerDto managerDto) {
    return ManagerResponse.builder()
        .id(managerDto.getId())
        .firstName(managerDto.getFirstName())
        .lastName(managerDto.getLastName())
        .email(managerDto.getEmail())
        .maxCreditLimit(managerDto.getMaxCreditLimit())
        .usedCreditLimit(managerDto.getUsedCreditLimit())
        .createDate(managerDto.getCreateDate())
        .updateDate(managerDto.getUpdateDate())
        .build();
  }
}
