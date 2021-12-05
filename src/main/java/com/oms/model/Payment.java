package com.oms.model;

public class Payment {
	private long id;
	private long amount;
	private long orderId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", amount=" + amount + ", orderId=" + orderId + "]";
	}
	
}
