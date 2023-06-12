package com.shopme.admin.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.paginig.PagingAndSortingHelper;
import com.shopme.common.entity.Order;

@Service
public class OrderService {

	private static final int ORDERS_PER_PAGE = 10;
	@Autowired
	private OrderRepository orderRepository;

	public void listByPage(int pageNum, PagingAndSortingHelper helper) {

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
			page.forEach(obj->System.out.println(obj.getCustomer().getFirstName()+":"+obj.getProductCost()));
		}
		helper.updateModelAttributes(pageNum, page);
	}
}
