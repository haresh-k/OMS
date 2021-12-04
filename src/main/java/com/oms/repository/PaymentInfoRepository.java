package com.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.PaymentInfo;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
	@Query("SELECT o FROM PaymentInfo o where o.id = :id")
	PaymentInfo getById(@Param(value="id") long paymentId);
}
