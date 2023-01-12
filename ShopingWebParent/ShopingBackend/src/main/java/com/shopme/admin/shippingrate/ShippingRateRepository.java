package com.shopme.admin.shippingrate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.admin.paginig.SearchRepository;
import com.shopme.common.entity.ShippingRate;

@Repository
public interface ShippingRateRepository extends SearchRepository<ShippingRate, Integer> {

	@Query("SELECT shippingRate FROM ShippingRate shippingRate WHERE shippingRate.country.id=?1 AND shippingRate.state = ?2")
	public ShippingRate findByCountryAndState(Integer countryId, String state);

	@Modifying
	@Query("UPDATE ShippingRate shippingRate SET shippingRate.codeSupported=?2 WHERE shippingRate.id=?1")
	public void updateCodeSupport(Integer id, boolean enabled);

	@Query("SELECT shippingRate FROM ShippingRate shippingRate WHERE shippingRate.country.name LIKE %?1% OR shippingRate.state LIKE %?1%")
	public Page<ShippingRate> findAll(String keyword, Pageable pageable);

	public Long countById(Integer id);

}
