package com.shopme.admin.shippingrate;

import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShippingRateController {

	private String defaultRedirectULR = "redirect:/shipping_rates/page/1?sortField=country&sortDir=asc";

	@GetMapping("/shopping_rates")
	public String listFirstPage() {

		return defaultRedirectULR;
	}

	@GetMapping("/shipping_rates/page/{pageNum}")
	public String listByPage() {

	}
}
