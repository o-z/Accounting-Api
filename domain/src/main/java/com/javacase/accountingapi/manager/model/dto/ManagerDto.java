package com.javacase.accountingapi.manager.model.dto;

import com.javacase.accountingapi.common.model.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDto extends BaseDto implements Serializable {

  private String firstName;
  private String lastName;
  private String email;
  private BigDecimal maxCreditLimit;
  private BigDecimal usedCreditLimit;

}
