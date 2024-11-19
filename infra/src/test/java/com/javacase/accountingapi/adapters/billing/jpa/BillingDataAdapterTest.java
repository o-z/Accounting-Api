package com.javacase.accountingapi.adapters.billing.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.javacase.accountingapi.adapters.billing.jpa.entity.BillingEntity;
import com.javacase.accountingapi.adapters.billing.jpa.repository.BillingRepository;
import com.javacase.accountingapi.billing.model.dto.BillingDto;
import com.javacase.accountingapi.billing.usecase.SaveBilling;
import com.javacase.accountingapi.common.exception.AccountingApiException;
import com.javacase.accountingapi.common.model.dto.AmountDto;
import com.javacase.accountingapi.common.model.enums.BillingProgressEnum;
import com.javacase.accountingapi.common.model.enums.Currency;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class BillingDataAdapterTest {

  @Mock
  private BillingRepository billingRepository;

  @InjectMocks
  private BillingDataAdapter billingDataAdapter;

  private BillingEntity billingEntity;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    billingEntity = BillingEntity.builder()
        .managerId(1L)
        .price(BigDecimal.valueOf(1000.0))
        .currency(Currency.TRY)
        .productName("Product A")
        .billNo("INV-001")
        .progressStatus(BillingProgressEnum.NOT_ACCEPTED)
        .build();

    ReflectionTestUtils.setField(billingDataAdapter, "billingRepository", billingRepository);
  }

  @Test
  public void testGetBillingById_Success() {
    when(billingRepository.findById(1L)).thenReturn(Optional.of(billingEntity));

    BillingDto result = billingDataAdapter.getBillingById(1L);

    assertNotNull(result);
    assertEquals("Product A", result.getProductName());
    assertEquals("INV-001", result.getBillNo());
    assertEquals(BillingProgressEnum.NOT_ACCEPTED, result.getProgressStatus());
    verify(billingRepository, times(1)).findById(1L);
  }

  @Test
  public void testGetBillingById_NotFound() {
    when(billingRepository.findById(1L)).thenReturn(Optional.empty());

    AccountingApiException exception = assertThrows(AccountingApiException.class, () -> {
      billingDataAdapter.getBillingById(1L);
    });

    assertEquals("Billing not found.", exception.getMessage());
    verify(billingRepository, times(1)).findById(1L);
  }

  @Test
  public void testSaveBilling_Success() {
    SaveBilling saveBilling = SaveBilling.builder()
        .accountingManagerId(1L)
        .productName("Product A")
        .billNo("INV-001")
        .amount(new AmountDto(BigDecimal.valueOf(1000.0), Currency.TRY))
        .build();

    when(billingRepository.save(any(BillingEntity.class))).thenReturn(billingEntity);

    BillingDto result = billingDataAdapter.saveBilling(saveBilling,
        BillingProgressEnum.NOT_ACCEPTED);

    assertNotNull(result);
    assertEquals("Product A", result.getProductName());
    assertEquals("INV-001", result.getBillNo());
    assertEquals(BigDecimal.valueOf(1000.0), result.getAmountDto().getPrice());
    verify(billingRepository, times(1)).save(any(BillingEntity.class));
  }

  @Test
  public void testGetAllByProgressStatusAndManagerId_Success() {
    when(billingRepository.getAllByProgressStatusAndManagerId(BillingProgressEnum.NOT_ACCEPTED, 1L))
        .thenReturn(List.of(billingEntity));

    List<BillingDto> result = billingDataAdapter.getAllByProgressStatusAndManagerId(
        BillingProgressEnum.NOT_ACCEPTED, 1L);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Product A", result.get(0).getProductName());
    verify(billingRepository, times(1)).getAllByProgressStatusAndManagerId(
        BillingProgressEnum.NOT_ACCEPTED, 1L);
  }

  @Test
  public void testGetAllByProgressStatusAndManagerId_EmptyList() {
    when(billingRepository.getAllByProgressStatusAndManagerId(BillingProgressEnum.SUCCESS, 1L))
        .thenReturn(List.of());

    List<BillingDto> result = billingDataAdapter.getAllByProgressStatusAndManagerId(
        BillingProgressEnum.SUCCESS, 1L);

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(billingRepository, times(1)).getAllByProgressStatusAndManagerId(
        BillingProgressEnum.SUCCESS, 1L);
  }
}