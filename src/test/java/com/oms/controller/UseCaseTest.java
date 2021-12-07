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
import com.oms.model.Order;
import com.oms.model.Payment;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UseCaseTest extends ApiTestBase {

	String customerUri = "/v1/customer/";
	String orderUri = "/v1/order/";
	String paymentUri = "/v1/payment/";
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void test01_createCustomerWithZeroBalance() throws Exception {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(Long.valueOf("1001"));
		customerInfo.setCustomerName("John");
		customerInfo.setCustomerBalance(Long.valueOf("0"));
		customerInfo.setOrders(Collections.emptyList());
		String inputJson = super.mapToJson(customerInfo);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(customerUri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    
	    mvcResult = mvc.perform(MockMvcRequestBuilders.get(customerUri+"1001")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString())
	    		.getJSONObject("data");
	    assertEquals("1001", json.getString("id"));
	    assertEquals("John", json.getString("customerName"));
	    assertEquals("0", json.getString("customerBalance"));
	}
	
	@Test
	public void test02_createFirstOrder() throws Exception {
		Order order = new Order();
		order.setId(Long.valueOf("1001"));
		order.setOrderAmount(Long.valueOf("100"));
		order.setCustomerId(Long.valueOf("1001"));
		String inputJson = super.mapToJson(order);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(orderUri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    
	    mvcResult = mvc.perform(MockMvcRequestBuilders.get(customerUri+"1001")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString())
	    		.getJSONObject("data");
	    assertEquals("1001", json.getString("id"));
	    assertEquals("John", json.getString("customerName"));
	    assertEquals("-100", json.getString("customerBalance"));
	    JSONObject order1 = json.getJSONArray("orders").getJSONObject(0);
	    assertEquals("1001", order1.getString("id"));
	    assertEquals("100", order1.getString("orderAmount"));
	    assertEquals("-100", order1.getString("orderBalance"));
	}
	
	@Test
	public void test03_createPayment() throws Exception {
		Payment payment = new Payment();
		payment.setId(Long.valueOf("1001"));
		payment.setPaymentAmount(Long.valueOf("110"));
		payment.setOrderId(Long.valueOf("1001"));
		String inputJson = super.mapToJson(payment);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(paymentUri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    
	    mvcResult = mvc.perform(MockMvcRequestBuilders.get(customerUri+"1001")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString())
	    		.getJSONObject("data");
	    assertEquals("1001", json.getString("id"));
	    assertEquals("John", json.getString("customerName"));
	    assertEquals("10", json.getString("customerBalance"));
	    JSONObject order1 = json.getJSONArray("orders").getJSONObject(0);
	    assertEquals("1001", order1.getString("id"));
	    assertEquals("100", order1.getString("orderAmount"));
	    assertEquals("10", order1.getString("orderBalance"));
	    JSONObject payment1 = order1.getJSONArray("payments").getJSONObject(0);
	    assertEquals("1001", payment1.getString("id"));
	    assertEquals("110", payment1.getString("paymentAmount"));
	}
	
	@Test
	public void test04_createSecondOrder() throws Exception {
		Order order = new Order();
		order.setId(Long.valueOf("1002"));
		order.setOrderAmount(Long.valueOf("50"));
		order.setCustomerId(Long.valueOf("1001"));
		String inputJson = super.mapToJson(order);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(orderUri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    
	    mvcResult = mvc.perform(MockMvcRequestBuilders.get(customerUri+"1001")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString())
	    		.getJSONObject("data");
	    assertEquals("1001", json.getString("id"));
	    assertEquals("John", json.getString("customerName"));
	    assertEquals("-40", json.getString("customerBalance"));
	    JSONObject order1 = json.getJSONArray("orders").getJSONObject(0);
	    assertEquals("1001", order1.getString("id"));
	    assertEquals("100", order1.getString("orderAmount"));
	    assertEquals("10", order1.getString("orderBalance"));
	    JSONObject payment1 = order1.getJSONArray("payments").getJSONObject(0);
	    assertEquals("1001", payment1.getString("id"));
	    assertEquals("110", payment1.getString("paymentAmount"));
	    JSONObject order2 = json.getJSONArray("orders").getJSONObject(1);
	    assertEquals("1002", order2.getString("id"));
	    assertEquals("50", order2.getString("orderAmount"));
	    assertEquals("-50", order2.getString("orderBalance"));
	}
	
}
