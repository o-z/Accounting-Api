package com.javacase.accountingapi.billing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.javacase.accountingapi.adapters.BillingFakeAdapter;
import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.usecase.GetBillingById;
import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.model.dto.AmountDto;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import com.javacase.accountingapi.common.model.enums.Currency;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

public class GetBillingByIdUseCaseHandlerTest {

  private BillingFakeAdapter billingFakeAdapter;
  private GetBillingByIdUseCaseHandler getBillingByIdUseCaseHandler;

  @Before
  public void setUp() {
    billingFakeAdapter = new BillingFakeAdapter();
    getBillingByIdUseCaseHandler = new GetBillingByIdUseCaseHandler(billingFakeAdapter);
  }

  @Test
  public void handle_withValidId_returnsCorrectBilling() {
    // Arrange
    SaveBilling saveBilling = SaveBilling.builder()
        .accountingManagerId(1L)
        .amount(AmountDto.builder().price(new BigDecimal("100")).currency(Currency.TRY).build())
        .productName("Product A")
        .billNo("12345")
        .build();
    BillingDto savedBilling = billingFakeAdapter.saveBilling(saveBilling,
        BillingProgressEnum.SUCCESS);

    // Act
    BillingDto result = getBillingByIdUseCaseHandler.handle(
        new GetBillingById(savedBilling.getId()));

    // Assert
    assertEquals("Product A", result.getProductName());
    assertEquals("12345", result.getBillNo());
    assertEquals(new BigDecimal("100"), result.getAmountDto().getPrice());
    assertEquals(Currency.TRY, result.getAmountDto().getCurrency());
    assertEquals(BillingProgressEnum.SUCCESS, result.getProgressStatus());
  }

  @Test
  public void handle_withInvalidId_returnsNull() {
    // Act
    BillingDto result = getBillingByIdUseCaseHandler.handle(new GetBillingById(999L));

    // Assert
    assertNull(result);
  }

  @Test
  public void handle_withNoBillings_returnsNull() {
    // Act
    BillingDto result = getBillingByIdUseCaseHandler.handle(new GetBillingById(1L));

    // Assert
    assertNull(result);
  }

  @Test
  public void handle_afterSavingMultipleBillings_returnsCorrectBilling() {
    // Arrange
    SaveBilling saveBilling1 = SaveBilling.builder()
        .accountingManagerId(2L)
        .amount(AmountDto.builder().price(new BigDecimal("150")).currency(Currency.TRY).build())
        .productName("Product B")
        .billNo("67890")
        .build();
    BillingDto savedBilling1 = billingFakeAdapter.saveBilling(saveBilling1,
        BillingProgressEnum.SUCCESS);

    SaveBilling saveBilling2 = SaveBilling.builder()
        .accountingManagerId(3L)
        .amount(AmountDto.builder().price(new BigDecimal("250")).currency(Currency.TRY).build())
        .productName("Product C")
        .billNo("98765")
        .build();
    BillingDto savedBilling2 = billingFakeAdapter.saveBilling(saveBilling2,
        BillingProgressEnum.NOT_ACCEPTED);

    // Act
    BillingDto result = getBillingByIdUseCaseHandler.handle(
        new GetBillingById(savedBilling2.getId()));

    // Assert
    assertEquals("Product C", result.getProductName());
    assertEquals("98765", result.getBillNo());
    assertEquals(new BigDecimal("250"), result.getAmountDto().getPrice());
    assertEquals(Currency.TRY, result.getAmountDto().getCurrency());
    assertEquals(BillingProgressEnum.NOT_ACCEPTED, result.getProgressStatus());
  }
}