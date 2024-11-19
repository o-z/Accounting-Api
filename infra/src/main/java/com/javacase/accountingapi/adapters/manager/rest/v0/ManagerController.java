package com.javacase.accountingapi.adapters.manager.rest.v0;


import com.javacase.accountingapi.adapters.manager.model.request.SaveManagerRequest;
import com.javacase.accountingapi.adapters.manager.model.response.ManagerResponse;
import com.javacase.accountingapi.common.usecase.UseCaseHandler;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.usecase.GetManagerById;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import com.javacase.accountingapi.manager.usecase.UpdateManagerMaxCreditLimit;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v0/managers")
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

  private final UseCaseHandler<ManagerDto, GetManagerById> getManagerByIdUseCaseHandler;
  private final UseCaseHandler<ManagerDto, SaveManager> saveManagerUseCaseHandler;
  private final UseCaseHandler<ManagerDto, UpdateManagerMaxCreditLimit> updateManagerMaxCreditLimitUseCaseHandler;


  @GetMapping("/{id}")
  public ResponseEntity<ManagerResponse> getManagerById(@PathVariable final Long id) {
    GetManagerById getManagerById = GetManagerById.builder().id(id).build();
    return ResponseEntity.of(
        Optional.of(ManagerResponse.from(getManagerByIdUseCaseHandler.handle(getManagerById))));
  }

  @PostMapping
  public ResponseEntity<ManagerResponse> saveManager(
      @RequestBody @Valid SaveManagerRequest saveManagerRequest) {
    return ResponseEntity.of(
        Optional.of(
            ManagerResponse.from(saveManagerUseCaseHandler.handle(saveManagerRequest.toModel()))));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ManagerResponse> getBillingsByProgressStatus(
      @PathVariable final Long id,
      @Valid @RequestBody BigDecimal maxCreditLimit) {
    UpdateManagerMaxCreditLimit updateManagerMaxCreditLimit = UpdateManagerMaxCreditLimit.builder()
        .id(id)
        .maxCreditLimit(maxCreditLimit)
        .build();
    return ResponseEntity.of(
        Optional.of(ManagerResponse.from(
            updateManagerMaxCreditLimitUseCaseHandler.handle(
                updateManagerMaxCreditLimit))));
  }
}