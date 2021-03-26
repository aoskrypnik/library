package com.ukma.library.controller;

import com.ukma.library.dto.OrderFilterDto;
import com.ukma.library.dto.OrderSaveDto;
import com.ukma.library.model.Order;
import com.ukma.library.service.OrderService;
import com.ukma.library.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;

	@GetMapping("/current-user")
	public List<Order> getCurrentUserOrders() {
		Long currentUserId = userService.getCurrentUser().getId();
		return orderService.getAll().stream()
				.filter(order -> order.getUser().getId().equals(currentUserId))
				.sorted(Comparator.comparing(Order::getRegisteredDate).reversed())
				.collect(Collectors.toList());
	}

	@PostMapping
	public Order save(@RequestBody OrderSaveDto orderSaveDto) {
		return orderService.save(orderSaveDto);
	}

	@GetMapping(value = "/search")
	public Page<Order> getAll(@SortDefault(sort = "registeredDate", direction = Sort.Direction.ASC) Pageable pageable,
							  OrderFilterDto orderFilter) {
		return orderService.search(orderFilter, pageable);
	}

	@PutMapping(value = "/{id}")
	public Order updateBook(@RequestBody Order order, @PathVariable Long id) {
		return orderService.update(order, id);
	}

	@GetMapping("/{orderId}")
	public Order getById(@PathVariable Long orderId) {
		return orderService.getById(orderId);
	}
}
