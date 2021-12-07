package com.oms.model;

public class Order {
	private Long id;
	private Long orderAmount;
	private Long customerId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", orderAmount=" + orderAmount + ", customerId=" + customerId + "]";
	}
	
}
