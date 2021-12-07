package com.oms.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.oms.service.PayloadValidatorService;
import com.oms.utils.Wrapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/order")
public class OrderInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);
	
	@Autowired
	OrderInfoService orderInfoService;
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	@Autowired
	PayloadValidatorService payloadValidator;
	
	@Operation(summary = "Get an order by passing id")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
	  @ApiResponse(responseCode = "400", description = "Payload validation errors", 
	    content = @Content) })
	@GetMapping("{id}")
	public @ResponseBody Wrapper<OrderInfo> getOrder(@PathVariable("id") long orderId) {
		logger.info("Received getOrder request with orderId=" + orderId);
		OrderInfo order = orderInfoService.getOrderInfoById(orderId);
		return Wrapper.wrap(order);
	}
	
	@Operation(summary = "Create order by passing id, orderAmount and customer Id. [orderBalance is always mapped as negative orderAmount]")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Successfully created"),
	  @ApiResponse(responseCode = "400", description = "Payload validation errors", 
	    content = @Content) })
	@PostMapping("/")
	public Wrapper<String> addOrder(@RequestBody Order order) {
		logger.info("Received addOrder request with " + order.toString());
		payloadValidator.isValidOrderPayload(order, orderInfoService);
		CustomerInfo customerInfo = customerInfoService.getCustomerInfoById(order.getCustomerId());
		customerInfo.setCustomerBalance(customerInfo.getCustomerBalance() - order.getOrderAmount());
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(order.getId());
		orderInfo.setOrderAmount(order.getOrderAmount());
		orderInfo.setOrderBalance(-order.getOrderAmount());
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
		customerInfoService.addCustomer(customerInfo);
		return Wrapper.wrap(null);
	}
}
