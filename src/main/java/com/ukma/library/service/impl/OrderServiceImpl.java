package com.ukma.library.service.impl;

import com.ukma.library.dto.OrderSaveDto;
import com.ukma.library.exception.ResourceNotFoundException;
import com.ukma.library.model.Copy;
import com.ukma.library.model.Order;
import com.ukma.library.model.OrderStatus;
import com.ukma.library.repository.OrderRepository;
import com.ukma.library.service.CopyService;
import com.ukma.library.service.OrderService;
import com.ukma.library.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	private static final String ORDER_NOT_FOUND_WITH_ID = "order not found with id ";

	@Resource
	private CopyService copyService;
	@Resource
	private UserService userService;
	@Resource
	private OrderRepository orderRepository;

	@Override
	public Order save(OrderSaveDto orderSaveDto) {
		Set<Copy> copies = orderSaveDto.getCopiesIds().stream().map(id -> copyService.getById(id).get()).collect(Collectors.toSet());
		LocalDateTime registeredDate = LocalDateTime.now();
		Order order = Order.builder()
				.status(OrderStatus.PENDING)
				.copies(copies)
				.registeredDate(registeredDate)
				.estimatedReturnDate(registeredDate.plusMonths(1))
				.user(userService.getCurrentUser())
				.build();
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	@Override
	public Order getById(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(ORDER_NOT_FOUND_WITH_ID + orderId));
	}
}
