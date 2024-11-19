package com.javacase.accountingapi.adapters.manager.lock;

import com.javacase.accountingapi.common.exception.AccountingApiException;
import com.javacase.accountingapi.common.exception.ErrorCode;
import com.javacase.accountingapi.manager.port.ManagerLockPort;
import java.util.concurrent.locks.Lock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagerLockRedisAdapter implements ManagerLockPort {

  @Qualifier("managerLockRegistry")
  private final RedisLockRegistry redisLockRegistry;

  @Override
  public void lock(Long managerId) {
    Lock lock = redisLockRegistry.obtain(managerId.toString());

    if (!lock.tryLock()) {
      throw new AccountingApiException(ErrorCode.ACCOUNT_LOCK_ERROR);
    }
    log.info("managerId:{} locked", managerId);
  }

  @Override
  public void unlock(Long managerId) {
    try {
      redisLockRegistry.obtain(managerId.toString()).unlock();
      log.info("managerId:{} unlocked", managerId);
    } catch (RuntimeException e) {
      log.error("managerId:{} unlocked error", managerId, e);
    }


  }
}
