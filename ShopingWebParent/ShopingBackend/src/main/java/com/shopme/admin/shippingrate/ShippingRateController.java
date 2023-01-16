package com.shopme.admin.shippingrate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.paginig.PagingAndSortingHelper;
import com.shopme.admin.paginig.PagingAndSortingParam;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;

@Controller
public class ShippingRateController {

	private String defaultRedirectURL = "redirect:/shipping_rates/page/1?sortField=country&sortDir=asc";
	@Autowired
	private ShippingRateService service;

	@GetMapping("/shipping_rates")
	public String listFirstPage() {

		return defaultRedirectURL;
	}

	@GetMapping("/shipping_rates/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "shipingRates", moduleURL = "/shipping_rates") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		service.listByPage(pageNum, helper);

		return "shipping_rates/shipping_rates";
	}

	@GetMapping("shipping_rates/new")
	public String newRate(Model model) {

		List<Country> listCountries = service.listAllCountry();
		model.addAttribute("rate", new ShippingRate());
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("pageTitle", "New Rate");

		return "shipping_rates/shipping_rate_form";
	}

	@PostMapping("/shipping_rates/save")
	public String save(ShippingRate rate, RedirectAttributes redirectAttribute) {

		try {

			service.save(rate);
			redirectAttribute.addAttribute("messsage", "The shipping rate has been save successfully");

		} catch (ShipingRateAlreadyException ex) {

			redirectAttribute.addAttribute("message", ex.getMessage());
		}
		return defaultRedirectURL;
	}

	@GetMapping("/shipping_rates/edit/{id}")
	public String edit(@PathVariable(name = "id") Integer id, Model model) {

		return null;
	}
}
