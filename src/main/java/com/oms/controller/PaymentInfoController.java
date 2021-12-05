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
import com.oms.model.OrderInfo;
import com.oms.model.Payment;
import com.oms.model.PaymentInfo;
import com.oms.service.CustomerInfoService;
import com.oms.service.OrderInfoService;
import com.oms.service.PaymentInfoService;
import com.oms.utils.Wrapper;

@RestController
@RequestMapping("/v1/payment")
public class PaymentInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentInfoController.class);
	
	@Autowired
	PaymentInfoService paymentInfoService;
	
	@Autowired
	OrderInfoService orderInfoService;
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	@GetMapping("{id}")
	public @ResponseBody Wrapper<PaymentInfo> getPayment(@PathVariable("id") long paymentId) {
		logger.info("Received getPayment request with orderId=" + paymentId);
		PaymentInfo payment = paymentInfoService.getPaymentInfoById(paymentId);
		return Wrapper.wrap(payment);
	}
	
	@PostMapping("/")
	public Wrapper<HttpStatus> addPayment(@RequestBody Payment payment) {
		logger.info("Received addPayment request with " + payment.toString());
		OrderInfo orderInfo =  orderInfoService.getOrderInfoById(payment.getOrderId());
		CustomerInfo customerInfo = orderInfo.getCustomerInfo();
		orderInfo.setAmount(orderInfo.getAmount() - payment.getAmount());
		customerInfo.setCustomerBalance(customerInfo.getCustomerBalance() + payment.getAmount());
		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setId(payment.getId());
		paymentInfo.setAmount(payment.getAmount());
		paymentInfo.setOrderInfo(orderInfo);
		if(orderInfo.getPayments().isEmpty()) {
			List<PaymentInfo> paymentInfos = new ArrayList<>();
			paymentInfos.add(paymentInfo);
			orderInfo.setPayments(paymentInfos);
		} else {
			List<PaymentInfo> paymentInfos = new ArrayList<>();
			paymentInfos.add(paymentInfo);
			orderInfo.setPayments(paymentInfos);
		}
		return orderInfoService.addOrder(orderInfo) ? Wrapper.wrap(HttpStatus.CREATED) : Wrapper.wrap(HttpStatus.BAD_REQUEST);
	}
}
