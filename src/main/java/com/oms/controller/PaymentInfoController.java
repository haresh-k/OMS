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
import com.oms.model.OrderInfo;
import com.oms.model.Payment;
import com.oms.model.PaymentInfo;
import com.oms.service.CustomerInfoService;
import com.oms.service.OrderInfoService;
import com.oms.service.PayloadValidatorService;
import com.oms.service.PaymentInfoService;
import com.oms.utils.Wrapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
	
	@Autowired
	PayloadValidatorService payloadValidator;
	
	@Operation(summary = "Get a payment by passing id")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
	  @ApiResponse(responseCode = "400", description = "Payload validation errors", 
	    content = @Content) })
	@GetMapping("{id}")
	public @ResponseBody Wrapper<PaymentInfo> getPayment(@PathVariable("id") long paymentId) {
		logger.info("Received getPayment request with orderId=" + paymentId);
		PaymentInfo payment = paymentInfoService.getPaymentInfoById(paymentId);
		return Wrapper.wrap(payment);
	}
	
	@Operation(summary = "Create payment by passing id, paymentAmount and orderId")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Successfully created"),
	  @ApiResponse(responseCode = "400", description = "Payload validation errors", 
	    content = @Content) })
	@PostMapping("/")
	public Wrapper<String> addPayment(@RequestBody Payment payment) {
		logger.info("Received addPayment request with " + payment.toString());
		payloadValidator.isValidPaymentPayload(payment, paymentInfoService);
		OrderInfo orderInfo =  orderInfoService.getOrderInfoById(payment.getOrderId());
		CustomerInfo customerInfo = orderInfo.getCustomerInfo();
		orderInfo.setOrderBalance(orderInfo.getOrderBalance() + payment.getPaymentAmount());
		customerInfo.setCustomerBalance(customerInfo.getCustomerBalance() + payment.getPaymentAmount());
		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setId(payment.getId());
		paymentInfo.setPaymentAmount(payment.getPaymentAmount());
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
		orderInfoService.addOrder(orderInfo);
		return Wrapper.wrap(null);
	}
}
