package com.oms.service;

import com.oms.model.PaymentInfo;

public interface PaymentInfoService {
	public boolean isPaymentExist(long paymentId);
	public abstract PaymentInfo getPaymentInfoById(long paymentId);
	public abstract void addPayment(PaymentInfo paymentInfo);
}
