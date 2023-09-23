package com.shopme.shipping;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)//not required in this case becase of we not updating data or inserting data
public class ShippingRateRepositoryTest {

	@Autowired
	private ShippingRateRepository repository;

	@Test
	public void testCreateNew() {
		Country india = new Country(106);
		ShippingRate newRate = new ShippingRate();
		newRate.setCountry(india);
		newRate.setState("New Delhi");
		newRate.setRate(8.25f);
		newRate.setDays(6);
		newRate.setCodeSupported(true);

		ShippingRate savedRate = repository.save(newRate);
		assertThat(savedRate).isNotNull();
		assertThat(savedRate.getId()).isGreaterThan(0);
	}

	@Test
	public void testFindByCountryAndState() {

		Country country = new Country(106);

		ShippingRate rate = repository.findByCountryAndState(country, "Telangana");
		System.out.println(rate);
	}
}
