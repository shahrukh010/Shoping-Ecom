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

	// shopping cart allowed to delivered on this customer based country and state
	// or not
	// checking product shipping is eligible or not
	// suppose if customer type some other state or country which are not available
	// in shipping_rate db then user will be No Shipping Available for this location
	public ShippingRate getShippingRateForCustomer(Customer customer) {

		String state = customer.getState();
		if (state == null || state.isEmpty()) {
			state = customer.getCity();
		}
		return shippingRate.findByCountryAndState(customer.getCountry(), state);
	}

	// shopping cart allowed to delivered on this Adress country and state or not
	// using address object
	// checking product shipping is eligible or not
	// suppose if customer type some other state or country which are not available
	// in shipping_rate db then user will be No Shipping Available for this location
	public ShippingRate getShippingRateForAddress(Address address) {

		String state = address.getState();
		if (state == null || state.isEmpty()) {
			state = address.getCity();
		}
		return shippingRate.findByCountryAndState(address.getCountry(), state);
	}
}
