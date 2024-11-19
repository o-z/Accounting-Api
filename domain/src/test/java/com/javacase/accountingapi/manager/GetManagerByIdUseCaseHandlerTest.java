package com.javacase.accountingapi.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.javacase.accountingapi.adapters.ManagerFakeAdapter;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.usecase.GetManagerById;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import org.junit.Before;
import org.junit.Test;

public class GetManagerByIdUseCaseHandlerTest {

  private ManagerFakeAdapter managerPortFakeAdapter;
  private GetManagerByIdUseCaseHandler getManagerByIdUseCaseHandler;

  @Before
  public void setUp() {
    managerPortFakeAdapter = new ManagerFakeAdapter();
    getManagerByIdUseCaseHandler = new GetManagerByIdUseCaseHandler(managerPortFakeAdapter);
  }

  @Test
  public void handle_withValidId_returnsCorrectManager() {
    // Arrange
    SaveManager saveManager = new SaveManager("John", "Doe", "john.doe@example.com");
    managerPortFakeAdapter.saveManager(saveManager);
    GetManagerById useCase = new GetManagerById(1L);
    // Act
    ManagerDto result = getManagerByIdUseCaseHandler.handle(useCase);

    // Assert
    assertEquals(1L, result.getId());
    assertEquals("John", result.getFirstName());
    assertEquals("Doe", result.getLastName());
    assertEquals("john.doe@example.com", result.getEmail());
  }

  @Test
  public void handle_withInvalidId_returnsNull() {
    // Arrange
    GetManagerById useCase = new GetManagerById(999L);

    // Act
    ManagerDto result = getManagerByIdUseCaseHandler.handle(useCase);

    // Assert
    assertNull(result);
  }

  @Test
  public void handle_withAnotherValidId_returnsCorrectManager() {
    // Arrange
    SaveManager saveManager = new SaveManager("Alice", "Johnson", "alice.johnson@example.com");
    ManagerDto savedManager = managerPortFakeAdapter.saveManager(saveManager);
    GetManagerById useCase = new GetManagerById(savedManager.getId());

    // Act
    ManagerDto result = getManagerByIdUseCaseHandler.handle(useCase);

    // Assert
    assertEquals(1L, result.getId());
    assertEquals("Alice", result.getFirstName());
    assertEquals("Johnson", result.getLastName());
    assertEquals("alice.johnson@example.com", result.getEmail());
  }
}