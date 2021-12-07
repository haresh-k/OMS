package com.oms.controller;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.oms.ApiTestBase;
import com.oms.model.Order;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderInfoControllerTest extends ApiTestBase {
	
	String uri = "/v1/order/";
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void test01_createValidOrder() throws Exception {
		Order order = new Order();
		order.setId(Long.valueOf("1"));
		order.setOrderAmount(Long.valueOf("100"));
		order.setCustomerId(Long.valueOf("1"));
		String inputJson = super.mapToJson(order);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void test02_createOrderWithNegativeAmount() throws Exception {
		Order order = new Order();
		order.setId(Long.valueOf("2"));
		order.setOrderAmount(Long.valueOf("-100"));
		order.setCustomerId(Long.valueOf("1"));
		String inputJson = super.mapToJson(order);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("orderAmount cannot be negative", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test03_createOrderWithNegativeId() throws Exception {
		Order order = new Order();
		order.setId(Long.valueOf("-2"));
		order.setOrderAmount(Long.valueOf("100"));
		order.setCustomerId(Long.valueOf("1"));
		String inputJson = super.mapToJson(order);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("orderId should be positive", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test04_createOrderWithExistingId() throws Exception {
		Order order = new Order();
		order.setId(Long.valueOf("1"));
		order.setOrderAmount(Long.valueOf("100"));
		order.setCustomerId(Long.valueOf("1"));
		String inputJson = super.mapToJson(order);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Order with id 1 already exist", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test05_createOrderWithInvalidCustomerId() throws Exception {
		Order order = new Order();
		order.setId(Long.valueOf("2"));
		order.setOrderAmount(Long.valueOf("100"));
		order.setCustomerId(Long.valueOf("100"));
		String inputJson = super.mapToJson(order);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Customer with id 100 not found", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test06_createOrderWithNoId() throws Exception {
		Order order = new Order();
		order.setOrderAmount(Long.valueOf("100"));
		order.setCustomerId(Long.valueOf("1"));
		String inputJson = super.mapToJson(order);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Keys:[id] are to be defined to create order", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test07_getOrderWithExistingId() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+"1")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString())
	    		.getJSONObject("data");
	    assertEquals("1", json.getString("id"));
	    assertEquals("100", json.getString("orderAmount"));
	    assertEquals("-100", json.getString("orderBalance"));
	}
	
	@Test
	public void test08_getOrderWithInvalidId() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+"100")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Order with id 100 not found", mvcResult.getResponse().getErrorMessage());
	}
}
