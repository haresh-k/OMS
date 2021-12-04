package com.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oms.model.CustomerInfo;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long>{
	@Query("SELECT o FROM CustomerInfo o where o.id = :id")
	CustomerInfo getById(@Param(value="id") long customerId);
}
