package com.shopme.shopingcart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.Utility;
import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerNotFoundException;
import com.shopme.customer.CustomerService;

@Controller
public class ShopingCartController {

	@Autowired
	private ShopingCartService shopingCartService;
	@Autowired
	private CustomerService customerService;

	@GetMapping("/cart")
	public String viewCart(Model model, HttpServletRequest request) throws CustomerNotFoundException {

		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> cartItems = shopingCartService.listCartItems(customer);

		model.addAttribute("cartItems", cartItems);
		return "cart/shopping_cart";
	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {

		String CUSTOMER_EMAIL = Utility.getEmailOfAuthenticatedCustomer(request);

		if (CUSTOMER_EMAIL == null)
			throw new CustomerNotFoundException("Customer Not Found");

		return customerService.getCustomerByEmail(CUSTOMER_EMAIL);
	}
}
