package com.shopme.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.Utility;
import com.shopme.common.entity.Address;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerNotFoundException;
import com.shopme.customer.CustomerService;

@Controller
public class AddressController {

	@Autowired
	private AddressService addressService;
	@Autowired
	private CustomerService customerService;

	private boolean usePrimaryAddressAsDefault = true;

	@GetMapping("address_book")
	public String showAddressBook(Model model, HttpServletRequest httpRequest) throws CustomerNotFoundException {

		Customer customer = getAuthenticatedCustomer(httpRequest);

		List<Address> listAddreses = addressService.listAddressBook(customer);

		for (Address address : listAddreses) {

			if (address.getDefaultForShipping()) {
				usePrimaryAddressAsDefault = false;
				break;
			}
		}
		System.out.println(listAddreses + "---");
		model.addAttribute("listAddresses", listAddreses);
		model.addAttribute("customer", customer);
		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
		return "address_book/addresses";
	}

	@GetMapping("address_book/new")
	public String newAddress(Model model) {

		List<Country> listCountries = addressService.listAllCountry();
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("address", new Address());
		model.addAttribute("pageTitle", "Add New Address ");

		return "address_book/address_form";
	}

	@PostMapping("address_book/save")
	public String save(Address address, HttpServletRequest request, RedirectAttributes redirect)
			throws CustomerNotFoundException {

		Customer customer = getAuthenticatedCustomer(request);
		address.setCustomer(customer);
		addressService.save(address);
		redirect.addFlashAttribute("message", "The address has been save successfully");

		return "redirect:/address_book";

	}

	@GetMapping("address_book/edit/{id}")
	public String edit(@PathVariable("id") Integer addressId, Model model, HttpServletRequest request)
			throws CustomerNotFoundException {

		Customer customer = getAuthenticatedCustomer(request);
		List<Country> listCountries = addressService.listAllCountry();
		Address address = addressService.get(addressId, customer.getId());

		model.addAttribute("address", address);
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("pageTitle", "Edit Address (ID: " + addressId + ")");
		return "address_book/address_form";
	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {

		String CUSTOMER_EMAIL = Utility.getEmailOfAuthenticatedCustomer(request);

		if (CUSTOMER_EMAIL == null)
			throw new CustomerNotFoundException("Customer Email Not Found");

		return customerService.getCustomerByEmail(CUSTOMER_EMAIL);
	}

	@GetMapping("address_book/delete/{id}")
	public String deleteAddress(@PathVariable("id") Integer addressId, RedirectAttributes redirect,
			HttpServletRequest request) throws CustomerNotFoundException {

		Customer customer = getAuthenticatedCustomer(request);
		addressService.delete(addressId, customer.getId());
		redirect.addFlashAttribute("message", "Address is deleted.");
		return "redirect:/address_book";
	}

	@GetMapping("address_book/default/{id}")
	public String setDefaultAddress(@PathVariable("id") Integer defaultAddressId, HttpServletRequest request)
			throws CustomerNotFoundException {

		Customer customer = getAuthenticatedCustomer(request);

		addressService.setDefaultAddress(defaultAddressId, customer.getId());
		return "redirect:/address_book";
	}
}
