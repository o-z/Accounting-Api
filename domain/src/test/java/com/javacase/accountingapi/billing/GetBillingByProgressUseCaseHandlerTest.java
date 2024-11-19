package com.javacase.accountingapi.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javacase.accountingapi.adapters.BillingFakeAdapter;
import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.usecase.GetBillingsByProgressStatus;
import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.model.dto.AmountDto;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import com.javacase.accountingapi.common.model.enums.Currency;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class GetBillingByProgressUseCaseHandlerTest {

  private BillingFakeAdapter billingFakeAdapter;
  private GetBillingByProgressUseCaseHandler getBillingByProgressUseCaseHandler;

  @Before
  public void setUp() {
    billingFakeAdapter = new BillingFakeAdapter();
    getBillingByProgressUseCaseHandler = new GetBillingByProgressUseCaseHandler(
        billingFakeAdapter);
  }

  @Test
  public void handle_withProgressStatus_returnsCorrectBillings() {
    // Arrange
    SaveBilling saveBilling1 = SaveBilling.builder()
        .accountingManagerId(1L)
        .amount(AmountDto.builder().price(new BigDecimal("100")).currency(Currency.TRY).build())
        .productName("Product A")
        .billNo("12345")
        .build();
    billingFakeAdapter.saveBilling(saveBilling1, BillingProgressEnum.SUCCESS);

    SaveBilling saveBilling2 = SaveBilling.builder()
        .accountingManagerId(1L)
        .amount(AmountDto.builder().price(new BigDecimal("200")).currency(Currency.TRY).build())
        .productName("Product B")
        .billNo("67890")
        .build();
    billingFakeAdapter.saveBilling(saveBilling2, BillingProgressEnum.SUCCESS);

    SaveBilling saveBilling3 = SaveBilling.builder()
        .accountingManagerId(1L)
        .amount(AmountDto.builder().price(new BigDecimal("300")).currency(Currency.TRY).build())
        .productName("Product C")
        .billNo("54321")
        .build();
    billingFakeAdapter.saveBilling(saveBilling3, BillingProgressEnum.NOT_ACCEPTED);

    // Act
    GetBillingsByProgressStatus useCase = new GetBillingsByProgressStatus(
        BillingProgressEnum.SUCCESS, 1L);
    List<BillingDto> result = getBillingByProgressUseCaseHandler.handle(useCase);

    // Assert
    assertEquals(2, result.size());
    assertEquals("Product A", result.get(0).getProductName());
    assertEquals("12345", result.get(0).getBillNo());
    assertEquals(new BigDecimal("100"), result.get(0).getAmountDto().getPrice());
    assertEquals(Currency.TRY, result.get(0).getAmountDto().getCurrency());
    assertEquals(BillingProgressEnum.SUCCESS, result.get(0).getProgressStatus());
  }

  @Test
  public void handle_withInvalidProgressStatus_returnsEmptyList() {
    // Arrange
    SaveBilling saveBilling = SaveBilling.builder()
        .accountingManagerId(1L)
        .amount(AmountDto.builder().price(new BigDecimal("100")).currency(Currency.TRY).build())
        .productName("Product A")
        .billNo("12345")
        .build();
    billingFakeAdapter.saveBilling(saveBilling, BillingProgressEnum.SUCCESS);

    // Act
    GetBillingsByProgressStatus useCase = new GetBillingsByProgressStatus(
        BillingProgressEnum.NOT_ACCEPTED, 1L
    );
    List<BillingDto> result = getBillingByProgressUseCaseHandler.handle(useCase);

    // Assert
    assertTrue(result.isEmpty());
  }

  @Test
  public void handle_withNoBillings_returnsEmptyList() {
    // Act
    GetBillingsByProgressStatus useCase = new GetBillingsByProgressStatus(
        BillingProgressEnum.SUCCESS, 1L
    );
    List<BillingDto> result = getBillingByProgressUseCaseHandler.handle(useCase);

    // Assert
    assertTrue(result.isEmpty());
  }

  @Test
  public void handle_withDifferentManagerId_returnsEmptyList() {
    // Arrange
    SaveBilling saveBilling = SaveBilling.builder()
        .accountingManagerId(2L)
        .amount(AmountDto.builder().price(new BigDecimal("100")).currency(Currency.TRY).build())
        .productName("Product A")
        .billNo("12345")
        .build();
    billingFakeAdapter.saveBilling(saveBilling, BillingProgressEnum.SUCCESS);

    // Act
    GetBillingsByProgressStatus useCase = new GetBillingsByProgressStatus(
        BillingProgressEnum.SUCCESS, 1L);
    List<BillingDto> result = getBillingByProgressUseCaseHandler.handle(useCase);

    // Assert
    assertTrue(result.isEmpty());
  }
}