package com.shopme.checkout;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.ShippingRate;

@Service
public class CheckoutService {

	private static final int DIM_DIVISOR = 139;

	public CheckoutInfo prepareCheckout(List<CartItem> cartItem, ShippingRate rate) {

		CheckoutInfo checkout = new CheckoutInfo();

		float productCost = productCost(cartItem);
		float productTotal = productTotal(cartItem);
		float shippingCostTotal = shippingCost(cartItem, rate);
		checkout.setProductCost(productCost);
		checkout.setProductTotal(productTotal);
		checkout.setDeliverDays(rate.getDays());
		checkout.setCodSupported(rate.isCodeSupported());
		checkout.setShippingCostTotal(shippingCostTotal);

		return checkout;
	}

	private float shippingCost(List<CartItem> cartItem, ShippingRate rate) {

		float shippingCostTotal = 0.0f;
		for (CartItem cart : cartItem) {

			Product product = cart.getProduct();
			float dimWeight = (product.getLength() * product.getWidth() * product.getHeight()) / DIM_DIVISOR;
			float finalWeight = product.getWeight() > dimWeight ? product.getWeight() : dimWeight;
			float shippingCost = finalWeight * cart.getQuantity() * rate.getRate();
			cart.setShippingCost(shippingCost);

			shippingCostTotal += shippingCost;
		}
		return shippingCostTotal;
	}

	private float productCost(List<CartItem> cartItem) {

		float cost = 0.0f;
		for (CartItem cart : cartItem) {

			cost += cart.getQuantity() * cart.getProduct().getCost();
		}
		return cost;
	}

	private float productTotal(List<CartItem> cartItem) {

		float total = 0.0f;
		for (CartItem cart : cartItem) {

			total += cart.getSubtotal();
		}
		return total;
	}
}
