package com.oms.service;

import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.oms.commons.I18Constants;
import com.oms.exception.NoSuchElementFoundException;
import com.oms.model.PaymentInfo;
import com.oms.repository.PaymentInfoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentInfoServiceImpl implements PaymentInfoService {

	@Autowired
	PaymentInfoRepository paymentInfoRepository;
	
	private final MessageSource messageSource;
	
	@Override
	@Transactional
	public boolean isPaymentExist(long paymentId) {
		return paymentInfoRepository.existsById(paymentId);
	}
	
	@Override
	@Transactional
	public PaymentInfo getPaymentInfoById(long paymentId) {
		return paymentInfoRepository.findById(paymentId).orElseThrow(() -> 
				new NoSuchElementFoundException(getLocalMessage(I18Constants.PAYMENT_NOT_FOUND.getKey(), paymentId)));
	}

	@Override
	@Transactional
	public void addPayment(PaymentInfo paymentInfo) {
		paymentInfoRepository.save(paymentInfo);
	}

	private String getLocalMessage(String key, Long... params){
        return messageSource.getMessage(key,
                params,
                Locale.ENGLISH);
    }
	
}
