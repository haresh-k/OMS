package com.oms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oms.model.OrderInfo;
import com.oms.repository.OrderInfoRepository;

@Service
public class OrderInfoServiceImpl implements OrderInfoService{
	
	@Autowired
	OrderInfoRepository orderInfoRepository;
	
	@Override
	@Transactional
	public OrderInfo getOrderInfoById(long orderId) {
		return orderInfoRepository.getById(orderId);
	}

	@Override
	@Transactional
	public boolean addOrder(OrderInfo orderInfo) {
		return orderInfoRepository.save(orderInfo) != null;
	}
	
}
