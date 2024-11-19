package com.javacase.accountingapi.adapters.billing.model.response;

import com.javacase.accountingapi.billing.model.dto.BillingDto;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingListResponse implements Serializable {

  private List<BillingResponse> billings;


  public static BillingListResponse from(List<BillingDto> billingDtos) {
    return BillingListResponse.builder()
        .billings(!CollectionUtils.isEmpty(billingDtos) ? billingDtos.stream()
            .map(BillingResponse::from).toList() : Collections.emptyList())
        .build();
  }
}
