package com.javacase.accountingapi.adapters.billing.rest.v0;


import com.javacase.accountingapi.adapters.billing.model.request.SaveBillingRequest;
import com.javacase.accountingapi.adapters.billing.model.response.BillingListResponse;
import com.javacase.accountingapi.adapters.billing.model.response.BillingResponse;
import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.usecase.GetBillingById;
import com.javacase.accountingapi.billing.usecase.GetBillingsByProgressStatus;
import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import com.javacase.accountingapi.common.usecase.UseCaseHandler;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0/billings")
@RequiredArgsConstructor
@Slf4j
public class BillingController {

  private final UseCaseHandler<BillingDto, GetBillingById> getBillingByIdUseCaseHandler;
  private final UseCaseHandler<BillingDto, SaveBilling> saveBillingUseCaseHandler;
  private final UseCaseHandler<List<BillingDto>, GetBillingsByProgressStatus> getBillingsByProgressStatusUseCaseHandler;


  @GetMapping("/{id}")
  public ResponseEntity<BillingResponse> getBillingById(@PathVariable final Long id) {
    GetBillingById getBillingById = GetBillingById.builder().id(id).build();
    return ResponseEntity.of(
        Optional.of(BillingResponse.from(getBillingByIdUseCaseHandler.handle(getBillingById))));
  }

  @PostMapping
  public ResponseEntity<BillingResponse> saveBilling(
      @RequestBody @Valid SaveBillingRequest saveBillingRequest) {
    return ResponseEntity.of(
        Optional.of(
            BillingResponse.from(saveBillingUseCaseHandler.handle(saveBillingRequest.toModel()))));
  }

  @GetMapping
  public ResponseEntity<BillingListResponse> getBillingsByProgressStatus(
      @RequestParam @Valid @NotNull BillingProgressEnum progressStatus,
      @RequestParam(value = "accountingManagerId", required = false) Long accountingManagerId) {
    GetBillingsByProgressStatus getBillingsByProgressStatus = GetBillingsByProgressStatus.builder()
        .progressStatus(progressStatus)
        .accountingManagerId(accountingManagerId)
        .build();
    return ResponseEntity.of(
        Optional.of(BillingListResponse.from(
            getBillingsByProgressStatusUseCaseHandler.handle(getBillingsByProgressStatus))));
  }
}