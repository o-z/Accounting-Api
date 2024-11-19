package com.javacase.accountingapi.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.javacase.accountingapi.adapters.ManagerFakeAdapter;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

public class SaveManagerUseCaseHandlerTest {

  private ManagerFakeAdapter managerFakeAdapter;
  private SaveManagerUseCaseHandler saveManagerUseCaseHandler;

  @Before
  public void setUp() {
    managerFakeAdapter = new ManagerFakeAdapter();
    saveManagerUseCaseHandler = new SaveManagerUseCaseHandler(managerFakeAdapter);
  }

  @Test
  public void handle_withValidSaveManager_returnsSavedManager() {
    // Arrange
    SaveManager saveManager = new SaveManager("John", "Doe", "john.doe@example.com");

    // Act
    ManagerDto result = saveManagerUseCaseHandler.handle(saveManager);

    // Assert
    assertEquals("John", result.getFirstName());
    assertEquals("Doe", result.getLastName());
    assertEquals("john.doe@example.com", result.getEmail());
    assertEquals(1L, result.getId());
    assertEquals(new BigDecimal(200), result.getMaxCreditLimit());
    assertEquals(new BigDecimal(0), result.getUsedCreditLimit());
  }

  @Test
  public void handle_whenSavingMultipleManagers_returnsCorrectManagers() {
    // Arrange
    SaveManager saveManager1 = new SaveManager("Alice", "Johnson", "alice.johnson@example.com");
    SaveManager saveManager2 = new SaveManager("Bob", "Smith", "bob.smith@example.com");

    // Act
    ManagerDto result1 = saveManagerUseCaseHandler.handle(saveManager1);
    ManagerDto result2 = saveManagerUseCaseHandler.handle(saveManager2);

    // Assert
    assertEquals("Alice", result1.getFirstName());
    assertEquals("Bob", result2.getFirstName());
    assertEquals(2L, result2.getId());
    assertEquals(1L, result1.getId());
  }
}