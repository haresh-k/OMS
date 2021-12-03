package com.oms.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@PostMapping("{name}")
	public String getOrder(@PathVariable("name") String orderName) {
		System.out.println(orderName);
		return "This is the beginning of the order";
	}
}
