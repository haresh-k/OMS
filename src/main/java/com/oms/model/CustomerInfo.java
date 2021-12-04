package com.oms.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "customer_info")
public class CustomerInfo {
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="customer_wallet")
	private Long customerBalance;
	
	@OneToMany(targetEntity=OrderInfo.class, mappedBy="customerInfo",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"customerInfo"})
	private List<OrderInfo> orders;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(Long customerBalance) {
		this.customerBalance = customerBalance;
	}

	public List<OrderInfo> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderInfo> orders) {
		this.orders = orders;
		orders.forEach(entity -> entity.setCustomerInfo(this));
	}

}
