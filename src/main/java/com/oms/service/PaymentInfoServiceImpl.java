package com.oms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oms.model.PaymentInfo;
import com.oms.repository.PaymentInfoRepository;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

	@Autowired
	PaymentInfoRepository paymentInfoRepository;
	
	@Override
	public PaymentInfo getPaymentInfoById(long paymentId) {
		return paymentInfoRepository.getById(paymentId);
	}

	@Override
	public void addPayment(PaymentInfo paymentInfo) {
		paymentInfoRepository.save(paymentInfo);
	}

}
