package com.oms.service;

import com.oms.model.OrderInfo;

public interface OrderInfoService {
	public boolean isOrderExist(long orderId);
	public abstract OrderInfo getOrderInfoById(long orderId);
	public abstract boolean addOrder(OrderInfo orderInfo);
}
