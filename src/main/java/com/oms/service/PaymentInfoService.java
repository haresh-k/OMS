package com.oms.service;

import com.oms.model.PaymentInfo;

public interface PaymentInfoService {
	public abstract PaymentInfo getPaymentInfoById(long paymentId);
	public abstract void addPayment(PaymentInfo paymentInfo);
}
