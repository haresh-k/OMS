package com.oms.service;

import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.oms.commons.I18Constants;
import com.oms.exception.NoSuchElementFoundException;
import com.oms.model.CustomerInfo;
import com.oms.repository.CustomerInfoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerInfoServiceImpl implements CustomerInfoService {

	@Autowired
	CustomerInfoRepository customerInfoRepository;
	
	private final MessageSource messageSource;

	@Override
	@Transactional
	public boolean isCustomerExist(long customerId) {
		return customerInfoRepository.existsById(customerId);
	}
	
	@Override
	@Transactional
	public CustomerInfo getCustomerInfoById(long customerId) {
		return customerInfoRepository.findById(customerId).orElseThrow(() -> 
					new NoSuchElementFoundException(getLocalMessage(I18Constants.CUSTOMER_NOT_FOUND.getKey(), customerId)));
	}

	@Override
	@Transactional
	public boolean addCustomer(CustomerInfo customerInfo) {
		return customerInfoRepository.save(customerInfo) != null;
	}

	private String getLocalMessage(String key, Long... params){
        return messageSource.getMessage(key,
                params,
                Locale.ENGLISH);
    }
	
}
