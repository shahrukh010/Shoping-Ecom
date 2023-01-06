package com.shopme.shopingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopme.category.ProductRepository;
import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Product;

@Service
@Transactional // this annotation use when we are working on delete/update query on repository
				// layer to avoid TransactionException
public class ShopingCartService {

	@Autowired
	private ShopingCartRepository cartRepo;

	@Autowired
	private ProductRepository productRepo;

	public Integer addProduct(Integer productId, Integer quantity, Customer customer) throws ShopingCartException {

		Integer updateQuantity = quantity;

		Product product = new Product(productId);

		CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);
		if (cartItem != null) {

			if (updateQuantity > 5) {

				throw new ShopingCartException("you can not add more then " + 5 + " items");
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

	public List<CartItem> listCartItems(Customer customer) {

		return cartRepo.findByCustomer(customer);
	}

	public float updateQuantity(Integer productId, Integer quantity, Customer customer) {

		cartRepo.updateQuantity(quantity, customer.getId(), productId);

		Product product = productRepo.findById(productId).get();
		float subtotal = product.getDiscountPrice() * quantity;
		return subtotal;
	}

	public void removeProduct(Integer productId, Customer customer) {

		cartRepo.deleteByCustomerAndProduct(customer.getId(), productId);
	}
}
