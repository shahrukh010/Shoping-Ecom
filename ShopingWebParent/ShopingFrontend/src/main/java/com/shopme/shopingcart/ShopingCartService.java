package com.shopme.shopingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Product;

@Service
public class ShopingCartService {

	@Autowired
	private ShopingCartRepository cartRepo;

	public Integer addProduct(Integer productId, Integer quantity, Customer customer) {

		Integer updateQuantity = quantity;

		Product product = new Product(productId);

		CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);
		if (cartItem != null) {

			updateQuantity = cartItem.getQuantity();
		} else {

			cartItem.setCustomer(customer);
			cartItem.setProduct(product);
		}
		cartItem.setQuantity(updateQuantity);

		return updateQuantity;
	}

}
