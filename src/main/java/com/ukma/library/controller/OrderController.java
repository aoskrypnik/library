package com.ukma.library.controller;

import com.ukma.library.dto.OrderSaveDto;
import com.ukma.library.model.Order;
import com.ukma.library.service.OrderService;
import com.ukma.library.service.UserService;
import org.springframework.web.bind.annotation.*;

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

	@PutMapping(value = "/{id}")
	public Order updateBook(@RequestBody Order order, @PathVariable Long id) {
		return orderService.update(order, id);
	}

	@GetMapping("/{orderId}")
	public Order getById(@PathVariable Long orderId) {
		return orderService.getById(orderId);
	}
}
