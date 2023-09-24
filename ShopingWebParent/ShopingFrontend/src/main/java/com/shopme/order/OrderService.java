package com.shopme.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.checkout.CheckoutInfo;
import com.shopme.common.entity.Address;
import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.order.Order;
import com.shopme.common.entity.order.OrderDetail;
import com.shopme.common.entity.order.OrderStatus;
import com.shopme.common.entity.order.PaymentMethod;

@Service
public class OrderService {

	@Autowired
	private OrderRepository order;

	public Order createOrder(Customer customer, Address address, List<CartItem> cartItems, PaymentMethod paymentMethod,
			CheckoutInfo checkoutInfo) {

		Order newOrder = new Order();
		newOrder.setOrderTime(new Date());
		newOrder.setOrderStatus(OrderStatus.NEW);
		newOrder.setCustomer(customer);
		newOrder.setProductCost(checkoutInfo.getProductCost());
		newOrder.setSubtotal(checkoutInfo.getProductTotal());
		newOrder.setShippingCost(checkoutInfo.getShippingCostTotal());
		newOrder.setPaymentMethod(paymentMethod);
		newOrder.setTax(0.0f);
		newOrder.setTotal(checkoutInfo.getPaymentTotal());
		newOrder.setDeliveryDays(checkoutInfo.getDeliverDays());
		newOrder.setDeliveryDate(checkoutInfo.getDeliverDate());

		if (address == null) {

			newOrder.copyAddressFromCustomer();
		} else {
			newOrder.copyShippingAddress(address);

		}
		Set<OrderDetail> orderDetails = newOrder.getOrderDetails();

		for (CartItem item : cartItems) {

			Product product = item.getProduct();
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(newOrder);
			orderDetail.setUnitPrice(product.getDiscountPrice());
			orderDetail.setShippingCost(item.getShippingCost());
			orderDetail.setProductCost(product.getCost() * item.getQuantity());
			orderDetail.setQuantity(item.getQuantity());
			orderDetail.setProduct(product);
			orderDetails.add(orderDetail);
		}
		return order.save(newOrder);
	}
}
