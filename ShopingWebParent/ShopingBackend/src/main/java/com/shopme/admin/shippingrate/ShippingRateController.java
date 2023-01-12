package com.shopme.admin.shippingrate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopme.admin.paginig.PagingAndSortingHelper;
import com.shopme.admin.paginig.PagingAndSortingParam;

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
	public String listByPagele(
			@PagingAndSortingParam(listName = "shipingRates", moduleURL = "/shipping_rates") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		service.listByPage(pageNum, helper);
		return "shipping_rates/shipping_rates";
	}
}
