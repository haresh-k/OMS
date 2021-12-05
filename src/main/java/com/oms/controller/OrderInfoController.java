package com.oms.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oms.model.CustomerInfo;
import com.oms.model.Order;
import com.oms.model.OrderInfo;
import com.oms.service.CustomerInfoService;
import com.oms.service.OrderInfoService;
import com.oms.utils.Wrapper;

@RestController
@RequestMapping("/v1/order")
public class OrderInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);
	
	@Autowired
	OrderInfoService orderInfoService;
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	@GetMapping("{id}")
	public @ResponseBody Wrapper<OrderInfo> getOrder(@PathVariable("id") long orderId) {
		logger.info("Received getOrder request with orderId=" + orderId);
		OrderInfo order = orderInfoService.getOrderInfoById(orderId);
		return Wrapper.wrap(order);
	}
	
	@PostMapping("/")
	public Wrapper<HttpStatus> addOrder(@RequestBody Order order) {
		logger.info("Received addOrder request with " + order.toString());
		CustomerInfo customerInfo = customerInfoService.getCustomerInfoById(order.getCustomerId());
		customerInfo.setCustomerBalance(customerInfo.getCustomerBalance() - order.getAmount());
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(order.getId());
		orderInfo.setAmount(order.getAmount());
		orderInfo.setCustomerInfo(customerInfo);
		if(customerInfo.getOrders().isEmpty()) {
			 List<OrderInfo> orderInfos = new ArrayList<>();
			 orderInfos.add(orderInfo);
			 customerInfo.setOrders(orderInfos);
		} else {
			List<OrderInfo> orderInfos = customerInfo.getOrders();
			orderInfos.add(orderInfo);
			customerInfo.setOrders(orderInfos);
		}
		return customerInfoService.addCustomer(customerInfo)? Wrapper.wrap(HttpStatus.CREATED) : Wrapper.wrap(HttpStatus.BAD_REQUEST);
	}
}
