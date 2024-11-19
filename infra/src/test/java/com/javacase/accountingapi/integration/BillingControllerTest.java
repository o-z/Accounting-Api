package com.javacase.accountingapi.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.javacase.accountingapi.adapters.billing.model.request.SaveBillingRequest;
import com.javacase.accountingapi.adapters.billing.model.response.BillingListResponse;
import com.javacase.accountingapi.adapters.billing.model.response.BillingResponse;
import com.javacase.accountingapi.common.model.dto.AmountDto;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import com.javacase.accountingapi.common.model.enums.Currency;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


class BillingControllerTest extends BaseMySQLContainerTest {

  @Test
  void testSaveBillingWithValidData() {
    SaveBillingRequest request = SaveBillingRequest.builder()
        .accountingManagerId(1L)
        .amount(new AmountDto(BigDecimal.valueOf(1000.0), Currency.TRY))
        .productName("Product A")
        .billNo("INV-001")
        .build();

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<SaveBillingRequest> entity = new HttpEntity<>(request, headers);

    BillingResponse response = testRestTemplate.exchange("/v0/billings", HttpMethod.POST, entity,
        BillingResponse.class).getBody();

    assertEquals("Product A", response.getProductName());
    assertEquals(1L, (long) response.getAccountingManagerId());
    assertEquals(Currency.TRY, response.getAmountDto().getCurrency());
    assertEquals(BigDecimal.valueOf(1000.0), response.getAmountDto().getPrice());
  }

  @Test
  void testSaveBillingWithMissingAccountingManagerId() {
    SaveBillingRequest request = SaveBillingRequest.builder()
        .amount(new AmountDto(BigDecimal.valueOf(1000.0), Currency.TRY))
        .productName("Product A")
        .billNo("INV-001")
        .build();

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<SaveBillingRequest> entity = new HttpEntity<>(request, headers);

    var response = testRestTemplate.exchange("/v0/billings", HttpMethod.POST, entity, String.class);

    assertEquals(400, response.getStatusCodeValue());
  }


  @Test
  void testSaveBillingWithMissingProductName() {
    SaveBillingRequest request = SaveBillingRequest.builder()
        .accountingManagerId(1L)
        .amount(new AmountDto(BigDecimal.valueOf(1000.0), Currency.TRY))
        .billNo("INV-001")
        .build();

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<SaveBillingRequest> entity = new HttpEntity<>(request, headers);

    var response = testRestTemplate.exchange("/v0/billings", HttpMethod.POST, entity, String.class);

    assertEquals(400, response.getStatusCodeValue());
  }

  @Test
  void testSaveBillingWithMissingBillNo() {
    SaveBillingRequest request = SaveBillingRequest.builder()
        .accountingManagerId(1L)
        .amount(new AmountDto(BigDecimal.valueOf(1000.0), Currency.TRY))
        .productName("Product A")
        .build();

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<SaveBillingRequest> entity = new HttpEntity<>(request, headers);

    var response = testRestTemplate.exchange("/v0/billings", HttpMethod.POST, entity, String.class);

    assertEquals(400, response.getStatusCodeValue());
  }

  @Test
  void testGetBillingById() {
    Long billingId = 1L;
    BillingResponse response = testRestTemplate.exchange("/v0/billings/{id}", HttpMethod.GET, null,
        BillingResponse.class, billingId).getBody();

    assertEquals(billingId, response.getId());
    assertEquals("Product B", response.getProductName());
    assertEquals(1L, (long) response.getAccountingManagerId());
  }

  @Test
  void testGetBillingByIdNotFound() {
    Long billingId = 999L;
    var response = testRestTemplate.exchange("/v0/billings/{id}", HttpMethod.GET, null,
        BillingResponse.class, billingId);

    assertEquals(400, response.getStatusCodeValue());
  }

  @Test
  void testGetBillingsByProgressStatus() {
    BillingProgressEnum progressStatus = BillingProgressEnum.SUCCESS;
    BillingListResponse response = testRestTemplate.exchange(
            "/v0/billings?progressStatus={progressStatus}", HttpMethod.GET, null,
            new ParameterizedTypeReference<BillingListResponse>() {
            }, progressStatus)
        .getBody();

    assertEquals(0, response.getBillings().size());
  }

  @Test
  void testGetBillingsByProgressStatusWithAccountingManagerId() {
    BillingProgressEnum progressStatus = BillingProgressEnum.NOT_ACCEPTED;
    Long accountingManagerId = 1L;

    ResponseEntity<Optional<BillingListResponse>> responseEntity = testRestTemplate.exchange(
        "/v0/billings?progressStatus={progressStatus}&accountingManagerId={accountingManagerId}",
        HttpMethod.GET, null,
        new ParameterizedTypeReference<Optional<BillingListResponse>>() {
        }, progressStatus, accountingManagerId);

    Optional<BillingListResponse> response = responseEntity.getBody();

    assertTrue(response.isPresent());

    assertEquals(1, response.get().getBillings().size());
  }
}