package com.oms.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.oms.commons.I18Constants;
import com.oms.exception.ElementAlreadyExistException;
import com.oms.model.CustomerInfo;
import com.oms.model.Order;
import com.oms.model.Payment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PayloadValidatorService {	
	
	private final MessageSource messageSource;
	
	public boolean isValidCustomerPayload(CustomerInfo customerInfo, CustomerInfoService customerInfoService) {
		if(customerInfo.getCustomerBalance() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer Balance cannot be negative");
		} else if(customerInfo.getId() < 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer Id should be positive");
		}
		if(customerInfoService.isCustomerExist(customerInfo.getId())){
			throw new ElementAlreadyExistException(getLocalMessage(I18Constants.CUSTOMER_ALREADY_EXIST.getKey(), customerInfo.getId()));
		}
		return true;
	}
	
	public boolean isValidOrderPayload(Order order, OrderInfoService orderInfoService) {
		if(order.getAmount() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Amount cannot be negative");
		} else if(order.getId() < 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Id should be positive");
		}
		if(orderInfoService.isOrderExist(order.getId())){
			throw new ElementAlreadyExistException(getLocalMessage(I18Constants.ORDER_ALREADY_EXIST.getKey(), order.getId()));
		}
		return true;
	}
	
	public boolean isValidPaymentPayload(Payment payment, PaymentInfoService paymentInfoService) {
		if(payment.getAmount() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment Amount cannot be negative");
		} else if(payment.getId() < 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment Id should be positive");
		}
		if(paymentInfoService.isPaymentExist(payment.getId())) {
			throw new ElementAlreadyExistException(getLocalMessage(I18Constants.PAYMENT_ALREADY_EXIST.getKey(), payment.getId()));
		}
		return true;
	}
	
	private String getLocalMessage(String key, Long... params){
        return messageSource.getMessage(key,
                params,
                Locale.ENGLISH);
    }
}
