package com.ukma.library.service.impl;

import com.ukma.library.dto.OrderSaveDto;
import com.ukma.library.exception.IdNotMatchException;
import com.ukma.library.exception.ResourceNotFoundException;
import com.ukma.library.model.Book;
import com.ukma.library.model.Copy;
import com.ukma.library.model.Order;
import com.ukma.library.model.OrderStatus;
import com.ukma.library.repository.OrderRepository;
import com.ukma.library.service.BookService;
import com.ukma.library.service.CopyService;
import com.ukma.library.service.OrderService;
import com.ukma.library.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private CopyService copyService;
	@Resource
	private UserService userService;
	@Resource
	private BookService bookService;
	@Resource
	private OrderRepository orderRepository;

	private static final String ORDER_NOT_FOUND_WITH_ID = "Order not found with id ";

	@Override
	public Order save(OrderSaveDto orderSaveDto) {
		Set<Copy> copies = orderSaveDto.getCopiesIds().stream()
				.map(id -> copyService.getById(id).orElse(null))
				.filter(Objects::nonNull)
				.collect(toSet());

		Set<Book> books = copies.stream()
				.map(copy -> bookService.getById(copy.getBook().getIsbn()))
				.collect(toSet());

		LocalDateTime registeredDate = LocalDateTime.now();

		Order order = Order.builder()
				.status(OrderStatus.PENDING)
				.copies(copies)
				.books(books)
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

	@Override
	@Transactional
	public Order update(Order order, Long orderId) {
		if (!order.getId().equals(orderId))
			throw new IdNotMatchException("Id in path and inside order object are different");
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		if (optionalOrder.isEmpty())
			throw new ResourceNotFoundException(ORDER_NOT_FOUND_WITH_ID + orderId);
		Order orderToSave = optionalOrder.get();
		orderToSave.setActualReturnDate(order.getActualReturnDate());
		orderToSave.setStatus(order.getStatus());
		orderToSave.setTakenDate(order.getTakenDate());
		return orderToSave;
	}
}
