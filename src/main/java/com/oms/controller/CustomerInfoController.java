package com.oms.controller;

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
import com.oms.service.CustomerInfoService;
import com.oms.utils.Wrapper;

@RestController
@RequestMapping("/v1/customer")
public class CustomerInfoController {
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	@GetMapping("{id}")
	public @ResponseBody Wrapper<CustomerInfo> getCustomer(@PathVariable("id") long customerId) {
		CustomerInfo customer = customerInfoService.getCustomerInfoById(customerId);
		return Wrapper.wrap(customer);
	}
	
	@PostMapping("/")
	public Wrapper<HttpStatus> addOrder(@RequestBody CustomerInfo customerInfo) {    
		return customerInfoService.addCustomer(customerInfo) ? Wrapper.wrap(HttpStatus.CREATED) : Wrapper.wrap(HttpStatus.BAD_REQUEST);    
	}     

}
