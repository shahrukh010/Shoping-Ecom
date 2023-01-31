package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name", nullable = false, length = 45)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 45)
	private String lastName;

	@Column(name = "phone_number", nullable = false, length = 12)
	private long phoneNumber;

	@Column(name = "address_line1", nullable = false, length = 64)
	private String address1;

	@Column(name = "address_line2", nullable = false, length = 64)
	private String address2;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private int zipCode;

	@ManyToOne()
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne()
	@JoinColumn(name = "country_id")
	private Country country;

	@Column(name = "default_address")
	private boolean defaultForShipping;


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

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
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

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public boolean getDefaultForShipping() {
		return defaultForShipping;
	}

	public void setDefaultForShipping(boolean defaultForShipping) {
		this.defaultForShipping = defaultForShipping;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
				+ phoneNumber + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state="
				+ state + ", zipCode=" + zipCode + ",country=" + country.getName() + ", defaultForShipping="
				+ defaultForShipping + "]";
	}

}
