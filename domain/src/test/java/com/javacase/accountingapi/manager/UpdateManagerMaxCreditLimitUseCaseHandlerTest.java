package com.javacase.accountingapi.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.javacase.accountingapi.adapters.ManagerFakeAdapter;
import com.javacase.accountingapi.common.exception.AccountingApiException;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import com.javacase.accountingapi.manager.usecase.UpdateManagerMaxCreditLimit;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

public class UpdateManagerMaxCreditLimitUseCaseHandlerTest {

  private ManagerFakeAdapter managerFakeAdapter;
  private UpdateManagerMaxCreditLimitUseCaseHandler updateManagerMaxCreditLimitUseCaseHandler;

  @Before
  public void setUp() {
    managerFakeAdapter = new ManagerFakeAdapter();
    updateManagerMaxCreditLimitUseCaseHandler = new UpdateManagerMaxCreditLimitUseCaseHandler(
        managerFakeAdapter);
  }

  @Test
  public void handle_withValidData_returnsUpdatedManager() {
    // Arrange:
    SaveManager saveManager = new SaveManager("John", "Doe", "john.doe@example.com");
    ManagerDto savedManager = managerFakeAdapter.saveManager(saveManager);

    // Act:
    BigDecimal newCreditLimit = new BigDecimal(500);
    UpdateManagerMaxCreditLimit updateManagerMaxCreditLimit = new UpdateManagerMaxCreditLimit(
        savedManager.getId(), newCreditLimit);
    ManagerDto updatedManager = updateManagerMaxCreditLimitUseCaseHandler.handle(
        updateManagerMaxCreditLimit);

    // Assert:
    assertEquals(newCreditLimit, updatedManager.getMaxCreditLimit());
  }

  @Test
  public void handle_withNonExistentManager_throwsException() {
    // Arrange:
    UpdateManagerMaxCreditLimit updateManagerMaxCreditLimit = new UpdateManagerMaxCreditLimit(999L,
        new BigDecimal(500));

    // Act & Assert:
    assertThrows(AccountingApiException.class, () -> {
      updateManagerMaxCreditLimitUseCaseHandler.handle(updateManagerMaxCreditLimit);
    });
  }

  @Test
  public void handle_withMultipleUpdates_returnsCorrectUpdatedManager() {
    // Arrange:
    SaveManager saveManager1 = new SaveManager("Alice", "Johnson", "alice.johnson@example.com");
    SaveManager saveManager2 = new SaveManager("Bob", "Smith", "bob.smith@example.com");
    ManagerDto manager1 = managerFakeAdapter.saveManager(saveManager1);
    ManagerDto manager2 = managerFakeAdapter.saveManager(saveManager2);

    // Act:
    BigDecimal newCreditLimit1 = new BigDecimal(600);
    BigDecimal newCreditLimit2 = new BigDecimal(800);
    UpdateManagerMaxCreditLimit updateManager1 = new UpdateManagerMaxCreditLimit(manager1.getId(),
        newCreditLimit1);
    UpdateManagerMaxCreditLimit updateManager2 = new UpdateManagerMaxCreditLimit(manager2.getId(),
        newCreditLimit2);

    ManagerDto updatedManager1 = updateManagerMaxCreditLimitUseCaseHandler.handle(updateManager1);
    ManagerDto updatedManager2 = updateManagerMaxCreditLimitUseCaseHandler.handle(updateManager2);

    // Assert:
    assertEquals(newCreditLimit1, updatedManager1.getMaxCreditLimit());
    assertEquals(newCreditLimit2, updatedManager2.getMaxCreditLimit());
  }
}