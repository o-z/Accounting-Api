package com.javacase.accountingapi.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.javacase.accountingapi.adapters.manager.model.request.SaveManagerRequest;
import com.javacase.accountingapi.adapters.manager.model.response.ManagerResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ManagerControllerTest extends BaseMySQLContainerTest {

  @Test
  void testSaveManagerWithValidData() {
    SaveManagerRequest request = SaveManagerRequest.builder()
        .firstName("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .build();

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<SaveManagerRequest> entity = new HttpEntity<>(request, headers);

    ResponseEntity<ManagerResponse> responseEntity = testRestTemplate.exchange(
        "/v0/managers",
        HttpMethod.POST,
        entity,
        ManagerResponse.class);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    ManagerResponse managerResponse = responseEntity.getBody();
    assertNotNull(managerResponse);

    assertEquals(request.getFirstName(), managerResponse.getFirstName());
    assertEquals(request.getEmail(), managerResponse.getEmail());
    assertEquals(request.getLastName(), managerResponse.getLastName());
  }


  @Test
  void testUpdateManagerMaxCreditLimit() {
    Long managerId = 1L;
    BigDecimal newMaxCreditLimit = new BigDecimal("10000.00");

    ResponseEntity<ManagerResponse> responseEntity = testRestTemplate.exchange(
        "/v0/managers/{id}",
        HttpMethod.PUT,
        new HttpEntity<>(newMaxCreditLimit),
        ManagerResponse.class,
        managerId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    ManagerResponse managerResponse = responseEntity.getBody();
    assertNotNull(managerResponse);

    assertEquals(newMaxCreditLimit, managerResponse.getMaxCreditLimit());
  }

  @Test
  void testGetManagerById() {
    Long managerId = 1L;

    ResponseEntity<ManagerResponse> responseEntity = testRestTemplate.exchange(
        "/v0/managers/{id}",
        HttpMethod.GET,
        null,
        ManagerResponse.class,
        managerId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    ManagerResponse managerResponse = responseEntity.getBody();
    assertNotNull(managerResponse);

    assertEquals(managerId, managerResponse.getId());
  }

  @Test
  void testGetManagerByIdNotFound() {
    Long managerId = 999L;

    ResponseEntity<String> responseEntity = testRestTemplate.exchange(
        "/v0/managers/{id}",
        HttpMethod.GET,
        null,
        String.class,
        managerId);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }
}