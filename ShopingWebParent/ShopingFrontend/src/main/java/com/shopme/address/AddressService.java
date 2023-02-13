package com.shopme.address;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.setting.CountryRepository;

@Service
@Transactional
public class AddressService {

	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private CountryRepository countryRepo;

	public List<Address> listAddressBook(Customer customer) {

		return addressRepo.findByCustomer(customer);
	}

	public List<Country> listAllCountry() {

		List<Country> listCountry = (List<Country>) countryRepo.findAll();
		return listCountry;
	}

	public void save(Address address) {

		addressRepo.save(address);
	}

	public Address get(Integer addressId, Integer customerId) {

		return addressRepo.findByIdAndCustomer(addressId, customerId);
	}

	public void delete(Integer addressId, Integer customerId) {

		addressRepo.deleteByIdCustomer(addressId, customerId);
	}

	public void setDefaultAddress(Integer defaultAddressId, Integer customerId) {

		if (defaultAddressId > 0) {
			addressRepo.setDefaultAddress(defaultAddressId, customerId);
		}
		addressRepo.setNonDefaultForOther(defaultAddressId, customerId);
	}
}
