package com.oms.service;

import com.oms.model.CustomerInfo;

public interface CustomerInfoService {
	public boolean isCustomerExist(long customerId);
	public abstract CustomerInfo getCustomerInfoById(long customerId);
	public abstract boolean addCustomer(CustomerInfo customerInfo);
}
