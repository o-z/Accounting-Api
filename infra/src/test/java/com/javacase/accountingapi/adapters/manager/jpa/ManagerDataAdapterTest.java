package com.javacase.accountingapi.adapters.manager.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.javacase.accountingapi.adapters.manager.jpa.entity.ManagerEntity;
import com.javacase.accountingapi.adapters.manager.jpa.repository.ManagerRepository;
import com.javacase.accountingapi.common.exception.AccountingApiException;
import com.javacase.accountingapi.common.exception.ErrorCode;
import com.javacase.accountingapi.manager.model.dto.ManagerDto;
import com.javacase.accountingapi.manager.usecase.SaveManager;
import com.javacase.accountingapi.manager.usecase.UpdateManagerMaxCreditLimit;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ManagerDataAdapterTest {

  @Mock
  private ManagerRepository managerRepository;

  @InjectMocks
  private ManagerDataAdapter managerDataAdapter;

  private ManagerEntity managerEntity;

  @Before
  public void setUp() {
    managerEntity = ManagerEntity.builder()
        .firstName("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .maxCreditLimit(BigDecimal.valueOf(5000.00))
        .usedCreditLimit(BigDecimal.ZERO)
        .build();
  }

  @Test
  public void testSaveManager_Success() {
    SaveManager saveManager = SaveManager.builder()
        .firstName("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .build();

    when(managerRepository.save(any(ManagerEntity.class))).thenReturn(managerEntity);

    ManagerDto result = managerDataAdapter.saveManager(saveManager);

    assertNotNull(result);
    assertEquals("John", result.getFirstName());
    assertEquals("Doe", result.getLastName());
    assertEquals("john.doe@example.com", result.getEmail());
    assertEquals(BigDecimal.valueOf(5000.00), result.getMaxCreditLimit());

    verify(managerRepository, times(1)).save(any(ManagerEntity.class));
  }

  @Test
  public void testGetManagerById_Success() {
    when(managerRepository.findById(anyLong())).thenReturn(Optional.of(managerEntity));

    ManagerDto result = managerDataAdapter.getManagerById(1L);

    assertNotNull(result);
    assertEquals("John", result.getFirstName());
    assertEquals("Doe", result.getLastName());

    verify(managerRepository, times(1)).findById(anyLong());
  }

  @Test
  public void testGetManagerById_ManagerNotFound() {
    when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

    AccountingApiException exception = assertThrows(AccountingApiException.class,
        () -> managerDataAdapter.getManagerById(1L));

    assertEquals(ErrorCode.MANAGER_NOT_FOUND_ERROR.getCode(), exception.getCode());
  }

  @Test
  public void testUpdateManagerMaxCreditLimit_Success() {
    UpdateManagerMaxCreditLimit updateManagerMaxCreditLimit = UpdateManagerMaxCreditLimit.builder()
        .id(1L)
        .maxCreditLimit(BigDecimal.valueOf(10000.00))
        .build();

    when(managerRepository.findById(anyLong())).thenReturn(Optional.of(managerEntity));
    when(managerRepository.save(any(ManagerEntity.class))).thenReturn(managerEntity);

    ManagerDto result = managerDataAdapter.updateManagerMaxCreditLimit(updateManagerMaxCreditLimit);

    assertNotNull(result);
    assertEquals(BigDecimal.valueOf(10000.00), result.getMaxCreditLimit());

    verify(managerRepository, times(1)).findById(anyLong());
    verify(managerRepository, times(1)).save(any(ManagerEntity.class));
  }

  @Test
  public void testUpdateManagerMaxCreditLimit_ManagerNotFound() {
    when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

    UpdateManagerMaxCreditLimit updateManagerMaxCreditLimit = UpdateManagerMaxCreditLimit.builder()
        .id(1L)
        .maxCreditLimit(BigDecimal.valueOf(10000.00))
        .build();

    AccountingApiException exception = assertThrows(AccountingApiException.class,
        () -> managerDataAdapter.updateManagerMaxCreditLimit(updateManagerMaxCreditLimit));

    assertEquals(ErrorCode.MANAGER_NOT_FOUND_ERROR.getCode(), exception.getCode());
  }

  @Test
  public void testUseCreditLimit_Success() {
    when(managerRepository.findById(anyLong())).thenReturn(Optional.of(managerEntity));
    when(managerRepository.save(any(ManagerEntity.class))).thenReturn(managerEntity);

    BigDecimal amount = BigDecimal.valueOf(500.00);
    ManagerDto result = managerDataAdapter.useCreditLimit(1L, amount);

    assertNotNull(result);
    assertEquals(amount, result.getUsedCreditLimit());

    verify(managerRepository, times(1)).findById(anyLong());
    verify(managerRepository, times(1)).save(any(ManagerEntity.class));
  }

  @Test
  public void testUseCreditLimit_ManagerNotFound() {
    when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

    AccountingApiException exception = assertThrows(AccountingApiException.class,
        () -> managerDataAdapter.useCreditLimit(1L, BigDecimal.valueOf(500.00)));

    assertEquals(ErrorCode.MANAGER_NOT_FOUND_ERROR.getCode(), exception.getCode());
  }
}