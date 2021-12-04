package com.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.OrderInfo;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
	@Query("SELECT o FROM OrderInfo o where o.id = :id")
	OrderInfo getById(@Param(value="id") long orderId);
}
