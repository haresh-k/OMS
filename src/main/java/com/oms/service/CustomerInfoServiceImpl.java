package com.oms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oms.model.CustomerInfo;
import com.oms.repository.CustomerInfoRepository;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

	@Autowired
	CustomerInfoRepository customerInfoRepository;
	
	@Override
	@Transactional
	public CustomerInfo getCustomerInfoById(long customerId) {
		return customerInfoRepository.getById(customerId);
	}

	@Override
	@Transactional
	public boolean addCustomer(CustomerInfo customerInfo) {
		return customerInfoRepository.save(customerInfo) != null;
	}

}
