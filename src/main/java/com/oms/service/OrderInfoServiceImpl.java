package com.oms.service;

import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.oms.commons.I18Constants;
import com.oms.exception.NoSuchElementFoundException;
import com.oms.model.OrderInfo;
import com.oms.repository.OrderInfoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderInfoServiceImpl implements OrderInfoService{
	
	@Autowired
	OrderInfoRepository orderInfoRepository;
	
	private final MessageSource messageSource;

	@Override
	@Transactional
	public boolean isOrderExist(long orderId) {
		return orderInfoRepository.existsById(orderId);
	}
	
	@Override
	@Transactional
	public OrderInfo getOrderInfoById(long orderId) {
		return orderInfoRepository.findById(orderId).orElseThrow(() -> 
				new NoSuchElementFoundException(getLocalMessage(I18Constants.ORDER_NOT_FOUND.getKey(), orderId)));
	}

	@Override
	@Transactional
	public boolean addOrder(OrderInfo orderInfo) {
		return orderInfoRepository.save(orderInfo) != null;
	}
	
	private String getLocalMessage(String key, Long... params){
        return messageSource.getMessage(key,
                params,
                Locale.ENGLISH);
    }

}
