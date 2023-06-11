package com.shopme.admin.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.admin.paginig.SearchRepository;
import com.shopme.common.entity.Order;

@Repository
public interface OrderRepository extends SearchRepository<Order, Integer> {

//	@Query("SELECT o FROM Order o WHERE o.firstName LIKE %?1% OR "
//			+ "o.lastName LIKE %?1% OR "
//			+ "o.customer.firstName LIKE %?1% OR "
//			+ "o.customer.lastName Like %?1%")
//	public Page<Order> findAll(String keyword, Pageable pageable);

	@Query("SELECT o FROM Order o WHERE CONCAT('#', o.id) LIKE %?1% OR "
			+ " CONCAT(o.firstName, ' ', o.lastName) LIKE %?1% OR" + " o.firstName LIKE %?1% OR"
			+ " o.lastName LIKE %?1% OR o.phoneNumber LIKE %?1% OR"
			+ " o.address1 LIKE %?1% OR o.address2 LIKE %?1% OR"
			+ " o.city LIKE %?1% OR" + " o.state LIKE %?1% OR o.country LIKE %?1% OR"
			+ " o.paymentMethod LIKE %?1% OR"
			+ " o.customer.firstName LIKE %?1% OR"
			+ " o.customer.lastName LIKE %?1%")
	public Page<Order> findAll(String keyword, Pageable pageable);
}
