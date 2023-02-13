package com.shopme.address;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AddressRepositoryTest {

	@Autowired
	private AddressRepository repo;

	@Test
	public void testNewAddress() {

		Integer customerId = 7;
		Integer countryId = 106;// india

		Address address = new Address();
		address.setCustomer(new Customer(customerId));
		address.setCountry(new Country(countryId));
		address.setFirstName("Shahrukh");
		address.setLastName("khan");
		address.setPhoneNumber(9097620737l);
		address.setAddress1("Hyderabad");
		address.setAddress2("Ghausiya Cafe,Hitex,Hyderabad");
		address.setCity("Hitex");
		address.setState("Telangana");
		address.setZipCode(50018);

		Address saveAdd = repo.save(address);
		assertThat(saveAdd).isNotNull();
		assertThat(saveAdd.getId()).isGreaterThan(0);
	}

	@Test
	public void testFindByCustomer() {

		Integer customerId = 7;
		List<Address> listAddress = this.repo.findByCustomer(new Customer(customerId));
		assertThat(listAddress).isNotNull();
		listAddress.forEach(System.out::println);
	}

	@Test
	public void findByIdAndCustomer() {

		Integer id = 3;
		Integer customerId = 7;
		Address address = this.repo.findByIdAndCustomer(id, customerId);
		assertThat(address).isNotNull();
		System.out.println(address);
	}

	@Test
	public void deleteByIdAndCustomer() {

		Integer id = 3;
		Integer customerId = 7;
		this.repo.deleteByIdCustomer(id, customerId);
	}

	@Test
	public void updatePhoneNo() {

		Integer addressId = 2;
		long phoneNo = 8804345371l;
		Address address = this.repo.findById(addressId).get();
		if (address != null)
			address.setPhoneNumber(phoneNo);
		Address updateAddress = this.repo.save(address);
		assertThat(updateAddress.getPhoneNumber()).isEqualTo(phoneNo);
	}

	@Test
	public void setDefaultAddress() {

		Integer id = 3;
		repo.setDefaultAddress(id, 3);
	}

	@Test
	public void setNonDefaultForOther() {

		Integer id = 2;
		repo.setNonDefaultForOther(id, id);
	}
}
