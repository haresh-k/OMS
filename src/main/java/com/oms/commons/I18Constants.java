package com.oms.commons;

public enum I18Constants {
	CUSTOMER_NOT_FOUND("customer.not.found"),
	ORDER_NOT_FOUND("order.not.found"),
	PAYMENT_NOT_FOUND("payment.not.found"),
	CUSTOMER_ALREADY_EXIST("customer.already.exist"),
	ORDER_ALREADY_EXIST("order.already.exist"),
	PAYMENT_ALREADY_EXIST("payment.already.exist");
	
	String key;
    I18Constants(String key) {
        this.key = key;
    }
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
