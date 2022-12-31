package com.shopme.shopingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Product;

@Service
public class ShopingCartService {

	@Autowired
	private ShopingCartRepository cartRepo;

	public Integer addProduct(Integer productId, Integer quantity, Customer customer)throws ShopingCartException {

		Integer updateQuantity = quantity;

		Product product = new Product(productId);

		CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);
		if (cartItem != null) {

			if(updateQuantity > 5) {
				
				throw new ShopingCartException("you can not add more then "+5+" items");
			}
			updateQuantity = cartItem.getQuantity();
		} else {

			cartItem = new CartItem();
			cartItem.setCustomer(customer);
			cartItem.setProduct(product);
		}
		cartItem.setQuantity(updateQuantity);
		cartRepo.save(cartItem);
		return updateQuantity;
	}

	
	public List<CartItem>listCartItems(Customer customer){
		
		return cartRepo.findByCustomer(customer);
	}
}
