package com.shopme.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetails = new HashSet<>();

	public Order() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public float getShipingCost() {
		return shipingCost;
	}

	public void setShipingCost(float shipingCost) {
		this.shipingCost = shipingCost;
	}

	public float getProductCost() {
		return productCost;
	}

	public void setProductCost(float productCost) {
		this.productCost = productCost;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getDeliveryDays() {
		return deliveryDays;
	}

	public void setDeliveryDays(int deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(java.util.Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Transient
	public String getDestination() {

		String destination = city + ", ";
		if (state != null && !state.isEmpty())
			destination += state;
		destination += country;
		return destination;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
				+ phoneNumber + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state="
				+ state + ", country=" + country + ", pincode=" + pincode + ", orderTime=" + orderTime
				+ ", shipingCost=" + shipingCost + ", productCost=" + productCost + ", subtotal=" + subtotal + ", tax="
				+ tax + ", total=" + total + ", deliveryDays=" + deliveryDays + ", deliveryDate=" + deliveryDate
				+ ", paymentMethod=" + paymentMethod + ", orderStatus=" + orderStatus + ", customer=" + customer
				+ ", orderDetails=" + orderDetails + "]";
	}

}
