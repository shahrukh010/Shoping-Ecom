package com.shopme.admin.order;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.paginig.PagingAndSortingHelper;
import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.order.Order;

@Service
public class OrderService {

	private static final int ORDERS_PER_PAGE = 10;
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CountryRepository countryRepo;

	public Page listByPage(int pageNum, PagingAndSortingHelper helper) {

		String sortedField = helper.getSortField();
		String sortDir = helper.getSortDir();
		String keyword = helper.getKeyword();

		Sort sort = null;
		if ("destination".equals(sortedField)) {

			sort = Sort.by("country").and(Sort.by("state")).and(Sort.by("city"));
		} else {
			sort = Sort.by(sortedField);
		}

		sort = sort.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE);

		Page<Order> page = null;
		if (keyword != null) {

			page = orderRepository.findAll(keyword, pageable);
		} else {

			page = orderRepository.findAll(pageable);
			page.forEach(obj -> System.out.println(obj.getCustomer().getFirstName() + ":" + obj.getProductCost()));
		}
		helper.updateModelAttributes(pageNum, page);
		return page;
	}

	public Order get(Integer id) throws OrderNotFoundException {

		try {

			return orderRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new OrderNotFoundException("Count not find any order with id:" + id);
		}
	}

	public List<Country> listCountries() {

		List<Country> list = countryRepo.findAllByOrderByNameAsc();
		return list;
	}

	public void deleteOrder(Integer id) throws OrderNotFoundException {

		Long count = orderRepository.count();
		if (count == null || count == 0)
			throw new OrderNotFoundException("Could not find any order");

		orderRepository.deleteById(id);
	}
}
