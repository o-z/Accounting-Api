package com.javacase.accountingapi.common.model.dto;


import java.io.Serializable;
import java.time.LocalDateTime;
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
public class BaseDto implements Serializable {

  private Long id;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
}
