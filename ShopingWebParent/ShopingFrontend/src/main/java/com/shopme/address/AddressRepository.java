package com.shopme.address;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Customer;

public interface AddressRepository extends CrudRepository<Address, Integer> {

	public List<Address> findByCustomer(Customer customer);

	@Query("SELECT address FROM Address address WHERE address.id=?1 AND address.customer.id=?2")
	public Address findByIdAndCustomer(Integer addressId, Integer customerId);

	@Query("DELETE FROM Address address where address.id=?1 AND address.customer.id=?2")
	@Modifying
	public void deleteByIdCustomer(Integer addressId, Integer customerId);
}
