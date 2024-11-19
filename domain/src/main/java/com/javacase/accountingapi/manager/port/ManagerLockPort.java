package com.javacase.accountingapi.manager.port;

public interface ManagerLockPort {

  void lock(Long manager);

  void unlock(Long manager);
}
