package com.shopme.checkout;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import com.shopme.common.entity.order.Order;
import com.shopme.common.entity.order.PaymentMethod;
import com.shopme.customer.CustomerNotFoundException;
import com.shopme.customer.CustomerService;
import com.shopme.order.OrderService;
import com.shopme.setting.CurrencySettingBag;
import com.shopme.setting.EmailSettingBag;
import com.shopme.setting.SettingService;
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

	@Autowired
	private OrderService orderService;

	@Autowired
	private SettingService settingService;

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
	public String placeOrder(HttpServletRequest request)
			throws CustomerNotFoundException, UnsupportedEncodingException, MessagingException {

		String paymentTyep = request.getParameter("paymentMethod");
		PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentTyep);

		Customer customer = getAuthenticatedCustomer(request);

		List<CartItem> cartItems = shoppingCartService.listCartItems(customer);
		Address defaultAddress = addressService.getDefaultAddress(customer);

		ShippingRate shippingRate = null;

		if (defaultAddress != null) {
			shippingRate = shippingRates.getShippingRateForAddress(defaultAddress);
		} else {

			shippingRate = shippingRates.getShippingRateForCustomer(customer);
		}

		List<CartItem> cartItem = shoppingCartService.listCartItems(customer);
		CheckoutInfo checkout = checkoutService.prepareCheckout(cartItem, shippingRate);

		Order createOrder = orderService.createOrder(customer, defaultAddress, cartItems, paymentMethod, checkout);
		shoppingCartService.deleteByCustomer(customer);

		sendOrderConfirmationEmail(request, createOrder);

		return "checkout/order_completed";
	}

	private void sendOrderConfirmationEmail(HttpServletRequest request, Order order)
			throws UnsupportedEncodingException, MessagingException {

		EmailSettingBag emailSetting = settingService.getEmailSettings();
		JavaMailSender mailSender = Utility.parepareMailSender(emailSetting);

		String toAddress = order.getCustomer().getEmail();
		String subject = emailSetting.getOrderConfirmationSubject();
		String content = emailSetting.getOrderConfirmationContent();
		subject = subject.replace("[[orderId]", String.valueOf(order.getId()));

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(emailSetting.getFromEmailAddress(), emailSetting.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);

		DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss E,dd MMM yyyy");
		String orderTime = dateFormatter.format(order.getOrderTime());

		CurrencySettingBag currencySetting = settingService.getCurrencySettings();
		String totalAmount = Utility.formatCurrency(order.getTotal(), currencySetting);

		content = content.replace("[[name]]", order.getCustomer().getFullName());
		content = content.replace("[[orderId]]", String.valueOf(order.getId()));
		content = content.replace("[[orderTime]]", orderTime);
		content = content.replace("[[shippingAddress]]", order.getShippingAddress());
		content = content.replace("[[total]]", totalAmount);
		content = content.replace("[[paymentMethod]]", order.getPaymentMethod().toString());
		helper.setText(content, true);
		mailSender.send(message);
	}

}
