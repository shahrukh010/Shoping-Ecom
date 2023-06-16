package com.shopme.admin.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopme.admin.paginig.PagingAndSortingHelper;
import com.shopme.admin.paginig.PagingAndSortingParam;
import com.shopme.admin.setting.SettingService;
import com.shopme.common.entity.Order;
import com.shopme.common.entity.Setting;

@Controller
public class OrderController {

	private String defaultRedirectURL = "redirect:/orders/page/1?sortField=orderTime&sortDir=desc";

	@Autowired
	private OrderService orderService;

	@Autowired
	private SettingService settingService;

	@GetMapping("/orders")
	public String listFirstPage() {
		return defaultRedirectURL;
	}

	@GetMapping("/orders/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listOrders", moduleURL = "/orders") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum, HttpServletRequest request) {

		Page orders = orderService.listByPage(pageNum, helper);
		// debug
		List<Order> order = orders.getContent();
		System.out.print(order);
		loadCurrencySetting(request);
		return "orders/orders";
	}

	@GetMapping("/orders/delete/{id}")
	public String orderDelete(@PathVariable(name = "id") Integer id, Model redirectAttributes) {

		try {
			orderService.deleteOrder(id);
		} catch (OrderNotFoundException ex) {
			redirectAttributes.addAttribute("message", ex.getMessage());
		}
		return defaultRedirectURL;

	}

	private void loadCurrencySetting(HttpServletRequest request) {

		List<Setting> currencySetting = settingService.getCurrencySetting();

		for (Setting setting : currencySetting) {
			request.setAttribute(setting.getKey(), setting.getValue());
		}
	}
}
