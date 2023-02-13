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
import com.shopme.common.entity.Order;
import com.shopme.common.entity.OrderDetails;
import com.shopme.common.entity.OrderStatus;
import com.shopme.common.entity.PaymentMethod;
import com.shopme.common.entity.Product;

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

		Customer customer = testEntityManager.find(Customer.class, 2);
		Product product = testEntityManager.find(Product.class, 2);

		Order mainOrder = new Order();
		mainOrder.setFirstName(customer.getFirstName());
		mainOrder.setLastName(customer.getLastName());
		mainOrder.setAddress1(customer.getAddressLine1());
		mainOrder.setAddress2(customer.getAddressLine2());
		mainOrder.setPhoneNumber(Long.parseLong(customer.getPhoneNumber()));
		mainOrder.setCity(customer.getCity());
		mainOrder.setCountry(customer.getCountry().getName());
		mainOrder.setState(customer.getState());
		mainOrder.setPostalCode(Integer.parseInt(customer.getPostalCode()));
		mainOrder.setShippingCost(10);
		mainOrder.setProductCost(product.getCost());
		mainOrder.setTax(0);
		mainOrder.setSubtotal(product.getPrice());
		mainOrder.setTotal(product.getPrice() + 10);

		mainOrder.setPaymentMethod(PaymentMethod.CREDIT_CARD);
		mainOrder.setStatus(OrderStatus.NEW);
		mainOrder.setDeliverDate(new Date());
		mainOrder.setDeliverDays(1);

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setProduct(product);
		orderDetails.setOrder(mainOrder);
		orderDetails.setPrdouctCost(product.getCost());
		orderDetails.setShippingCost(10);
		orderDetails.setQuantity(1);
		orderDetails.setSubtotal(product.getPrice());
		orderDetails.setUnitPrice(product.getPrice());
		mainOrder.getOrderDetails().add(orderDetails);

		Order saveOrder = orderRepo.save(mainOrder);
		assertThat(saveOrder.getId()).isGreaterThan(0);
	}
}
