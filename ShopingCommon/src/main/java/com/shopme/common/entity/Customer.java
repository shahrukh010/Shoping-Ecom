package com.shopme.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(nullable = false, length = 45)
	private String firstName;
	@Column(nullable = false, length = 45)
	private String lastName;

	@Column(nullable = false, length = 12)
	private Long phoneNumber;

	@Column(nullable = false, length = 64)
	private String addressLine1;
	@Column(nullable = false, length = 64)
	private String addressLine2;
	@Column(length = 45)
	private String state;

	private String city;

	@Column(nullable = false, length = 64)
	private Integer postalCode;

	@Column(nullable = true, length = 64)
	private String verificationCode;
	private boolean enabled;
	private Date createdTime;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	@Enumerated(EnumType.STRING)
	@Column(name = "authentication_type", length = 10)
	private AuthenticationType authenticationType;

	public Customer() {
	}

	public Customer(Integer id) {
		this.id = id;
	}

	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(AuthenticationType authenticationType) {
		this.authenticationType = authenticationType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
//		return "Customer [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
//				+ ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", addressLine1=" + addressLine1
//				+ ", addressLine2=" + addressLine2 + ", state=" + state + ", postalCode=" + postalCode
//				+ ", verificationCode=" + verificationCode + ", enabled=" + enabled + ", createdTime=" + createdTime
//				+ ", country=" + country + "]";

		return getAddress();
	}

	public String getFullName() {

		return firstName + " " + lastName;
	}

	@Transient
	public String getAddress() {

		String address = firstName;

		if (lastName != null && !lastName.isBlank()) {
			address += " " + lastName;
		}

		if (addressLine1 != null && !addressLine1.isEmpty()) {
			address += "," + addressLine1;
		}

		if (addressLine2 != null && !addressLine2.isEmpty()) {
			address += "," + addressLine2;
		}

		if (city != null && !city.isEmpty()) {
			address += "," + city;
		}

		if (state != null && !state.isEmpty()) {
			address += "," + getState();
		}

		address += "," + getCountry().getName();

		if (postalCode != null) {
			address += ", pincode " + getPostalCode();
		}

		if (phoneNumber != null) {
			address += ", phone " + getPhoneNumber();
		}

		return address;
	}
}
