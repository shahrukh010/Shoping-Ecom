package com.shopme.common.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name", nullable = false, length = 45)
	private String firstName;
	@Column(name = "last_name", nullable = false, length = 45)
	private String lastName;

	/*
	 * When storing phone numbers in an e-commerce project, the most suitable data
	 * type to use would be a string. Phone numbers can include various characters
	 * such as digits, plus sign (+), parentheses, hyphens, and spaces, depending on
	 * the country or region.
	 */
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "Address_Line1", nullable = false, length = 64)
	private String address1;
	@Column(name = "Address_Line2", nullable = false, length = 64)
	private String address2;
	@Column(nullable = false, length = 45)
	private String city;
	@Column(nullable = false)
	private String state;
	@Column(nullable = false, length = 45)
	private String country;

	/*
	 * where you need to store the field "pincode," the most appropriate data type
	 * to use would typically be a string Pincode or postal codes often contain both
	 * numerical digits and alphabetic characters, depending on the country or
	 * region
	 */
	@Column(name = "postal_code", nullable = false, length = 10)
	private String pincode;

	private Date orderTime;
	private float shipingCost;
	private float productCost;
	private float subtotal;
	private float tax;
	private float total;

	private int deliveryDays;
	private Date deliveryDate;

	/*
	 * By annotating the paymentMethod field with @Enumerated(EnumType.STRING), you
	 * are specifying that the enum value should be persisted as a string in the
	 * 
	 * database. let's say you create an instance of the Order entity and set the
	 * payment method as follows:
	 * 
	 * Order order = new Order(); order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
	 * 
	 * When this Order object is persisted to the database, the paymentMethod value
	 * will be stored as the string "CREDIT_CARD" in the corresponding column.
	 * 
	 * Similarly, if you set the payment method to PaymentMethod.PAYPAL, it will be
	 * stored as the string "PAYPAL".
	 */
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	/*
	 * @ManyToOne: This annotation indicates that the relationship between the Order
	 * entity and the Customer entity is a many-to-one relationship. It means that
	 * multiple orders can be associated with a single customer.
	 * 
	 * @JoinColumn(name = "customer_id"): This annotation specifies the foreign key
	 * column used to establish the association between the Order entity and the
	 * Customer entity. In this case, the foreign key column is named "customer_id".
	 * 
	 * In the database schema, the "customer_id" column in the Order table will
	 * refer to the primary key of the corresponding Customer table.
	 */
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToMany(mappedBy="order")
	private Set<OrderDetails> orderDetails = new HashSet<>();
}
