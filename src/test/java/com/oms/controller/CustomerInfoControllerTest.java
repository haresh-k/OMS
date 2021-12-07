package com.oms.controller;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.oms.ApiTestBase;
import com.oms.model.CustomerInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerInfoControllerTest extends ApiTestBase {
	
	String uri = "/v1/customer/";
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void test01_createValidCustomer() throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(Long.valueOf("1"));
		customerInfo.setCustomerName("Haresh");
		customerInfo.setCustomerBalance(Long.valueOf("100"));
		customerInfo.setOrders(Collections.emptyList());
		String inputJson = super.mapToJson(customerInfo);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void test02_createCustomerWithNegativeBalance() throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(Long.valueOf("2"));
		customerInfo.setCustomerName("Haresh");
		customerInfo.setCustomerBalance(Long.valueOf("-100"));
		customerInfo.setOrders(Collections.emptyList());
		String inputJson = super.mapToJson(customerInfo);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("customerBalance cannot be negative", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test03_createCustomerWithNegativeId() throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(Long.valueOf("-2"));
		customerInfo.setCustomerName("Haresh");
		customerInfo.setCustomerBalance(Long.valueOf("100"));
		customerInfo.setOrders(Collections.emptyList());
		String inputJson = super.mapToJson(customerInfo);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("customerId should be positive", mvcResult.getResponse().getErrorMessage());
	}

	@Test
	public void test04_createCustomerWithExistingId() throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(Long.valueOf("1"));
		customerInfo.setCustomerName("Kannan");
		customerInfo.setCustomerBalance(Long.valueOf("100"));
		customerInfo.setOrders(Collections.emptyList());
		String inputJson = super.mapToJson(customerInfo);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Customer with id 1 already exist", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test05_createCustomerWithNoId() throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setCustomerName("Kannan");
		customerInfo.setCustomerBalance(Long.valueOf("100"));
		customerInfo.setOrders(Collections.emptyList());
		String inputJson = super.mapToJson(customerInfo);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Keys:[id] are to be defined to create customer", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test06_getCustomerWithExistingId() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+"1")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString())
	    		.getJSONObject("data");
	    assertEquals("1", json.getString("id"));
	    assertEquals("Haresh", json.getString("customerName"));
	    assertEquals("100", json.getString("customerBalance"));
	}
	
	@Test
	public void test07_getCustomerWithInvalidId() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+"100")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Customer with id 100 not found", mvcResult.getResponse().getErrorMessage());
	}
	
}
