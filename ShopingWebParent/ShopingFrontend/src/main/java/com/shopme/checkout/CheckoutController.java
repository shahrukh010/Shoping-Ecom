package com.shopme.checkout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.Utility;
import com.shopme.address.AddressService;
import com.shopme.common.entity.Address;
import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.ShippingRate;
import com.shopme.customer.CustomerNotFoundException;
import com.shopme.customer.CustomerService;
import com.shopme.shipping.ShippingRateService;
import com.shopme.shopingcart.ShopingCartService;

@Controller
public class CheckoutController {

	@Autowired
	private CheckoutService checkoutService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ShippingRateService shippingRates;
	@Autowired
	private ShopingCartService shoppingCartService;

	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, HttpServletRequest request) throws CustomerNotFoundException {
		
		System.out.println("checkout");

		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> cartItems = shoppingCartService.listCartItems(customer);
		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;

		if (defaultAddress != null) {
			model.addAttribute("shippingAddress", defaultAddress.toString());
			shippingRate = shippingRates.getShippingRateForAddress(defaultAddress);
		} else {

			model.addAttribute("shippingAddress", customer.toString());
			shippingRate = shippingRates.getShippingRateForCustomer(customer);
		}

		if (shippingRate == null) {
			return "redirect:/cart";
		}
		List<CartItem> cartItem = shoppingCartService.listCartItems(customer);
		CheckoutInfo checkout = checkoutService.prepareCheckout(cartItem, shippingRate);

		model.addAttribute("checkoutInfo", checkout);
		model.addAttribute("cartItems", cartItem);
		model.addAttribute("customer", customer);

		return "checkout/checkout";
	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {

		String CUSTOMER_EMAIL = Utility.getEmailOfAuthenticatedCustomer(request);

		if (CUSTOMER_EMAIL == null)
			throw new CustomerNotFoundException("Customer Email Not Found");

		return customerService.getCustomerByEmail(CUSTOMER_EMAIL);
	}

	@PostMapping("/place_order")
	public String placeOrder(HttpServletRequest request) {

		return "checkout/order_completed";
	}

}
