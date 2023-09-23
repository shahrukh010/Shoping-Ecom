package com.shopme.admin.shipingrate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.shippingrate.ShippingRateRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ShipingRateRepositoryTest {

	@Autowired
	private ShippingRateRepository repo;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNew() {
		Country india = new Country(106);
		ShippingRate newRate = new ShippingRate();
		newRate.setCountry(india);
		newRate.setState("Haryana");
		newRate.setRate(6.25f);
		newRate.setDays(4);
		newRate.setCodeSupported(true);

		ShippingRate savedRate = repo.save(newRate);
		assertThat(savedRate).isNotNull();
		assertThat(savedRate.getId()).isGreaterThan(0);
	}

	@Test
	public void testFindAll() {
		List<ShippingRate> rates = (List<ShippingRate>) repo.findAll();
		assertThat(rates.size()).isGreaterThan(0);

		rates.forEach(System.out::println);
	}

}
