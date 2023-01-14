package com.shopme.admin.shippingrate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopme.admin.paginig.PagingAndSortingHelper;
import com.shopme.admin.paginig.PagingAndSortingParam;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;

@Controller
public class ShippingRateController {

	private String defaultRedirectULR = "redirect:/shipping_rates/page/1?sortField=country&sortDir=asc";
	@Autowired
	private ShippingRateService service;

	@GetMapping("/shipping_rates")
	public String listFirstPage() {

		return defaultRedirectULR;
	}

	@GetMapping("/shipping_rates/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "shipingRates", moduleURL = "/shipping_rates") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		service.listByPage(pageNum, helper);

		return "shipping_rates/shipping_rates";
	}

	@GetMapping("shipping_rate/new")
	public String newRate(Model model) {

		List<Country> listCountries = service.listAllCountry();
		model.addAttribute("rate", new ShippingRate());
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("pageTitle", "New Rate");

		return "shipping_rates/shipping_rate_form";
	}
}
