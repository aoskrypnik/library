package com.ukma.library.controller;

import com.ukma.library.dto.OrderSaveDto;
import com.ukma.library.model.Order;
import com.ukma.library.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Resource
	private OrderService orderService;

	@GetMapping()
	public List<Order> getAll(@RequestParam(required = false) Long userId,
							  @RequestParam(required = false) String status) {
		return orderService.getAll().stream()
				.filter(order -> {
					boolean filteredByUserId = true;
					boolean filteredByStatus = true;
					if (nonNull(userId)) {
						filteredByUserId = order.getUser().getId().equals(userId);
					}
					if (nonNull(status)) {
						filteredByStatus = order.getStatus().toString().equals(status);
					}
					return filteredByUserId && filteredByStatus;
				})
				.sorted(Comparator.comparing(Order::getRegisteredDate).reversed())
				.collect(Collectors.toList());
	}

	@PostMapping
	public Order save(@RequestBody OrderSaveDto orderSaveDto) {
		return orderService.save(orderSaveDto);
	}

	@GetMapping("/{orderId}")
	public Order getById(@PathVariable Long orderId) {
		return orderService.getById(orderId);
	}
}
