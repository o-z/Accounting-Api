package com.javacase.accountingapi.common.model.dto;


import com.javacase.accountingapi.common.model.enums.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AmountDto implements Serializable {

  private BigDecimal price;
  private Currency currency;
}
