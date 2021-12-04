package com.oms.service;

import com.oms.model.CustomerInfo;

public interface CustomerInfoService {
	public abstract CustomerInfo getCustomerInfoById(long customerId);
	public abstract boolean addCustomer(CustomerInfo customerInfo);
}
