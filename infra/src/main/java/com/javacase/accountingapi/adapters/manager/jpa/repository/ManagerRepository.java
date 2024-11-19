package com.javacase.accountingapi.adapters.manager.jpa.repository;


import com.javacase.accountingapi.adapters.manager.jpa.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {

}
