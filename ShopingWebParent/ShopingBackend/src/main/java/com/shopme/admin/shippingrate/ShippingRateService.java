package com.shopme.admin.shippingrate;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.paginig.PagingAndSortingHelper;
import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;

@Service
public class ShippingRateService {

	private static final int RATE_PER_PAGE = 10;

	@Autowired
	private ShippingRateRepository shippingRepo;

	public void listByPage(int pageNum, PagingAndSortingHelper helper) {

		helper.listEntites(pageNum, RATE_PER_PAGE, shippingRepo);
	}

	@Autowired
	private CountryRepository countryRepo;

	public List<Country> listAllCountry() {

		return countryRepo.findAllByOrderByNameAsc();
	}

	public void save(ShippingRate rateInForm) throws ShipingRateAlreadyException {

		ShippingRate rateInDB = shippingRepo.findByCountryAndState(rateInForm.getCountry().getId(),
				rateInForm.getState());

		boolean foundExistingRateInDB = rateInForm.getId() == null && rateInDB != null;
		boolean foundDifferentExistingRateInEditMode = rateInForm.getId() == null && rateInDB != null;

		if (foundExistingRateInDB || foundDifferentExistingRateInEditMode) {

			throw new ShipingRateAlreadyException("There already a rate for the destination "
					+ rateInForm.getCountry().getName() + "  " + rateInForm.getState());
		}

		shippingRepo.save(rateInForm);
	}

	public ShippingRate get(Integer id) throws ShipingRateNotFoundException {

		try {

			return shippingRepo.findById(id).get();
		} catch (NoSuchElementException ex) {

			throw new ShipingRateNotFoundException("Could not find shipping rate with id " + id);
		}
	}

	public void updateCODESupport(Integer id, boolean codeSupport) throws ShipingRateNotFoundException {

		Long count = shippingRepo.countById(id);
		if (count == null || count == 0)
			throw new ShipingRateNotFoundException("Could not find shiping rate with id " + id);

		shippingRepo.updateCodeSupport(id, codeSupport);
	}

	public void delete(Integer id) throws ShipingRateNotFoundException {

		Long count = shippingRepo.countById(id);
		if (count == null || count == 0)
			throw new ShipingRateNotFoundException("Could not find shiping rate with id " + id);

		shippingRepo.deleteById(id);
	}

}
