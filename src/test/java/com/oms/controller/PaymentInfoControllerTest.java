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
import com.oms.model.Payment;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaymentInfoControllerTest extends ApiTestBase {
	
	String uri = "/v1/payment/";
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void test01_createValidPayment() throws Exception {
		Payment payment = new Payment();
		payment.setId(Long.valueOf("1"));
		payment.setPaymentAmount(Long.valueOf("100"));
		payment.setOrderId(Long.valueOf("1"));
		String inputJson = super.mapToJson(payment);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void test02_createPaymentWithNegativeAmount() throws Exception {
		Payment payment = new Payment();
		payment.setId(Long.valueOf("2"));
		payment.setPaymentAmount(Long.valueOf("-100"));
		payment.setOrderId(Long.valueOf("1"));
		String inputJson = super.mapToJson(payment);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("paymentAmount cannot be negative", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test03_createPaymentWithNegativeId() throws Exception {
		Payment payment = new Payment();
		payment.setId(Long.valueOf("-2"));
		payment.setPaymentAmount(Long.valueOf("100"));
		payment.setOrderId(Long.valueOf("1"));
		String inputJson = super.mapToJson(payment);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("paymentId should be positive", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test04_createPaymentWithExistingId() throws Exception {
		Payment payment = new Payment();
		payment.setId(Long.valueOf("1"));
		payment.setPaymentAmount(Long.valueOf("100"));
		payment.setOrderId(Long.valueOf("1"));
		String inputJson = super.mapToJson(payment);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Payment with id 1 already exist", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test05_createPaymentWithInvalidOrderId() throws Exception {
		Payment payment = new Payment();
		payment.setId(Long.valueOf("2"));
		payment.setPaymentAmount(Long.valueOf("100"));
		payment.setOrderId(Long.valueOf("100"));
		String inputJson = super.mapToJson(payment);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Order with id 100 not found", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test06_createPaymentWithNoId() throws Exception {
		Payment payment = new Payment();
		payment.setPaymentAmount(Long.valueOf("100"));
		payment.setOrderId(Long.valueOf("1"));
		String inputJson = super.mapToJson(payment);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
		         .contentType(MediaType.APPLICATION_JSON)
		         .content(inputJson)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Keys:[id] are to be defined to create payment", mvcResult.getResponse().getErrorMessage());
	}
	
	@Test
	public void test07_getPaymentWithExistingId() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+"1")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(200, mvcResult.getResponse().getStatus());
	    JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString())
	    		.getJSONObject("data");
	    assertEquals("1", json.getString("id"));
	    assertEquals("100", json.getString("paymentAmount"));
	}
	
	@Test
	public void test08_getPaymentWithInvalidId() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+"100")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    assertEquals(400, mvcResult.getResponse().getStatus());
	    assertEquals("Payment with id 100 not found", mvcResult.getResponse().getErrorMessage());
	}
}
