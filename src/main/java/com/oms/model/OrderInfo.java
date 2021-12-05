package com.oms.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "order_info")
public class OrderInfo {
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="amount")
	private Long amount;
	
	@ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties(value = {"orders"})
	@JsonIgnore
	private CustomerInfo customerInfo;

	@OneToMany(targetEntity=PaymentInfo.class, mappedBy="orderInfo",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"orderInfo"})
	private List<PaymentInfo> payments;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public List<PaymentInfo> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentInfo> paymentInfo) {
		this.payments = paymentInfo;
		paymentInfo.forEach(entity -> entity.setOrderInfo(this));
	}

	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", amount=" + amount + ", customerInfo=" + customerInfo + ", payments="
				+ payments + "]";
	}
	
}
