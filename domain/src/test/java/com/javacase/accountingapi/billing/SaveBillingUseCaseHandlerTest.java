package com.javacase.accountingapi.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.javacase.accountingapi.adapters.BillingFakeAdapter;
import com.javacase.accountingapi.adapters.ManagerFakeAdapter;
import com.javacase.accountingapi.adapters.ManagerLockFakeAdapter;
import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.port.BillingPort;
import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.model.dto.AmountDto;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import com.javacase.accountingapi.common.model.enums.Currency;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

public class SaveBillingUseCaseHandlerTest {

  private BillingPort billingPort;
  private ManagerFakeAdapter managerPort;
  private ManagerLockFakeAdapter managerLockPort;
  private SaveBillingUseCaseHandler saveBillingUseCaseHandler;

  @Before
  public void setUp() {
    billingPort = new BillingFakeAdapter();
    managerPort = new ManagerFakeAdapter();
    managerLockPort = new ManagerLockFakeAdapter();
    saveBillingUseCaseHandler = new SaveBillingUseCaseHandler(billingPort, managerPort,
        managerLockPort);
  }

  @Test
  public void handle_withSufficientCreditLimit_returnsSuccess() {
    SaveBilling saveBilling = SaveBilling.builder()
        .accountingManagerId(1L)
        .amount(AmountDto.builder().price(new BigDecimal("100")).currency(Currency.TRY).build())
        .productName("Product A")
        .billNo("12345")
        .build();

    managerPort.saveManager(
        SaveManager.builder().firstName("John").lastName("Doe").email("john.doe@example.com")
            .build());

    BillingDto result = saveBillingUseCaseHandler.handle(saveBilling);

    assertNotNull(result);
    assertEquals(BillingProgressEnum.SUCCESS, result.getProgressStatus());

    ManagerDto manager = managerPort.getManagerById(1L);
    assertEquals(new BigDecimal("100"), manager.getUsedCreditLimit());
  }

  @Test
  public void handle_withInsufficientCreditLimit_returnsNotAccepted() {
    SaveBilling saveBilling = SaveBilling.builder()
        .accountingManagerId(1L)
        .amount(AmountDto.builder().price(new BigDecimal("300")).currency(Currency.TRY).build())
        .productName("Product B")
        .billNo("67890")
        .build();

    managerPort.saveManager(
        SaveManager.builder().firstName("John").lastName("Doe").email("john.doe@example.com")
            .build());

    BillingDto result = saveBillingUseCaseHandler.handle(saveBilling);

    assertNotNull(result);
    assertEquals(BillingProgressEnum.NOT_ACCEPTED, result.getProgressStatus());

    ManagerDto manager = managerPort.getManagerById(1L);
    assertEquals(BigDecimal.ZERO, manager.getUsedCreditLimit());
  }

  @Test
  public void handle_whenManagerLockUnlocks_releasesLock() {
    SaveBilling saveBilling = SaveBilling.builder()
        .accountingManagerId(1L)
        .amount(AmountDto.builder().price(new BigDecimal("100")).currency(Currency.TRY).build())
        .productName("Product A")
        .billNo("12345")
        .build();

    managerPort.saveManager(
        SaveManager.builder().firstName("John").lastName("Doe").email("john.doe@example.com")
            .build());

    managerLockPort.lock(1L);

    BillingDto result = saveBillingUseCaseHandler.handle(saveBilling);

    assertFalse(managerLockPort.lockedManagers.contains(1L));

    assertNotNull(result);
    assertEquals(BillingProgressEnum.SUCCESS, result.getProgressStatus());
  }
}