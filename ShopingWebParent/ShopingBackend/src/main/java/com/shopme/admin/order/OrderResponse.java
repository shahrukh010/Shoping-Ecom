package com.shopme.admin.order;

import java.util.HashSet;
import java.util.Set;

import com.shopme.common.entity.order.Order;
import com.shopme.common.entity.order.OrderDetail;

public class OrderResponse {

	private Integer id;

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private Set<OrderDetail> orderDetails = new HashSet<>();

	public OrderResponse(Order order) {
		this.id = order.getId();
		this.firstName = order.getFirstName();
		this.lastName = order.getLastName();
		this.address1 = order.getAddress1();
		this.address2 = order.getAddress2();
		this.city = order.getCity();
		this.phoneNumber = order.getPhoneNumber();
		this.state = order.getState();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
