package com.oms.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.oms.controller.OrderController;

@SpringBootApplication
@ComponentScan(basePackageClasses = OrderController.class)
public class OMS {
	public static void main(String[] args) {
	    SpringApplication.run(OMS.class, args);
	  }
}
