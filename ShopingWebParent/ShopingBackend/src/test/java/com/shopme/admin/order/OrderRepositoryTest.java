package com.shopme.admin.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.order.Order;
import com.shopme.common.entity.order.OrderDetail;
import com.shopme.common.entity.order.OrderStatus;
import com.shopme.common.entity.order.PaymentMethod;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void testNewOrder() {

		Customer customer = testEntityManager.find(Customer.class, 6);
		Product product = testEntityManager.find(Product.class, 16);

		Order mainOrder = new Order();
		mainOrder.setFirstName(customer.getFirstName());
		mainOrder.setLastName(customer.getLastName());
		mainOrder.setAddress1(customer.getAddressLine1());
		mainOrder.setAddress2(customer.getAddressLine2());
		mainOrder.setPhoneNumber(customer.getPhoneNumber().toString());
		mainOrder.setCity(customer.getCity());
		mainOrder.setCountry(customer.getCountry().getName());
		mainOrder.setState(customer.getState());
		mainOrder.setPincode(customer.getPostalCode().toString());
		mainOrder.setShippingCost(10);
		mainOrder.setProductCost(product.getCost());
		mainOrder.setTax(0);
		mainOrder.setSubtotal(product.getPrice());
		mainOrder.setTotal(product.getPrice() + 10);

		mainOrder.setPaymentMethod(PaymentMethod.CREDIT_CARD);
		mainOrder.setOrderStatus(OrderStatus.NEW);
		mainOrder.setDeliveryDate(new Date());
		mainOrder.setDeliveryDays(1);

		OrderDetail orderDetails = new OrderDetail();
		orderDetails.setProduct(product);
		orderDetails.setOrder(mainOrder);
		orderDetails.setProductCost(product.getCost());
		orderDetails.setShippingCost(10);
		orderDetails.setQuantity(1);
		orderDetails.setSubtotal(product.getPrice());
		orderDetails.setUnitPrice(product.getPrice());
		mainOrder.getOrderDetails().add(orderDetails);
		mainOrder.setCustomer(customer);// for customer_id in db value

		Order saveOrder = orderRepo.save(mainOrder);
		assertThat(saveOrder.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewOrderWithMultipleProduct() {

		Customer customer = testEntityManager.find(Customer.class, 7);
		Product product = testEntityManager.find(Product.class, 1);
		Product product2 = testEntityManager.find(Product.class, 9);

		Order mainOrder = new Order();
		mainOrder.setCustomer(customer);
		mainOrder.setFirstName(customer.getFirstName());
		mainOrder.setLastName(customer.getLastName());
		mainOrder.setAddress1(customer.getAddressLine1());
		mainOrder.setAddress2(customer.getAddressLine2());
		mainOrder.setPhoneNumber(customer.getPhoneNumber().toString());
		mainOrder.setCity(customer.getCity());
		mainOrder.setCountry(customer.getCountry().getName());
		mainOrder.setState(customer.getState());
		mainOrder.setPincode(customer.getPostalCode().toString());

		OrderDetail orderDetails = new OrderDetail();
		orderDetails.setProduct(product);
		orderDetails.setOrder(mainOrder);
		orderDetails.setProductCost(product.getCost());
		orderDetails.setShippingCost(10);
		orderDetails.setQuantity(10);
		orderDetails.setSubtotal(product.getPrice());
		orderDetails.setUnitPrice(product.getPrice());

		OrderDetail orderDetails1 = new OrderDetail();
		orderDetails1.setProduct(product2);
		orderDetails1.setOrder(mainOrder);
		orderDetails1.setProductCost(product2.getCost());
		orderDetails1.setShippingCost(20);
		orderDetails1.setQuantity(3);
		orderDetails1.setSubtotal(product2.getPrice() * 3);
		orderDetails1.setUnitPrice(product2.getPrice());
		mainOrder.getOrderDetails().add(orderDetails);
		mainOrder.getOrderDetails().add(orderDetails1);

		mainOrder.setShippingCost(30);
		mainOrder.setProductCost(product.getCost() + product2.getCost());
		mainOrder.setTax(0);
		float subtotal = product.getPrice() + product2.getPrice() * 3;
		mainOrder.setSubtotal(subtotal);
		mainOrder.setTotal(subtotal + 30);

		mainOrder.setPaymentMethod(PaymentMethod.CODE);
		mainOrder.setOrderStatus(OrderStatus.PROCESSING);
		mainOrder.setDeliveryDate(new Date());
		mainOrder.setDeliveryDays(10);
		Order saveOrder = orderRepo.save(mainOrder);

		assertThat(saveOrder.getId()).isGreaterThan(0);
	}
//
//	@Test
//	public void testListOrder() {
//
//		Iterable<Order> listOrder = orderRepo.findAll();
//
//		listOrder.forEach(System.out::println);
//	}
//
//	@Test
//	public void testUpdateOrder() {
//
//		Integer orderId = 8;
//
//		Order order = orderRepo.findById(orderId).get();
//		order.setOrderStatus(OrderStatus.DELIVERD);
//		order.setPaymentMethod(PaymentMethod.CODE);
//		orderRepo.save(order);
//	}

}
