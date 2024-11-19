package com.javacase.accountingapi.common.model.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T extends Serializable> implements Serializable {

  private List<T> contents;
  private Integer page;
  private Integer size;
  private Integer totalPage;
}
