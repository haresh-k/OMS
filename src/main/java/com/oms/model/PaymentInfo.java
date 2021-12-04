package com.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "payment_info")
public class PaymentInfo {
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="amount")
	private Long amount;
	
	@ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties(value = {"payments"})
	@JsonIgnore
	private OrderInfo orderInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	
}
