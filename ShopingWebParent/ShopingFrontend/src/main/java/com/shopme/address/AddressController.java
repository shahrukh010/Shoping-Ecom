package com.shopme.address;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
		System.out.println(listAddreses+"---");
		model.addAttribute("listAddresses", listAddreses);
		model.addAttribute("customer", customer);
		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
		return "address_book/addresses";
	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {

		String CUSTOMER_EMAIL = Utility.getEmailOfAuthenticatedCustomer(request);

		if (CUSTOMER_EMAIL == null)
			throw new CustomerNotFoundException("Customer Email Not Found");

		return customerService.getCustomerByEmail(CUSTOMER_EMAIL);
	}

}
