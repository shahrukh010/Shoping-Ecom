package com.shopme.common.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shipping_rates")
public class ShippingRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private float rate;

	private int days;

	@Column(name = "code_supported")
	private boolean codeSupported;

	@ManyToOne
	@JoinColumn(name = "country_id")//join  country_id column in shipping_rate table with ManyToOne relationship
	private Country country;

	@Column(nullable = false)
	private String state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public boolean isCodeSupported() {
		return codeSupported;
	}

	public void setCodeSupported(boolean codeSupported) {
		this.codeSupported = codeSupported;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ShipingRate [id=" + id + ", rate=" + rate + ", days=" + days + ", codeSupported=" + codeSupported
				+ ", country=" + country + ", state=" + state + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShippingRate other = (ShippingRate) obj;
		return Objects.equals(id, other.id);
	}

}
