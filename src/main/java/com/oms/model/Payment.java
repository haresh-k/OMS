package com.oms.model;

public class Payment {
	private Long id;
	private Long paymentAmount;
	private Long orderId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", paymentAmount=" + paymentAmount + ", orderId=" + orderId + "]";
	}
	
}
