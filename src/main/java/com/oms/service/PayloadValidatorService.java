package com.oms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.oms.commons.I18Constants;
import com.oms.exception.ElementAlreadyExistException;
import com.oms.exception.KeyNotFoundException;
import com.oms.exception.OMSValidationException;
import com.oms.model.CustomerInfo;
import com.oms.model.Order;
import com.oms.model.Payment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PayloadValidatorService {	
	
	private static final Logger logger = LoggerFactory.getLogger(PayloadValidatorService.class);
	
	private final MessageSource messageSource;
	
	public boolean isValidCustomerPayload(CustomerInfo customerInfo, CustomerInfoService customerInfoService) {
		logger.info("Validating customer payload for " + customerInfo.getId());
		checkForCustomerPayloadkeys(customerInfo);
		if(customerInfo.getCustomerBalance() < 0) {
			logger.error("Customer Balance value is negative for " + customerInfo.getId());
			throw new OMSValidationException(getLocalMessage(I18Constants.CUSTOMER_BALANCE_NEGATIVE.getKey()));
		} else if(customerInfo.getId() < 1) {
			logger.error("Customer Id is not positive for " + customerInfo.getId());
			throw new OMSValidationException(getLocalMessage(I18Constants.CUSTOMER_ID_NOT_POSITIVE.getKey()));
		}
		if(customerInfoService.isCustomerExist(customerInfo.getId())){
			logger.error("Customer Id "+ customerInfo.getId() + " already exists");
			throw new ElementAlreadyExistException(getLocalMessage(I18Constants.CUSTOMER_ALREADY_EXIST.getKey(), customerInfo.getId()));
		}
		return true;
	}
	
	private void checkForCustomerPayloadkeys(CustomerInfo customerInfo) {
		List<String> unavailableKeys = new ArrayList<String>();
		if(null == customerInfo.getId()) {
			unavailableKeys.add("id");
		}
		if (null == customerInfo.getCustomerName()) {
			unavailableKeys.add("customerName");
		}
		if(null == customerInfo.getCustomerBalance()) {
			unavailableKeys.add("customerBalance");
		}
		if(!unavailableKeys.isEmpty()) {
			logger.error("Keys:" + unavailableKeys.toString() + "are to be defined to create customer");
			throw new KeyNotFoundException(getKeyLocalMessage(I18Constants.CUSTOMER_KEYS_NOT_FOUND.getKey(), unavailableKeys.toString()));
		}
	}

	public boolean isValidOrderPayload(Order order, OrderInfoService orderInfoService) {
		logger.info("Validating order payload for " + order.getId());
		checkForOrderPayloadkeys(order);
		if(order.getOrderAmount() < 0) {
			logger.error("Order Amount is negative for " + order.getId());
			throw new OMSValidationException(getLocalMessage(I18Constants.ORDER_AMOUNT_NEGATIVE.getKey()));
		} else if(order.getId() < 1) {
			logger.error("Order Id is not positive for " + order.getId());
			throw new OMSValidationException(getLocalMessage(I18Constants.ORDER_ID_NOT_POSITIVE.getKey()));
		}
		if(orderInfoService.isOrderExist(order.getId())){
			logger.error("Order Id "+ order.getId() + " already exists");
			throw new ElementAlreadyExistException(getLocalMessage(I18Constants.ORDER_ALREADY_EXIST.getKey(), order.getId()));
		}
		return true;
	}
	
	private void checkForOrderPayloadkeys(Order order) {
		List<String> unavailableKeys = new ArrayList<String>();
		if(null == order.getId()) {
			unavailableKeys.add("id");
		}
		if (null == order.getOrderAmount()) {
			unavailableKeys.add("orderAmount");
		}
		if(null == order.getCustomerId()) {
			unavailableKeys.add("customerId");
		}
		if(!unavailableKeys.isEmpty()) {
			logger.error("Keys:" + unavailableKeys.toString() + "are to be defined to create order");
			throw new KeyNotFoundException(getKeyLocalMessage(I18Constants.ORDER_KEYS_NOT_FOUND.getKey(), unavailableKeys.toString()));
		}
	}
	
	public boolean isValidPaymentPayload(Payment payment, PaymentInfoService paymentInfoService) {
		logger.info("Validating payment payload for " + payment.getId());
		checkForPaymentPayloadkeys(payment);
		if(payment.getPaymentAmount() < 0) {
			logger.error("Payment Amount is negative for " + payment.getId());
			throw new OMSValidationException(getLocalMessage(I18Constants.PAYMENT_AMOUNT_NEGATIVE.getKey()));
		} else if(payment.getId() < 1) {
			logger.error("Payment Id is not positive for " + payment.getId());
			throw new OMSValidationException(getLocalMessage(I18Constants.PAYMENT_ID_NOT_POSITIVE.getKey()));
		}
		if(paymentInfoService.isPaymentExist(payment.getId())) {
			logger.error("Payment Id "+ payment.getId() + " already exists");
			throw new ElementAlreadyExistException(getLocalMessage(I18Constants.PAYMENT_ALREADY_EXIST.getKey(), payment.getId()));
		}
		return true;
	}
	
	private void checkForPaymentPayloadkeys(Payment payment) {
		List<String> unavailableKeys = new ArrayList<String>();
		if(null == payment.getId()) {
			unavailableKeys.add("id");
		}
		if (null == payment.getPaymentAmount()) {
			unavailableKeys.add("paymentAmount");
		}
		if(null == payment.getOrderId()) {
			unavailableKeys.add("orderId");
		}
		if(!unavailableKeys.isEmpty()) {
			logger.error("Keys:" + unavailableKeys.toString() + "are to be defined to create payment");
			throw new KeyNotFoundException(getKeyLocalMessage(I18Constants.PAYMENT_KEYS_NOT_FOUND.getKey(), unavailableKeys.toString()));
		}
	}
	
	private String getLocalMessage(String key, Long... params){
        return messageSource.getMessage(key,
                params,
                Locale.ENGLISH);
    }
	
	private String getKeyLocalMessage(String key, String... params){
        return messageSource.getMessage(key,
                params,
                Locale.ENGLISH);
    }
}
