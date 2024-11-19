package com.javacase.accountingapi.adapters;

import com.javacase.accountingapi.manager.port.ManagerLockPort;
import java.util.HashSet;
import java.util.Set;

public class ManagerLockFakeAdapter implements ManagerLockPort {

  public final Set<Long> lockedManagers = new HashSet<>();

  @Override
  public void lock(Long managerId) {
    lockedManagers.add(managerId);
  }

  @Override
  public void unlock(Long managerId) {
    try {
      lockedManagers.remove(managerId);
    } catch (Exception e) {
      return;
    }

  }
}