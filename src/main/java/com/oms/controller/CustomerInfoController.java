package com.oms.controller;

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
import com.oms.service.CustomerInfoService;
import com.oms.service.PayloadValidatorService;
import com.oms.utils.Wrapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/customer")
public class CustomerInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	@Autowired
	PayloadValidatorService payloadValidator;
	
	@Operation(summary = "Get a customer by passing id")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
	  @ApiResponse(responseCode = "400", description = "Payload validation errors", 
	    content = @Content) })
	@GetMapping("{id}")
	public @ResponseBody Wrapper<CustomerInfo> getCustomer(@PathVariable("id") long customerId) {
		logger.info("Received getCustomer request with customerId=" + customerId);
		CustomerInfo customer = customerInfoService.getCustomerInfoById(customerId);
		return Wrapper.wrap(customer);
	}
	
	@Operation(summary = "Create customer by passing id, customerName and customerBalance. [Orders array can be empty]")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Successfully created"),
			  @ApiResponse(responseCode = "400", description = "Payload validation errors", 
			    content = @Content) })
	@PostMapping("/")
	public Wrapper<String> addCustomer(@RequestBody CustomerInfo customerInfo) {
		logger.info("Received addCustomer request with " + customerInfo.toString());
		payloadValidator.isValidCustomerPayload(customerInfo, customerInfoService);
		customerInfoService.addCustomer(customerInfo);
		return Wrapper.wrap(null);    
	}     

}
