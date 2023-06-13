package com.shopme.shipping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)//not required in this case becase of we not updating data or inserting data
public class ShippingRateRepositoryTest {

	@Autowired
	private ShippingRateRepository repository;

	@Test
	public void testFindByCountryAndState() {

		Country country = new Country(106);

		ShippingRate rate = repository.findByCountryAndState(country, "Telangana");
		System.out.println(rate);
	}
}
