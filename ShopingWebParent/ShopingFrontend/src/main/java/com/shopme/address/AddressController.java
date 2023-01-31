package com.shopme.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.Utility;
import com.shopme.common.entity.Address;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerNotFoundException;
import com.shopme.customer.CustomerService;

@Controller
public class AddressController {

	@Autowired
	private AddressService addressService;
	@Autowired
	private CustomerService customerService;

	@GetMapping("address_book")
	public String showAddressBook(Model model, HttpServletRequest httpRequest) throws CustomerNotFoundException {

		Customer customer = getAuthenticatedCustomer(httpRequest);

		List<Address> listAddreses = addressService.listAddressBook(customer);
		model.addAttribute("listAddress", listAddreses);
		model.addAttribute("customer", customer);

		return "address_book/addresses";
	}

//	@GetMapping("address_book")
//	public ResponseEntity<Map<String, Object>> showAddressBook(HttpServletRequest httpRequest)
//			throws CustomerNotFoundException {
//
//		Customer customer = getAuthenticatedCustomer(httpRequest);
//		List<Address> listAddresses = addressService.listAddressBook(customer);
//
//		Map<String, Object> response = new HashMap<>();
//
//		for (Map.Entry<String, Object> res : response.entrySet()) {
//
//			System.out.println(res.getKey() + "------>" + res.getValue());
//		}
////		response.put("customer", customer);
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {

		String CUSTOMER_EMAIL = Utility.getEmailOfAuthenticatedCustomer(request);

		if (CUSTOMER_EMAIL == null)
			throw new CustomerNotFoundException("Customer Email Not Found");

		return customerService.getCustomerByEmail(CUSTOMER_EMAIL);
	}

}
