package com.shopme.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.ShippingRate;

@Service
public class ShippingRateService {

	@Autowired
	private ShippingRateRepository shippingRate;

	// shopping cart allowed to delivered on this customer country and state or not
	// using customer object
	public ShippingRate getShippingRateForCustomer(Customer customer) {

		String state = customer.getState();
		if (state == null || state.isEmpty()) {
			state = customer.getCity();
		}
		return shippingRate.findByCountryAndState(customer.getCountry(), state);
	}

	// shopping cart allowed to delivered on this Adress country and state or not
	// using address object
	public ShippingRate getShippingRateForAddress(Address address) {

		String state = address.getState();
		if (state == null || state.isEmpty()) {
			state = address.getCity();
		}
		return shippingRate.findByCountryAndState(address.getCountry(), state);
	}
}
