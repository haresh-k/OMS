package com.oms.commons;

public enum I18Constants {
	CUSTOMER_NOT_FOUND("customer.not.found"),
	CUSTOMER_ALREADY_EXIST("customer.already.exist"),
	CUSTOMER_BALANCE_NEGATIVE("customer.balance.negative"),
	CUSTOMER_ID_NOT_POSITIVE("customer.id.not.positive"),
	CUSTOMER_KEYS_NOT_FOUND("customer.keys.not.found"),
	ORDER_NOT_FOUND("order.not.found"),
	ORDER_ALREADY_EXIST("order.already.exist"),
	ORDER_AMOUNT_NEGATIVE("order.amount.negative"),
	ORDER_ID_NOT_POSITIVE("order.id.not.positive"),
	ORDER_KEYS_NOT_FOUND("order.keys.not.found"),
	PAYMENT_NOT_FOUND("payment.not.found"),
	PAYMENT_ALREADY_EXIST("payment.already.exist"),
	PAYMENT_AMOUNT_NEGATIVE("payment.amount.negative"),
	PAYMENT_ID_NOT_POSITIVE("payment.id.not.positive"),
	PAYMENT_KEYS_NOT_FOUND("payment.keys.not.found");
	
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
